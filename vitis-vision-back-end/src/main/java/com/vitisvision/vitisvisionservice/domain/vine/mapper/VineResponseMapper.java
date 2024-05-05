package com.vitisvision.vitisvisionservice.domain.vine.mapper;

import com.vitisvision.vitisvisionservice.domain.vine.dto.VineResponse;
import com.vitisvision.vitisvisionservice.domain.vine.entity.Vine;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * VineResponseMapper class is used to map the vine entity to vine response.
 */
@Service
public class VineResponseMapper implements Function<Vine, VineResponse> {

    @Override
    public VineResponse apply(Vine vine) {
        LocalDateTime lastUpdatedAt = vine.getLastUpdatedAt();
        return VineResponse.builder()
                .id(vine.getId())
                .vineNumber(vine.getVineNumber())
                .rowNumber(vine.getRowNumber())
                .variety(vine.getVariety())
                .bolesCount(vine.getBolesCount())
                .plantingDate(vine.getPlantingDate().toString())
                .formationType(vine.getFormationType())
                .createdAt(vine.getCreatedAt().toString())
                .createdBy(vine.getCreatedBy())
                .lastUpdatedAt(lastUpdatedAt != null ? lastUpdatedAt.toString() : null)
                .lastUpdatedBy(vine.getLastUpdatedBy())
                .build();
    }
}

