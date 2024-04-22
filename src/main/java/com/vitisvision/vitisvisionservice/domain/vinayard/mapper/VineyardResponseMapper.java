package com.vitisvision.vitisvisionservice.domain.vinayard.mapper;

import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardResponse;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Address;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Company;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class VineyardResponseMapper implements Function<Vineyard, VineyardResponse> {
    @Override
    public VineyardResponse apply(Vineyard vineyard) {
        Company vineyardCompany = vineyard.getCompany();
        Address vineyardCompanyAddress = vineyardCompany.getAddress();
        return VineyardResponse.builder()
                .id(vineyard.getId())
                .companyName(vineyardCompany.getCompanyName())
                .dbaName(vineyardCompany.getDbaName())
                .streetAddress(vineyardCompanyAddress.getStreetAddress())
                .city(vineyardCompanyAddress.getCity())
                .zipCode(vineyardCompanyAddress.getZipCode())
                .phoneNumber(vineyardCompany.getPhoneNumber())
                .email(vineyardCompany.getEmail())
                .createdAt(vineyard.getCreatedAt())
                .createdBy(vineyard.getCreatedBy())
                .build();
    }
}
