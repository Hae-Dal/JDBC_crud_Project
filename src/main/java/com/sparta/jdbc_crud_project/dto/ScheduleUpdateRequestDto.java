package com.sparta.jdbc_crud_project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleUpdateRequestDto {
    private Long id;
    private String content;
    private String userName;
    private String password;
}
