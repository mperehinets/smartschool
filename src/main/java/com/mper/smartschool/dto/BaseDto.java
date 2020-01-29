package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;


@Data
public abstract class BaseDto {

    @Null(groups = {OnCreate.class}, message = "{baseDto.id.null}")
    @NotNull(groups = {OnUpdate.class}, message = "{baseDto.id.notnull}")
    private Long id;

    protected BaseDto() {
    }

    protected BaseDto(Long id) {
        this.id = id;
    }
}
