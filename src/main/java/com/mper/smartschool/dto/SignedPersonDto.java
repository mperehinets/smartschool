package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SignedPersonDto extends BaseDto {

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{signedPersonDto.fullName.notnull}")
    @Pattern(groups = {OnCreate.class, OnUpdate.class},
            regexp = "[A-Za-zА-Яа-яіІїЇєЄ`'\\- ]{3,60}",
            message = "{signedPersonDto.fullName.pattern}")
    private String fullName;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class},
            message = "{signedPersonDto.email.notblank}")
    @Email(groups = {OnCreate.class, OnUpdate.class},
            message = "{signedPersonDto.email.email}")
    private String email;

    public SignedPersonDto() {
    }

    @Builder
    public SignedPersonDto(Long id, String fullName, String email) {
        super(id);
        this.fullName = fullName;
        this.email = email;
    }
}
