package com.vitisvision.vitisvisionservice.domain.block.mapper;

import com.vitisvision.vitisvisionservice.domain.block.dto.BlockResponse;
import com.vitisvision.vitisvisionservice.domain.block.entity.Block;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * BlockResponseMapper class is used to map the block entity to block response.
 */
@Service
public class BlockResponseMapper implements Function<Block, BlockResponse> {

    @Override
    public BlockResponse apply(Block block) {
        LocalDateTime lastUpdatedAt = block.getLastUpdatedAt();
        return BlockResponse.builder()
                .id(block.getId())
                .name(block.getName())
                .partitioningType(block.getPartitioningType())
                .rowSpacing(block.getRowSpacing())
                .vineSpacing(block.getVineSpacing())
                .rowOrientation(block.getRowOrientation())
                .trellisSystemType(block.getTrellisSystemType())
                .createdAt(block.getCreatedAt().toString())
                .createdBy(block.getCreatedBy())
                .lastUpdatedAt(lastUpdatedAt != null ? lastUpdatedAt.toString() : null)
                .lastUpdatedBy(block.getLastUpdatedBy())
                .build();
    }
}
