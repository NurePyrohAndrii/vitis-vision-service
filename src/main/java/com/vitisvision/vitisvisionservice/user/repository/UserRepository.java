package com.vitisvision.vitisvisionservice.user.repository;

import com.vitisvision.vitisvisionservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

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
     * @return true if user exists, false otherwise.
     */
    boolean existsByEmail(String email);

}
