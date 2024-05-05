package com.vitisvision.vitisvisionservice.domain.group.entity;

import com.vitisvision.vitisvisionservice.common.entity.BaseEntity;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import com.vitisvision.vitisvisionservice.domain.vine.entity.Vine;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Group entity class.
 * This class is used to represent the group entity in the database.
 * The group entity represents a group of vines that are managed together.
 */
@Entity
@Table(name = "_group",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_group_name_vineyard_id",
                        columnNames = {
                                "name",
                                "vineyard_id"
                        }
                )
        })
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Group extends BaseEntity {

    /**
     * Represents the name of the group.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Represents the description of the group.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Represents the reason for the formation of the group.
     */
    @Column(nullable = false)
    private String formationReason;

    /**
     * Represents the vineyard that the group is associated with.
     */
    @ManyToOne
    @JoinColumn(name = "vineyard_id")
    private Vineyard vineyard;

    /**
     * Represents the list of vines in the group.
     */
    @ManyToMany
    @JoinTable(
            name = "group_vine",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "vine_id")
    )
    @ToString.Exclude
    List<Vine> vines;
}
