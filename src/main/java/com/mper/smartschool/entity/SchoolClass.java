package com.mper.smartschool.entity;

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

    @Column(name = "number")
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(name = "initial")
    private SchoolClassInitial initial;

    @OneToOne
    @JoinColumn(name = "class_teacher", referencedColumnName = "user")
    private Teacher classTeacher;

    public SchoolClass() {
    }

    @Builder
    public SchoolClass(Long id,
                       Integer number,
                       SchoolClassInitial initial,
                       Teacher classTeacher) {
        super(id);
        this.number = number;
        this.initial = initial;
        this.classTeacher = classTeacher;
    }
}
