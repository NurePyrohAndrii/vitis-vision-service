package com.vitisvision.vitisvisionservice.domain.vinayard.entity;

import com.vitisvision.vitisvisionservice.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

/**
 * Vineyard entity class.
 * This class is used to represent the vineyard entity in the database.
 */
@Entity
@Table
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vineyard {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    /**
     * Represents the company that owns the vineyard.
     */
    @Embedded
    private Company company;

    /**
     * Represents users that are associated with the vineyard.
     */
    @OneToMany(mappedBy = "vineyard")
    @ToString.Exclude
    private List<User> users;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Vineyard vineyard = (Vineyard) o;
        return getId() != null && Objects.equals(getId(), vineyard.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
