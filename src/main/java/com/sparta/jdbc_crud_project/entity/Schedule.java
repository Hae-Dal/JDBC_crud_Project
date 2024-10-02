package com.sparta.jdbc_crud_project.entity;

import com.sparta.jdbc_crud_project.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long scheduleId;
    private String title;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime updateDate;
    private String userName;
    private String password;

    public Schedule(ScheduleRequestDto requestDto) {
        this.scheduleId = requestDto.getId();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.postDate = requestDto.getPostDate();
        this.updateDate = requestDto.getUpdateDate();
        this.userName = requestDto.getUserName();
        this.password = requestDto.getPassword();
    }
}
