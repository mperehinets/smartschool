package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.dto.validator.password.Password;
import com.mper.smartschool.dto.validator.unique.Unique;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.service.UserService;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDto extends BaseDto {

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{userDto.firstName.notnull}")
    @Pattern(groups = {OnCreate.class, OnUpdate.class},
            regexp = "[A-Za-zА-Яа-яіІїЇєЄ`'\\- ]{3,60}",
            message = "{userDto.firstName.pattern}")
    private String firstName;

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{userDto.secondName.notnull}")
    @Pattern(groups = {OnCreate.class, OnUpdate.class},
            regexp = "[A-Za-zА-Яа-яіІїЇєЄ`'\\- ]{3,60}",
            message = "{userDto.secondName.pattern}")
    private String secondName;

    @NotBlank(groups = {OnCreate.class},
            message = "{userDto.email.notblank}")
    @Email(groups = {OnCreate.class},
            message = "{userDto.email.email}")
    @Unique(groups = {OnCreate.class},
            service = UserService.class,
            fieldName = "email",
            message = "{userDto.email.unique}")
    private String email;

    @NotNull(groups = {OnCreate.class},
            message = "{userDto.password.notnull}")
    @Password(groups = {OnCreate.class})
    private String password;

    @Past(groups = {OnCreate.class, OnUpdate.class}, message = "{userDto.dateBirth.past}")
    private LocalDate dateBirth;

    private String avatarName;

    private Set<Role> roles = new HashSet<>();

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
                   String avatarName,
                   Set<Role> roles,
                   EntityStatus status) {
        super(id);
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
        this.dateBirth = dateBirth;
        this.avatarName = avatarName;
        this.roles = roles;
        this.status = status;
    }
}
