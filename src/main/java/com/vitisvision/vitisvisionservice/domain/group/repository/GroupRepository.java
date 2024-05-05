package com.vitisvision.vitisvisionservice.domain.group.repository;

import com.vitisvision.vitisvisionservice.domain.group.entity.Group;
import com.vitisvision.vitisvisionservice.domain.vine.entity.Vine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Group repository interface.
 * This interface is used to perform database operations on the group entity.
 */
public interface GroupRepository extends JpaRepository<Group, Integer> {

    /**
     * Check if a group with the provided name exists in the vineyard.
     *
     * @param name       the name of the group
     * @param vineyardId the id of the vineyard
     * @return true if the group exists, false otherwise
     */
    boolean existsByNameAndVineyardId(String name, Integer vineyardId);

    /**
     * Find all groups in a vineyard by the vineyard id.
     *
     * @param vineyardId the id of the vineyard
     * @param pageable the pageable object containing the pagination details
     * @return the page object containing the groups in the vineyard
     */
    Page<Group> findAllByVineyardId(Integer vineyardId, Pageable pageable);

    /**
     * Find a group by its name and the vineyard id.
     *
     * @param name       the name of the group
     * @param vineyardId the id of the vineyard
     * @return the group object if found, empty otherwise
     */
    Optional<Group> findByNameAndVineyardId(String name, Integer vineyardId);

    /**
     * Check if a group with the provided id and vineyard id exists.
     *
     * @param groupId    the id of the group
     * @param vineyardId the id of the vineyard
     * @return true if the group exists, false otherwise
     */
    boolean existsByIdAndVineyardId(Integer groupId, Integer vineyardId);
}
