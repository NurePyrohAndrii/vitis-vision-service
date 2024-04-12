package com.vitisvision.vitisvisionservice.domain.vinayard.repository;

import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository class for managing Vineyard entity in the database
 */
public interface VineyardRepository extends JpaRepository<Vineyard, Integer> {
}
