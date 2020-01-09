package com.mper.smartschool.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "templates_schedule")
public class TemplateSchedule extends BaseEntity {

    @Column(name = "class_number")
    private Integer classNumber;

    @Column(name = "lesson_number")
    private Integer lessonNumber;

    @ManyToOne
    @JoinColumn(name = "teachers_subject", referencedColumnName = "id")
    private TeachersSubject teachersSubject;
}
