package com.vitisvision.vitisvisionservice.common.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>Base entity class for all entities in the application.</p>
 * This class is used to provide common fields to all entities in the application.
 */
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    /**
     * <p>The id of the entity. This is the primary key of the entity and is auto generated.</p>
     * Generation strategy is determined by the database.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * <p>The created at field of the entity. This field is used to store the date and time when the entity was created.</p>
     * This field is automatically set by the application and is not updatable.
     */
    @CreatedDate
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    /**
     * <p>The last updated at field of the entity. This field is used to store the date and time when the entity was last updated.</p>
     * This field is automatically set by the application and is not insertable.
     */
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastUpdatedAt;

    /**
     * <p>The created by field of the entity. This field is used to store the user who created the entity.</p>
     * This field is automatically set by the application and is not updatable.
     */
    @CreatedBy
    @Column(
            nullable = false,
            updatable = false
    )
    private String createdBy;

    /**
     * <p>The last updated by field of the entity. This field is used to store the user who last updated the entity.</p>
     * This field is automatically set by the application and is not insertable.
     */
    @LastModifiedBy
    @Column(insertable = false)
    private String lastUpdatedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BaseEntity that = (BaseEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
