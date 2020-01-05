package com.mper.smartschool.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
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
}
