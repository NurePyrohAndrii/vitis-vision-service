package com.vitisvision.vitisvisionservice.user;

import com.vitisvision.vitisvisionservice.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User entity class.
 * This class is used to represent the user entity in the database.
 * It is also used to implement the UserDetails interface to provide
 * the user details to the spring security.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    /**
     * The id of the user.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The email of the user. Application insures that this is unique.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The role of the user.
     *
     * @see Role
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * The list of tokens associated with the user.
     *
     * @see Token
     */
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    /**
     * Get the authorities of the user.
     *
     * @return List of {@link SimpleGrantedAuthority}.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Get the password of the user.
     *
     * @return The password of the user.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Get the username of the user.
     *
     * @return The email of the user.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Check if the account is not expired.
     *
     * @return True if the account is not expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the account is not locked.
     *
     * @return True if the account is not locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if the credentials are not expired.
     *
     * @return True if the credentials are not expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if the account is enabled.
     *
     * @return True if the account is enabled.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
