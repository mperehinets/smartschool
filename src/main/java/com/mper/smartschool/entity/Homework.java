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
@Table(name = "homeworks")
public class Homework extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "schedule", referencedColumnName = "id")
    private Schedule schedule;

    @Column(name = "homework")
    private String homework;

    public Homework() {
    }

    @Builder
    public Homework(Long id, Schedule schedule, String homework) {
        super(id);
        this.schedule = schedule;
        this.homework = homework;
    }
}
