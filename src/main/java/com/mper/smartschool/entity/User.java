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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String secondName;

    @Column(name = "email", updatable = false)
    private String email;

    @Column(name = "password", updatable = false)
    private String password;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_has_roles",
            joinColumns = {@JoinColumn(name = "user", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", updatable = false)
    private EntityStatus status;

    public User() {
    }

    @Builder(builderMethodName = "userBuilder")
    public User(Long id,
                String firstName,
                String secondName,
                String email,
                String password,
                LocalDate dateBirth,
                Set<Role> roles,
                EntityStatus status) {
        super(id);
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
        this.dateBirth = dateBirth;
        this.roles = roles;
        this.status = status;
    }
}
