package com.vitisvision.vitisvisionservice.domain.block.repository;

import com.vitisvision.vitisvisionservice.domain.block.entity.Block;
import com.vitisvision.vitisvisionservice.domain.device.entity.DeviceData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for handling block details in the database.
 */
public interface BlockRepository extends JpaRepository<Block, Integer> {

    /**
     * Check if a block with the provided name exists within a certain vineyard in the database.
     *
     * @param name the name of the block
     * @return true if the block exists, false otherwise
     */
    boolean existsByNameAndVineyardId(String name, Integer vineyardId);

    /**
     * Check if a block with the provided id and vineyard id exists in the database.
     *
     * @param id the id of the block
     * @param vineyardId the id of the vineyard
     * @return true if the block exists, false otherwise
     */
    boolean existsByIdAndVineyard_Id(Integer id, Integer vineyardId);

    /**
     * Find a block by its name and the vineyard id.
     *
     * @param name the name of the block
     * @param vineyardId the id of the vineyard
     * @return the block object if found, empty otherwise
     */
    Optional<Block> findByNameAndVineyard_Id(String name, Integer vineyardId);

    /**
     * Find all blocks in a vineyard by the vineyard id.
     *
     * @param vineyardId the id of the vineyard
     * @param pageable the pageable object containing the pagination details
     * @return the page object containing the blocks in the vineyard
     */
    Page<Block> findAllByVineyard_Id(Pageable pageable, Integer vineyardId);
}
