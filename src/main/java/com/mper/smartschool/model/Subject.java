package com.mper.smartschool.model;

import com.mper.smartschool.model.modelsEnum.EntityStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "subjects")
public class Subject extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EntityStatus status;
}
