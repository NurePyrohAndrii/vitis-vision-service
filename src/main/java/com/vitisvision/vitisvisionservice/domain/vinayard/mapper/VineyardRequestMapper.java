package com.vitisvision.vitisvisionservice.domain.vinayard.mapper;

import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardRequest;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Address;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Company;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class VineyardRequestMapper implements Function<VineyardRequest, Vineyard> {
    @Override
    public Vineyard apply(VineyardRequest request) {
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
