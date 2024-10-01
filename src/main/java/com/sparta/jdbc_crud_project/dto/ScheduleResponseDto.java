package com.sparta.jdbc_crud_project.dto;

import com.sparta.jdbc_crud_project.entitiy.Schedule;
import lombok.Getter;

import java.util.Date;

@Getter
public class ScheduleResponseDto {
    private Long scheduleId;
    private String title;
    private String content;
    private Date postDate;
    private Date updateDate;
    private String userName;
    private String password;

    public ScheduleResponseDto(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.postDate = schedule.getPostDate();
        this.updateDate = schedule.getUpdateDate();
        this.userName = schedule.getUserName();
        this.password = schedule.getPassword();
    }
}
