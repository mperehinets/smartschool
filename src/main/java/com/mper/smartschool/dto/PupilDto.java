package com.mper.smartschool.dto;

import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PupilDto extends UserDto {

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
                    Set<Role> roles,
                    EntityStatus status,
                    SchoolClassDto schoolClass,
                    Set<SignedPersonDto> signedPersons) {
        super(id, firstName, secondName, email, password, dateBirth, roles, status);
        this.schoolClass = schoolClass;
        this.signedPersons = signedPersons;
    }
}
