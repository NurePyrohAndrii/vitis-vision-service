package com.vitisvision.vitisvisionservice.domain.vine.entity;

import com.vitisvision.vitisvisionservice.common.entity.BaseEntity;
import com.vitisvision.vitisvisionservice.domain.block.entity.Block;
import com.vitisvision.vitisvisionservice.domain.device.entity.Device;
import com.vitisvision.vitisvisionservice.domain.group.entity.Group;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

/**
 * Vine entity class.
 * This class is used to represent the vine entity in the database.
 * The vine entity represents a vine in a block.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_vine_block_id_vineNumber_rowNumber",
                columnNames = {
                        "block_id",
                        "vine_number",
                        "row_number"
                }
        )
})
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Vine extends BaseEntity {

    /**
     * Represents the vine number.
     */
    @Column(nullable = false)
    private Integer vineNumber;

    /**
     * Represents the row number of the vine.
     */
    @Column(nullable = false)
    private Integer rowNumber;

    /**
     * Represents the variety of the vine.
     */
    @Column(nullable = false)
    private String variety;

    /**
     * Represents the count of boles in the vine.
     */
    @Column(nullable = false)
    private Integer bolesCount;

    /**
     * Represents the planting date of the vine.
     */
    @Column(nullable = false)
    private LocalDate plantingDate;

    /**
     * Represents the formation type of the vine.
     */
    @Column(nullable = false)
    private String formationType;

    /**
     * Represents the block that the vine is associated with.
     */
    @ManyToOne
    @JoinColumn(name = "block_id")
    private Block block;

    /**
     * Represents the groups that the vine is associated with.
     */
    @ManyToMany(mappedBy = "vines")
    @ToString.Exclude
    private List<Group> groups;

    /**
     * Represents the device that the vine is associated with.
     */
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "device_id")
    private Device device;

}
