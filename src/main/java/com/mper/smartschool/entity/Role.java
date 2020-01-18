package com.mper.smartschool.entity;

import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EntityStatus status;

    public Role() {
    }

    @Builder
    public Role(Long id, String name, EntityStatus status) {
        super(id);
        this.name = name;
        this.status = status;
    }
}
