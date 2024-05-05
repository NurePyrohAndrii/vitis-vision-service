package com.vitisvision.vitisvisionservice.user.repository;

import com.vitisvision.vitisvisionservice.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * User Repository interface to interact with the database using JPA.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find a user by email.
     *
     * @param email email of the user.
     * @return user object.
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user exists by email.
     *
     * @param email email of the user.
     * @return true if a user exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Finds all users working in a vineyard with the given id.
     *
     * @param vineyardId id of the vineyard.
     * @return list of users.
     */
    List<User> findAllByVineyard_Id(int vineyardId);

    /**
     * Finds all users working in a vineyard with the given id.
     *
     * @param vineyardId id of the vineyard.
     * @param pageable the pageable object.
     * @return page of users.
     */
    Page<User> findAllByVineyard_Id(int vineyardId, Pageable pageable);
}
