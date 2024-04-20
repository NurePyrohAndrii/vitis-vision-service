package com.vitisvision.vitisvisionservice.user.mapper;

import com.vitisvision.vitisvisionservice.domain.vinayard.dto.CreateVineyardRequest;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Address;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Company;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CreateVineyardRequestMapper implements Function<CreateVineyardRequest, Vineyard> {
    @Override
    public Vineyard apply(CreateVineyardRequest request) {
        return Vineyard.builder()
                .company(
                        Company.builder()
                                .companyName(request.getCompanyName())
                                .dbaName(request.getDbaName())
                                .address(
                                        Address.builder()
                                                .streetAddress(request.getStreetAddress())
                                                .city(request.getCity())
                                                .zipCode(request.getZipCode())
                                                .build()
                                )
                                .phoneNumber(request.getPhoneNumber())
                                .email(request.getEmail())
                                .build()
                )
                .build();
    }
}
