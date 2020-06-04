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
@Table(name = "subjects")
public class Subject extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "class_interval")
    private String schoolClassInterval;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EntityStatus status;

    public Subject() {
    }

    @Builder
    public Subject(Long id, String name, String schoolClassInterval, EntityStatus status) {
        super(id);
        this.status = status;
        this.name = name;
        this.schoolClassInterval = schoolClassInterval;
    }
}
