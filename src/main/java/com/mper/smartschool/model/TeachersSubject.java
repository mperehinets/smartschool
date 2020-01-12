package com.mper.smartschool.model;

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
@Table(name = "teachers_has_subjects")
public class TeachersSubject extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "teacher", referencedColumnName = "user")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "subject", referencedColumnName = "id")
    private Subject subject;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public TeachersSubject() {
    }

    @Builder
    public TeachersSubject(Long id, Teacher teacher, Subject subject, LocalDate startDate, LocalDate endDate) {
        super(id);
        this.teacher = teacher;
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
