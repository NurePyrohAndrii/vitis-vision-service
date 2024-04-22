package com.vitisvision.vitisvisionservice.domain.vinayard.service;

import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardRequest;
import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardResponse;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import com.vitisvision.vitisvisionservice.domain.vinayard.exception.VineyardDuplicationException;
import com.vitisvision.vitisvisionservice.domain.vinayard.exception.VineyardNotFoundException;
import com.vitisvision.vitisvisionservice.domain.vinayard.exception.VineyardParticipationConflictException;
import com.vitisvision.vitisvisionservice.domain.vinayard.mapper.VineyardResponseMapper;
import com.vitisvision.vitisvisionservice.domain.vinayard.repository.VineyardRepository;
import com.vitisvision.vitisvisionservice.user.entity.User;
import com.vitisvision.vitisvisionservice.domain.vinayard.mapper.VineyardRequestMapper;
import com.vitisvision.vitisvisionservice.user.enumeration.Role;
import com.vitisvision.vitisvisionservice.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

/**
 * VineyardService class is a service class for vineyard operations.
 */
@Service
@RequiredArgsConstructor
public class VineyardService {

    /**
     * VineyardRepository object is used to access the vineyard data.
     */
    private final VineyardRepository vineyardRepository;

    /**
     * UserService object is used to access the user operations.
     */
    private final UserService userService;

    /**
     * CreateVineyardRequestMapper object is used to map the vineyard request to vineyard entity.
     */
    private final VineyardRequestMapper createVineyardRequestMapper;

    /**
     * VineyardResponseMapper object is used to map the vineyard entity to vineyard response.
     */
    private final VineyardResponseMapper vineyardResponseMapper;

    /**
     * This method is used to create a vineyard.
     *
     * @param vineyardRequest a request object to create a vineyard
     * @param principal       a principal object to get the user details
     * @return vineyard response object containing the created vineyard details
     */
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public VineyardResponse createVineyard(VineyardRequest vineyardRequest, Principal principal) {
        // Check if a vineyard with the given dbaName exists
        if (vineyardRepository.existsByCompany_DbaName(vineyardRequest.getDbaName())) {
            throw new VineyardDuplicationException("vineyard.duplicate.error");
        }

        User user = userService.findUserByEmail(principal.getName());

        // Check if the user is already participating in a vineyard
        if (user.getVineyard() != null) {
            throw new VineyardParticipationConflictException("user.already.participating.error");
        }

        // Save the vineyard
        Vineyard vineyard = vineyardRepository.save(
                createVineyardRequestMapper.apply(vineyardRequest)
        );

        // Set the user`s vineyard and role
        user.setVineyard(vineyard);
        user.setRole(Role.VINEYARD_DIRECTOR);
        userService.saveUser(user);

        return vineyardResponseMapper.apply(vineyard);
    }

    /**
     * This method is used to update a vineyard with the provided details.
     *
     * @param vineyardId      the vineyard id to update
     * @param vineyardRequest the request object to update the vineyard
     * @param principal       the principal object to get the user details
     * @return vineyard response object containing the updated vineyard details
     */
    @Transactional
    @PreAuthorize("hasAuthority('vineyard:write')")
    public VineyardResponse updateVineyard(Integer vineyardId, VineyardRequest vineyardRequest, Principal principal) {

        ensureVineyardParticipation(vineyardId, principal);

        Optional<Vineyard> vineyardByRequestDbaName = vineyardRepository.findByCompany_DbaName(vineyardRequest.getDbaName());

        // If a vineyard with the given dbaName exists, and it is not requested vineyard
        if (vineyardByRequestDbaName.isPresent() && !vineyardByRequestDbaName.get().getId().equals(vineyardId)) {
            throw new VineyardDuplicationException("vineyard.duplicate.error");
        }

        Vineyard updatedVineyard = createVineyardRequestMapper.apply(vineyardRequest);
        updatedVineyard.setId(vineyardId);

        return vineyardResponseMapper.apply(vineyardRepository.save(updatedVineyard));
    }

    /**
     * This method is used to delete a vineyard.
     *
     * @param vineyardId the vineyard id to delete
     * @param principal  the principal object to get the user details
     */
    @Transactional
    @PreAuthorize("hasAuthority('vineyard:delete')")
    public void deleteVineyard(Integer vineyardId, Principal principal) {
        ensureVineyardParticipation(vineyardId, principal);
        userService.disassociateVineyardStaff(vineyardId);
        vineyardRepository.deleteById(vineyardId);
    }

    /**
     * This method is used to ensure the vineyard participation of the user.
     *
     * @param vineyardId the vineyard id to check
     * @param principal  the principal object to get the user details
     */
    private void ensureVineyardParticipation(Integer vineyardId, Principal principal) {
        if (!vineyardRepository.existsById(vineyardId)) {
            throw new VineyardNotFoundException("vineyard.not.found.error");
        }

        User requestingUser = userService.findUserByEmail(principal.getName());

        if (!requestingUser.getVineyard().getId().equals(vineyardId)) {
            throw new VineyardParticipationConflictException("vineyard.participation.mismatch.error");
        }
    }

}
