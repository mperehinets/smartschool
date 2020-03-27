package com.mper.smartschool.dto;

import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChangeStatusDto extends BaseDto {
    EntityStatus newStatus;

    @Builder
    public ChangeStatusDto(Long id, EntityStatus newStatus) {
        super(id);
        this.newStatus = newStatus;
    }
}
