package com.vitisvision.vitisvisionservice.security.repository;

import com.vitisvision.vitisvisionservice.security.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * TokenRepository interface to interact with the database using JPA.
 */
public interface TokenRepository extends JpaRepository<Token, Integer> {

    /**
     * Find all valid tokens by user id. Validity is determined by the token not being expired or revoked.
     *
     * @param userId the user id
     * @return the list of valid tokens
     */
    @Query("""
            select t from Token t inner join User u on t.user.id = u.id
            where u.id = :userId and (t.expired = false and t.revoked = false)
            """)
    List<Token> findAllValidTokensByUserId(Integer userId);


    /**
     * Find by token.
     *
     * @param token the token
     * @return the optional token
     */
    Optional<Token> findByToken(String token);
}
