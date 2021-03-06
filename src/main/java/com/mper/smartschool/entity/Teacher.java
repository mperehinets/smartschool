package com.mper.smartschool.entity;

import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@PrimaryKeyJoinColumn(name = "user")
@Table(name = "teachers")
public class Teacher extends User {

    @Column(name = "education")
    private String education;

    @OneToOne(mappedBy = "classTeacher")
    private SchoolClass schoolClass;

    public Teacher() {
    }

    @Builder(builderMethodName = "teacherBuilder")
    public Teacher(Long id,
                   String firstName,
                   String secondName,
                   String email,
                   String password,
                   LocalDate dateBirth,
                   String avatarName,
                   Set<Role> roles,
                   EntityStatus status,
                   String education) {
        super(id, firstName, secondName, email, password, dateBirth, avatarName, roles, status);
        this.education = education;
    }
}
