package com.mper.smartschool.model;

import com.mper.smartschool.model.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
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

    public Teacher() {
    }

    @Builder(builderMethodName = "teacherBuilder")
    public Teacher(Long id,
                   String firstName,
                   String secondName,
                   String email,
                   String password,
                   LocalDate dateBirth,
                   Set<Role> roles,
                   EntityStatus status,
                   String education) {
        super(id, firstName, secondName, email, password, dateBirth, roles, status);
        this.education = education;
    }
}
