package com.mper.smartschool.entity;

import com.mper.smartschool.entity.modelsEnum.PupilsLessonStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "pupils_success")
public class PupilSuccess extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "pupil", referencedColumnName = "user")
    private Pupil pupil;

    @ManyToOne
    @JoinColumn(name = "schedule", referencedColumnName = "id", updatable = false)
    private Schedule schedule;

    @Column(name = "rating")
    private Integer rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "pupils_lesson_status")
    private PupilsLessonStatus pupilsLessonStatus;

    public PupilSuccess() {
    }

    @Builder
    public PupilSuccess(Long id,
                        Pupil pupil,
                        Schedule schedule,
                        Integer rating,
                        PupilsLessonStatus pupilsLessonStatus) {
        super(id);
        this.pupil = pupil;
        this.schedule = schedule;
        this.rating = rating;
        this.pupilsLessonStatus = pupilsLessonStatus;
    }
}
