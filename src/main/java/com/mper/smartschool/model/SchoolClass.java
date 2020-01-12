package com.mper.smartschool.model;

import com.mper.smartschool.model.converter.YearAttributeConverter;
import com.mper.smartschool.model.modelsEnum.EntityStatus;
import com.mper.smartschool.model.modelsEnum.SchoolClassInitial;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.Year;

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

    @Convert(converter = YearAttributeConverter.class)
    @Column(name = "year")
    private Year year;

    @OneToOne
    @JoinColumn(name = "class_teacher", referencedColumnName = "user")
    private Teacher classTeacher;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EntityStatus status;

    public SchoolClass() {
    }

    @Builder
    public SchoolClass(Long id,
                       Integer number,
                       SchoolClassInitial initial,
                       Year year,
                       Teacher classTeacher,
                       EntityStatus status) {
        super(id);
        this.number = number;
        this.initial = initial;
        this.year = year;
        this.classTeacher = classTeacher;
        this.status = status;
    }
}
