package com.sparta.jdbc_crud_project.entitiy;

import com.sparta.jdbc_crud_project.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long scheduleId;
    private String title;
    private String content;
    private Date postDate;
    private Date updateDate;
    private String userName;
    private String password;

    public Schedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.postDate = requestDto.getPostDate();
        this.updateDate = requestDto.getUpdateDate();
        this.userName = requestDto.getUserName();
        this.password = requestDto.getPassword();
    }
}
