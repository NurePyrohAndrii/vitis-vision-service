package com.vitisvision.vitisvisionservice.security.entity;

import com.vitisvision.vitisvisionservice.security.enumeration.TokenType;
import com.vitisvision.vitisvisionservice.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * Token entity class to store token information in the database.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Token token = (Token) o;
        return getId() != null && Objects.equals(getId(), token.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
