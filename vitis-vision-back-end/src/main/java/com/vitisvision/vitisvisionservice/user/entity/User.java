package com.vitisvision.vitisvisionservice.user.entity;

import com.vitisvision.vitisvisionservice.common.entity.BaseEntity;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import com.vitisvision.vitisvisionservice.security.entity.Token;
import com.vitisvision.vitisvisionservice.user.enumeration.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * User entity class.
 * This class is used to represent the user entity in the database.
 * It is also used to implement the UserDetails interface to provide
 * the user details to the spring security.
 */
@Entity
@Table(name = "_user")
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

    /**
     * The first name of the user.
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * The last name of the user.
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * The email of the user. Application insures that this is unique.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * The password of the user.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The role of the user.
     *
     * @see Role
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * The block status
     */
    boolean isBlocked = false;

    /**
     * The list of tokens associated with the user.
     *
     * @see Token
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Token> tokens;

    /**
     * Describes the user's vineyard employment status.
     * The vineyard associated with the user. This is optional. If the user is not associated with a vineyard, this will be null.
     *
     * @see Vineyard
     */
    @ManyToOne
    @JoinColumn(name = "vineyard_id")
    private Vineyard vineyard;

    /**
     * Get the authorities of the user. Mandatory for the UserDetails interface of spring security.
     *
     * @return List of {@link SimpleGrantedAuthority}.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    /**
     * Get the password of the user. Mandatory for the UserDetails interface of spring security.
     *
     * @return The password of the user.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Get the username of the user. Mandatory for the UserDetails interface of spring security.
     *
     * @return The email of the user.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Check if the account is not expired. Mandatory for the UserDetails interface of spring security.
     *
     * @return True if the account is not expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the account is not locked. Mandatory for the UserDetails interface of spring security.
     *
     * @return True if the account is not locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked;
    }

    /**
     * Check if the credentials are not expired. Mandatory for the UserDetails interface of spring security.
     *
     * @return True if the credentials are not expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if the account is enabled. Mandatory for the UserDetails interface of spring security.
     *
     * @return True if the account is enabled.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
