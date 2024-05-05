package com.vitisvision.vitisvisionservice.domain.vinayard.repository;

import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository class for managing Vineyard entity in the database
 */
public interface VineyardRepository extends JpaRepository<Vineyard, Integer> {

    /**
     * Check if a vineyard with the given dbaName exists in the database
     *
     * @param dbaName the dbaName to check
     * @return true if a vineyard with the given dbaName exists, false otherwise
     */
    boolean existsByCompany_DbaName(String dbaName);

    /**
     * Find a vineyard by the given dbaName
     *
     * @param dbaName the dbaName to find
     * @return the vineyard with the given dbaName
     */
    Optional<Vineyard> findByCompany_DbaName(String dbaName);

    /**
     * Check if a vineyard with the given id exists in the database
     *
     * @param id the id to check
     * @return true if a vineyard with the given id exists, false otherwise
     */
    boolean existsById (int id);
}
