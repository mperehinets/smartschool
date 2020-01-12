package com.mper.smartschool.model;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "signed_persons")
public class SignedPerson extends BaseEntity {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    public SignedPerson() {
    }

    @Builder
    public SignedPerson(Long id, String fullName, String email) {
        super(id);
        this.fullName = fullName;
        this.email = email;
    }
}
