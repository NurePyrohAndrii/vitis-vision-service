package com.vitisvision.vitisvisionservice.domain.staff.mapper;

import com.vitisvision.vitisvisionservice.domain.staff.dto.StaffResponse;
import com.vitisvision.vitisvisionservice.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Mapper class for mapping staff response.
 */
@Service
public class StaffResponseMapper implements Function<User, StaffResponse> {

    @Override
    public StaffResponse apply(User user) {
        return StaffResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
