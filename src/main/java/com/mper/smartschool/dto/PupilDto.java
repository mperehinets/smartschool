package com.mper.smartschool.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PupilDto extends UserDto {

    private SchoolClassDto schoolClass;

    private List<SignedPersonDto> signedPersons;
}
