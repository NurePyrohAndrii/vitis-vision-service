package com.vitisvision.vitisvisionservice.domain.vinayard.mapper;

import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardRequest;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Address;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Company;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * VineyardRequestMapper class is a mapper class that maps the vineyard request to vineyard entity.
 */
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

    /**
     * Update the vineyard entity with the provided details
     *
     * @param vineyardRequest the request object containing the vineyard details
     * @param vineyard        the vineyard entity to be updated
     */
    public void update(VineyardRequest vineyardRequest, Vineyard vineyard) {
        Company company = vineyard.getCompany();
        Address address = company.getAddress();

        company.setCompanyName(vineyardRequest.getCompanyName());
        company.setDbaName(vineyardRequest.getDbaName());
        address.setStreetAddress(vineyardRequest.getStreetAddress());
        address.setCity(vineyardRequest.getCity());
        address.setZipCode(vineyardRequest.getZipCode());
        company.setPhoneNumber(vineyardRequest.getPhoneNumber());
        company.setEmail(vineyardRequest.getEmail());
    }
}
