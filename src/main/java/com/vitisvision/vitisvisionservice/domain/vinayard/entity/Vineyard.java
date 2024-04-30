package com.vitisvision.vitisvisionservice.domain.vinayard.entity;

import com.vitisvision.vitisvisionservice.common.entity.BaseEntity;
import com.vitisvision.vitisvisionservice.domain.block.entity.Block;
import com.vitisvision.vitisvisionservice.domain.group.entity.Group;
import com.vitisvision.vitisvisionservice.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Vineyard extends BaseEntity {

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

    /**
     * Represents the blocks that are associated with the vineyard.
     */
    @OneToMany(mappedBy = "vineyard", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Block> blocks;

    /**
     * Represents the groups that are associated with the vineyard.
     */
    @OneToMany(mappedBy = "vineyard", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Group> groups;

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
