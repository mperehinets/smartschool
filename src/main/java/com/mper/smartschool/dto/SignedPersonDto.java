package com.mper.smartschool.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SignedPersonDto extends BaseDto {

    private String fullName;

    private String email;
}
