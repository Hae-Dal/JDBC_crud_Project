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

    public ScheduleResponseDto(Long id, String title, String content, LocalDateTime postDate, LocalDateTime updateDate, String userName, String password) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postDate = postDate;
        this.updateDate = updateDate;
        this.userName = userName;
        this.password = password;
    }
}
