package com.mper.smartschool.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "homeworks")
public class Homework extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "schedule", referencedColumnName = "id")
    private Schedule schedule;

    @Column(name = "homework")
    private String homework;
}
