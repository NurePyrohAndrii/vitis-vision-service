package com.vitisvision.vitisvisionservice.domain.block.entity;

import com.vitisvision.vitisvisionservice.common.entity.BaseEntity;
import com.vitisvision.vitisvisionservice.domain.vinayard.entity.Vineyard;
import com.vitisvision.vitisvisionservice.domain.vine.entity.Vine;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Block entity class.
 * This class is used to represent the vineyard structure block in the database.
 *
 * @see BaseEntity
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_block_name_vineyard_id",
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
public class Block extends BaseEntity {

    /**
     * The name of the block.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Partitioning type represents the principle of partitioning vineyard into blocks.
     */
    @Column(nullable = false)
    private String partitioningType;

    /**
     * Represents the row spacing in centimeters.
     * Row spacing is the distance between the rows of vines in a vineyard.
     */
    @Column(nullable = false)
    private double rowSpacing;

    /**
     * Represents the vine spacing in centimeters.
     * Vine spacing is the distance between the vines in a row.
     */
    @Column(nullable = false)
    private double vineSpacing;

    /**
     * Represents the row orientation in the vineyard.
     * Row orientation is the direction of the rows in the vineyard.
     */
    @Column(nullable = false)
    private String rowOrientation;

    /**
     * Represents the trellis system type used in the block.
     * Trellis system is the structure that supports the vine.
     */
    @Column(nullable = false)
    private String trellisSystemType;

    /**
     * Represents the vineyard that the block belongs to.
     *
     * @see Vineyard
     */
    @ManyToOne
    @JoinColumn(name = "vineyard_id")
    private Vineyard vineyard;

    /**
     * Represents the vines in the block.
     *
     * @see Vine
     */
    @OneToMany(mappedBy = "block", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Vine> vines;
}
