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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String secondName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_has_roles",
            joinColumns = {@JoinColumn(name = "user", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role", referencedColumnName = "id")})
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
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
                List<Role> roles,
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
