package com.vitisvision.vitisvisionservice.domain.group.mapper;

import com.vitisvision.vitisvisionservice.domain.group.dto.GroupVineResponse;
import com.vitisvision.vitisvisionservice.domain.vine.entity.Vine;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * GroupVineResponseMapper class is a mapper class to map the vine entity to group vine response.
 */
@Service
public class GroupVineResponseMapper implements Function<Vine, GroupVineResponse> {

    @Override
    public GroupVineResponse apply(Vine vine) {
        return GroupVineResponse.builder()
                .vineId(vine.getId())
                .vineNumber(vine.getVineNumber())
                .rowNumber(vine.getRowNumber())
                .variety(vine.getVariety())
                .bolesCount(vine.getBolesCount())
                .plantingDate(vine.getPlantingDate().toString())
                .formationType(vine.getFormationType())
                .blockId(vine.getBlock().getId().toString())
                .build();
    }
}
