package com.sparta.jdbc_crud_project.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class ScheduleRequestDto {
    private String title;
    private String content;
    private Date postDate;
    private Date updateDate;
    private String userName;
    private String password;
}
