package com.mper.smartschool.dto;

import com.mper.smartschool.model.Role;
import com.mper.smartschool.model.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDto extends BaseDto {

    private String firstName;

    private String secondName;

    private String email;

    private String password;

    private LocalDate dateBirth;

    private List<Role> roles;

    private EntityStatus status;

    public UserDto() {
    }

    @Builder(builderMethodName = "userBuilder")
    public UserDto(Long id,
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
