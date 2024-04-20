package com.vitisvision.vitisvisionservice.domain.vinayard.service;

import com.vitisvision.vitisvisionservice.domain.vinayard.dto.CreateVineyardRequest;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import com.vitisvision.vitisvisionservice.domain.vinayard.exception.VineyardDuplicationException;
import com.vitisvision.vitisvisionservice.domain.vinayard.exception.VineyardParticipationConflictException;
import com.vitisvision.vitisvisionservice.domain.vinayard.repository.VineyardRepository;
import com.vitisvision.vitisvisionservice.user.entity.User;
import com.vitisvision.vitisvisionservice.user.mapper.CreateVineyardRequestMapper;
import com.vitisvision.vitisvisionservice.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    private final CreateVineyardRequestMapper createVineyardRequestMapper;

    /**
     * This method is used to create a vineyard.
     * @param createVineyardRequest a request object to create a vineyard
     * @param principal a principal object to get the user details
     * @return vineyard id
     */
    @Transactional
    public Integer createVineyard(CreateVineyardRequest createVineyardRequest, Principal principal) {

        User user = userService.findUserByEmail(principal.getName());

        // Check if the user is already participating in a vineyard
        if (user.getVineyard() != null) {
            throw new VineyardParticipationConflictException("user.already.participating.error");
        }

        // Check if the vineyard is already exists
        if (vineyardRepository.existsByCompany_DbaName(createVineyardRequest.getDbaName())) {
                throw new VineyardDuplicationException("vineyard.duplicate.error");
        }

        // Save the vineyard
        Vineyard vineyard = vineyardRepository.save(
                createVineyardRequestMapper.apply(createVineyardRequest)
        );

        // Set the vineyard to the user
        user.setVineyard(vineyard);
        userService.saveUser(user);

        return vineyard.getId();
    }
}
