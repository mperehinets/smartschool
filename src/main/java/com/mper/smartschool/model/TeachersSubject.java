package com.mper.smartschool.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
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
}
