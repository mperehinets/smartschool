package com.mper.smartschool.entity;

import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.entity.modelsEnum.SchoolClassInitial;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "classes")
public class SchoolClass extends BaseEntity {

    @Column(name = "number", updatable = false)
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(name = "initial", updatable = false)
    private SchoolClassInitial initial;

    @Column(name = "season", updatable = false)
    private String season;

    @OneToOne
    @JoinColumn(name = "class_teacher", referencedColumnName = "user")
    private Teacher classTeacher;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", updatable = false)
    private EntityStatus status;

    public SchoolClass() {
    }

    @Builder
    public SchoolClass(Long id,
                       Integer number,
                       SchoolClassInitial initial,
                       String season,
                       Teacher classTeacher,
                       EntityStatus status) {
        super(id);
        this.number = number;
        this.initial = initial;
        this.season = season;
        this.classTeacher = classTeacher;
        this.status = status;
    }
}
