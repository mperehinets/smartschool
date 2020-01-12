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
public class PupilDto extends UserDto {

    private SchoolClassDto schoolClass;

    private List<SignedPersonDto> signedPersons;

    public PupilDto() {
    }

    @Builder(builderMethodName = "pupilBuilder")
    public PupilDto(Long id,
                    String firstName,
                    String secondName,
                    String email,
                    String password,
                    LocalDate dateBirth,
                    List<Role> roles,
                    EntityStatus status,
                    SchoolClassDto schoolClass,
                    List<SignedPersonDto> signedPersons) {
        super(id, firstName, secondName, email, password, dateBirth, roles, status);
        this.schoolClass = schoolClass;
        this.signedPersons = signedPersons;
    }
}
