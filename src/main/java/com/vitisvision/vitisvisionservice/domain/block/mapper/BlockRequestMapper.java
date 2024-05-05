package com.vitisvision.vitisvisionservice.domain.block.mapper;

import com.vitisvision.vitisvisionservice.domain.block.dto.BlockRequest;
import com.vitisvision.vitisvisionservice.domain.block.entity.Block;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * BlockRequestMapper class is used to map the block request to block entity.
 */
@Service
public class BlockRequestMapper implements Function<BlockRequest, Block> {

    @Override
    public Block apply(BlockRequest blockRequest) {
        return Block.builder()
                .name(blockRequest.getName())
                .partitioningType(blockRequest.getPartitioningType())
                .rowSpacing(blockRequest.getRowSpacing())
                .vineSpacing(blockRequest.getVineSpacing())
                .rowOrientation(blockRequest.getRowOrientation())
                .trellisSystemType(blockRequest.getTrellisSystemType())
                .build();
    }

    /**
     * Update the block entity with the provided details
     *
     * @param blockRequest the request object containing the block details
     * @param updatedBlock the block entity to be updated
     */
    public void update(BlockRequest blockRequest, Block updatedBlock) {
        updatedBlock.setName(blockRequest.getName());
        updatedBlock.setPartitioningType(blockRequest.getPartitioningType());
        updatedBlock.setRowSpacing(blockRequest.getRowSpacing());
        updatedBlock.setVineSpacing(blockRequest.getVineSpacing());
        updatedBlock.setRowOrientation(blockRequest.getRowOrientation());
        updatedBlock.setTrellisSystemType(blockRequest.getTrellisSystemType());
    }
}
