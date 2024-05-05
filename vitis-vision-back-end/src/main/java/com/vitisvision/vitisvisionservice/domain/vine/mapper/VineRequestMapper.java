package com.vitisvision.vitisvisionservice.domain.vine.mapper;

import com.vitisvision.vitisvisionservice.domain.vine.dto.VineRequest;
import com.vitisvision.vitisvisionservice.domain.vine.entity.Vine;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * VineRequestMapper is a mapper class for vine request.
 */
@Service
public class VineRequestMapper implements Function<VineRequest, Vine> {

    @Override
    public Vine apply(VineRequest vineRequest) {
        return Vine.builder()
                .vineNumber(vineRequest.getVineNumber())
                .rowNumber(vineRequest.getRowNumber())
                .variety(vineRequest.getVariety())
                .bolesCount(vineRequest.getBolesCount())
                .plantingDate(LocalDate.parse(vineRequest.getPlantingDate()))
                .formationType(vineRequest.getFormationType())
                .build();
    }

    public void update(VineRequest vineRequest, Vine vine) {
        vine.setVineNumber(vineRequest.getVineNumber());
        vine.setRowNumber(vineRequest.getRowNumber());
        vine.setVariety(vineRequest.getVariety());
        vine.setBolesCount(vineRequest.getBolesCount());
        vine.setPlantingDate(LocalDate.parse(vineRequest.getPlantingDate()));
        vine.setFormationType(vineRequest.getFormationType());
    }
}