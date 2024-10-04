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
    private Long scheduleID;
    private String title;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime updateDate;
    private Long userId;

    public Schedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.postDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        this.userId = requestDto.getUserId();
    }
}
