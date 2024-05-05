package com.vitisvision.vitisvisionservice.domain.vinayard.mapper;

import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardResponse;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Address;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Company;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * The CreateVineyardResponseMapper class is used to map the vineyard entity to vineyard response.
 */
@Service
public class VineyardResponseMapper implements Function<Vineyard, VineyardResponse> {

    /**
     * This method is used to map the vineyard entity to vineyard response when the vineyard is created.
     *
     * @param vineyard the vineyard entity object
     * @return the vineyard response object
     */
    @Override
    public VineyardResponse apply(Vineyard vineyard) {
        Company vineyardCompany = vineyard.getCompany();
        Address vineyardCompanyAddress = vineyardCompany.getAddress();

        LocalDateTime lastUpdatedAt = vineyard.getLastUpdatedAt();
        return VineyardResponse.builder()
                .id(vineyard.getId())
                .companyName(vineyardCompany.getCompanyName())
                .dbaName(vineyardCompany.getDbaName())
                .streetAddress(vineyardCompanyAddress.getStreetAddress())
                .city(vineyardCompanyAddress.getCity())
                .zipCode(vineyardCompanyAddress.getZipCode())
                .phoneNumber(vineyardCompany.getPhoneNumber())
                .email(vineyardCompany.getEmail())
                .createdAt(vineyard.getCreatedAt().toString())
                .createdBy(vineyard.getCreatedBy())
                .lastUpdatedAt(lastUpdatedAt != null ? lastUpdatedAt.toString() : null)
                .lastUpdatedBy(vineyard.getLastUpdatedBy())
                .build();
    }

}
