package com.mper.smartschool.model;

import com.mper.smartschool.model.modelsEnum.PupilsLessonStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pupils_success")
public class PupilSuccess extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "pupil", referencedColumnName = "user")
    private Pupil pupil;

    @ManyToOne
    @JoinColumn(name = "schedule", referencedColumnName = "id")
    private Schedule schedule;

    @Column(name = "rating")
    private Integer rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "pupils_lesson_status")
    private PupilsLessonStatus pupilsLessonStatus;
}
