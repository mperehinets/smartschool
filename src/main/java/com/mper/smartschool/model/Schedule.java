package com.mper.smartschool.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "schedules")
public class Schedule extends BaseEntity {

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "lesson_number")
    private Integer lessonNumber;

    @ManyToOne
    @JoinColumn(name = "class", referencedColumnName = "id")
    private SchoolClass schoolClass;

    @ManyToOne
    @JoinColumn(name = "teachers_subject", referencedColumnName = "id")
    private TeachersSubject teachersSubject;
}
