package com.vitisvision.vitisvisionservice.domain.vine.repository;

import com.vitisvision.vitisvisionservice.domain.vine.entity.Vine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * VineRepository is a repository interface for vine operations.
 */
public interface VineRepository extends JpaRepository<Vine, Integer> {

    /**
     * Check if a vine exists by vine number, row number and block id.
     *
     * @param vineNumber the vine number
     * @param rowNumber  the row number
     * @param blockId    the block id
     * @return true if the vine exists, false otherwise
     */
    boolean existsByVineNumberAndRowNumberAndBlock_Id(Integer vineNumber, Integer rowNumber, Integer blockId);

    /**
     * Find a vine by vine number, row number and block id.
     *
     * @param vineNumber the vine number
     * @param rowNumber  the row number
     * @param blockId    the block id
     * @return the vine object if found, null otherwise
     */
    Optional<Vine> findByVineNumberAndRowNumberAndBlock_Id(Integer vineNumber, Integer rowNumber, Integer blockId);

    /**
     * Find a vine by vine id and block id.
     *
     * @param vineId  the vine id
     * @param blockId the block id
     * @return the vine object if found, null otherwise
     */
    Optional<Vine> findByIdAndBlock_Id(Integer vineId, Integer blockId);

    /**
     * Find all vines by block id.
     *
     * @param blockId  the block id
     * @param pageable the pageable object containing the pagination details
     * @return the page object containing the vine objects
     */
    Page<Vine> findAllByBlock_Id(Integer blockId, Pageable pageable);

    /**
     * Check if a vine exists by vine id and block id.
     *
     * @param vineId  the vine id
     * @param blockId the block id
     * @return true if the vine exists, false otherwise
     */
    boolean existsByIdAndBlock_Id(Integer vineId, Integer blockId);

    /**
     * Find all vines by block id and vine ids.
     *
     * @param pageable the pageable object containing the pagination details
     * @param ids      the list of vine ids
     * @return the page object containing the vine objects
     */
    @Query("SELECT v FROM Vine v WHERE v.id IN :ids")
    Page<Vine> findAllByIds(Pageable pageable, @Param("ids") List<Integer> ids);

    /**
     * Find all vines by block id and vine ids.
     *
     * @param groupVineIds the list of vine ids
     * @return the list of vine objects
     */
    @Query("SELECT v FROM Vine v WHERE v.id NOT IN :groupVineIds")
    Page<Vine> findAllNotInIds(Pageable pageable, List<Integer> groupVineIds);
}
