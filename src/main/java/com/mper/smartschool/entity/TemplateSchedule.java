package com.mper.smartschool.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "templates_schedule")
public class TemplateSchedule extends BaseEntity {

    @Column(name = "class_number", updatable = false)
    private Integer classNumber;

    @Column(name = "lesson_number")
    private Integer lessonNumber;

    @ManyToOne
    @JoinColumn(name = "subject", referencedColumnName = "id")
    private Subject subject;

    public TemplateSchedule() {
    }

    @Builder
    public TemplateSchedule(Long id, Integer classNumber, Integer lessonNumber, Subject subject) {
        super(id);
        this.classNumber = classNumber;
        this.lessonNumber = lessonNumber;
        this.subject = subject;
    }
}
