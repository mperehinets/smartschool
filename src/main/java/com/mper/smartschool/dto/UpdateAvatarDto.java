package com.mper.smartschool.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateAvatarDto extends BaseDto {

    private String newAvatarName;

    @Builder
    public UpdateAvatarDto(Long id, String newAvatarName) {
        super(id);
        this.newAvatarName = newAvatarName;
    }
}
