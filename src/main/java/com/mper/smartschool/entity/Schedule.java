package com.mper.smartschool.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "schedules")
public class Schedule extends BaseEntity {

    @Column(name = "date", updatable = false)
    private LocalDate date;

    @Column(name = "lesson_number")
    private Integer lessonNumber;

    @ManyToOne
    @JoinColumn(name = "class", referencedColumnName = "id", updatable = false)
    private SchoolClass schoolClass;

    @ManyToOne
    @JoinColumn(name = "teachers_subject", referencedColumnName = "id")
    private TeachersSubject teachersSubject;

    public Schedule() {
    }

    @Builder
    public Schedule(Long id,
                    LocalDate date,
                    Integer lessonNumber,
                    SchoolClass schoolClass,
                    TeachersSubject teachersSubject) {
        super(id);
        this.date = date;
        this.lessonNumber = lessonNumber;
        this.schoolClass = schoolClass;
        this.teachersSubject = teachersSubject;
    }
}
