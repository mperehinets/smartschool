package com.mper.smartschool.model;

import com.mper.smartschool.model.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "pupils_has_signed_persons",
            joinColumns = {@JoinColumn(name = "pupil", referencedColumnName = "user")},
            inverseJoinColumns = {@JoinColumn(name = "signed_person", referencedColumnName = "id")})
    private List<SignedPerson> signedPersons;

    public Pupil() {
    }

    @Builder(builderMethodName = "pupilBuilder")
    public Pupil(Long id,
                 String firstName,
                 String secondName,
                 String email,
                 String password,
                 LocalDate dateBirth,
                 List<Role> roles,
                 EntityStatus status,
                 SchoolClass schoolClass,
                 List<SignedPerson> signedPersons) {
        super(id, firstName, secondName, email, password, dateBirth, roles, status);
        this.schoolClass = schoolClass;
        this.signedPersons = signedPersons;
    }
}
