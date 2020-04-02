package com.mper.smartschool.entity;

import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EntityStatus status;

    public TeachersSubject() {
    }

    @Builder
    public TeachersSubject(Long id, Teacher teacher, Subject subject, EntityStatus status) {
        super(id);
        this.teacher = teacher;
        this.subject = subject;
        this.status = status;
    }
}
