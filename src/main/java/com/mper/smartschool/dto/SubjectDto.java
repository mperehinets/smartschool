package com.mper.smartschool.dto;

import com.mper.smartschool.model.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubjectDto extends BaseDto {

    private String name;

    private EntityStatus status;

    public SubjectDto() {
    }

    @Builder
    public SubjectDto(Long id, String name, EntityStatus status) {
        super(id);
        this.name = name;
        this.status = status;
    }
}
