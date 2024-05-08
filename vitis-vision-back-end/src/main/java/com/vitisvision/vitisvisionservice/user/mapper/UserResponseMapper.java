package com.vitisvision.vitisvisionservice.user.mapper;

import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import com.vitisvision.vitisvisionservice.user.dto.UserResponse;
import com.vitisvision.vitisvisionservice.user.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * UserResponseMapper class is a mapper class for mapping the user entity to the user response DTO.
 */
@Service
public class UserResponseMapper implements Function<User, UserResponse> {

    @Override
    public UserResponse apply(User user) {
        LocalDateTime lastUpdatedAt = user.getLastUpdatedAt();
        Vineyard userVineyard = user.getVineyard();
        return UserResponse.builder()
                .id(user.getId())
                .createdAt(user.getCreatedAt().toString())
                .createdBy(user.getCreatedBy())
                .lastUpdatedAt(lastUpdatedAt != null ? lastUpdatedAt.toString() : null)
                .lastUpdatedBy(user.getLastUpdatedBy())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .vineyardId(userVineyard != null ? userVineyard.getId().toString() : null)
                .isBlocked(user.isBlocked())
                .build();
    }
}
