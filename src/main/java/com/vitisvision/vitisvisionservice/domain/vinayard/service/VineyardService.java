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

    private final VineyardResponseMapper vineyardResponseMapper;

    /**
     * This method is used to create a vineyard.
     * @param vineyardRequest a request object to create a vineyard
     * @param principal a principal object to get the user details
     * @return vineyard response object containing the created vineyard details
     */
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public VineyardResponse createVineyard(VineyardRequest vineyardRequest, Principal principal) {
        checkVineyardDuplicationByDbaName(vineyardRequest.getDbaName());

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

    @Transactional
    @PreAuthorize("hasAuthority('vineyard:write')")
    public VineyardResponse updateVineyard(Integer vineyardId, VineyardRequest vineyardRequest, Principal principal) {
        checkVineyardDuplicationByDbaName(vineyardRequest.getDbaName());

        Vineyard editedVineyard = vineyardRepository.findById(vineyardId)
                .orElseThrow(() -> new VineyardNotFoundException("vineyard.not.found.error"));

        return null;
        // TODO: Implement the update vineyard logic
    }

    private void checkVineyardDuplicationByDbaName(String dbaName) {
        // Check if the vineyard is already exists
        if (vineyardRepository.existsByCompany_DbaName(dbaName)) {
            throw new VineyardDuplicationException("vineyard.duplicate.error");
        }
    }
}
