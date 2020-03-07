package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PupilDto extends UserDto {

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{pupilDto.schoolClass.notnull}")
    private SchoolClassDto schoolClass;

    private Set<SignedPersonDto> signedPersons;

    public PupilDto() {
    }

    @Builder(builderMethodName = "pupilBuilder")
    public PupilDto(Long id,
                    String firstName,
                    String secondName,
                    String email,
                    String password,
                    LocalDate dateBirth,
                    String avatarName,
                    Set<Role> roles,
                    EntityStatus status,
                    SchoolClassDto schoolClass,
                    Set<SignedPersonDto> signedPersons) {
        super(id, firstName, secondName, email, password, dateBirth, avatarName, roles, status);
        this.schoolClass = schoolClass;
        this.signedPersons = signedPersons;
    }
}
