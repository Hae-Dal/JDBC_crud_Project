package com.sparta.jdbc_crud_project.dto;

import com.sparta.jdbc_crud_project.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime updateDate;
    private String userName;
    private String password;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getScheduleId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.postDate = schedule.getPostDate();
        this.updateDate = schedule.getUpdateDate();
        this.userName = schedule.getUserName();
        this.password = schedule.getPassword();
    }

}
