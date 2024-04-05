package com.vitisvision.vitisvisionservice.token;

import com.vitisvision.vitisvisionservice.user.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * Token entity class to store token information in the database.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {

    /**
     * The id of the token.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * The token value.
     */
    private String token;

    /**
     * The token type.
     * @see TokenType
     */
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    /**
     * The token creation date.
     */
    private boolean expired;

    /**
     * The token expiration date.
     */
    private boolean revoked;

    /**
     * The user associated with the token.
     */
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
