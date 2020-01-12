package com.mper.smartschool.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SignedPersonDto extends BaseDto {

    private String fullName;

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
