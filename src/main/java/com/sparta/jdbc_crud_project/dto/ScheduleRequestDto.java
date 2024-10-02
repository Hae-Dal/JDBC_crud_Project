package com.sparta.jdbc_crud_project.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleRequestDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime updateDate;
    private String userName;
    private String password;
}
