package com.mper.smartschool.entity;

import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@PrimaryKeyJoinColumn(name = "user")
@Table(name = "pupils")
public class Pupil extends User {

    @ManyToOne
    @JoinColumn(name = "class", referencedColumnName = "id")
    private SchoolClass schoolClass;

    @ManyToMany
    @JoinTable(name = "pupils_has_signed_persons",
            joinColumns = {@JoinColumn(name = "pupil", referencedColumnName = "user")},
            inverseJoinColumns = {@JoinColumn(name = "signed_person", referencedColumnName = "id")})
    private Set<SignedPerson> signedPersons = new HashSet<>();

    public Pupil() {
    }

    @Builder(builderMethodName = "pupilBuilder")
    public Pupil(Long id,
                 String firstName,
                 String secondName,
                 String email,
                 String password,
                 LocalDate dateBirth,
                 String avatarName,
                 Set<Role> roles,
                 EntityStatus status,
                 SchoolClass schoolClass,
                 Set<SignedPerson> signedPersons) {
        super(id, firstName, secondName, email, password, dateBirth, avatarName, roles, status);
        this.schoolClass = schoolClass;
        this.signedPersons = signedPersons;
    }
}
