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
    private Long userId;
    private String password;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getScheduleId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.postDate = schedule.getPostDate();
        this.updateDate = schedule.getUpdateDate();
        this.userId = schedule.getUserId();
    }

    public ScheduleResponseDto(Long id, String title, String content, LocalDateTime postDate, LocalDateTime updateDate, Long userId, String password) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postDate = postDate;
        this.updateDate = updateDate;
        this.userId = userId;
        this.password = password;
    }
}
