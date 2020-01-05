package com.mper.smartschool.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "signed_persons")
public class SignedPerson extends BaseEntity {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;
}
