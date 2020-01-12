package com.mper.smartschool.model;

import com.mper.smartschool.model.modelsEnum.EntityStatus;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EntityStatus status;

    public Subject() {
    }

    @Builder
    public Subject(Long id, EntityStatus status) {
        super(id);
        this.status = status;
    }
}
