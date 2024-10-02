package com.sparta.jdbc_crud_project.entity;

import com.sparta.jdbc_crud_project.dto.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long userId;
    private String name;
    private String email;
    private LocalDateTime registrationDate;
    private LocalDateTime updatedDate;

    public User(UserRequestDto requestDto) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.registrationDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    public User(Long userId, String name, String email, LocalDateTime registrationDate, LocalDateTime updatedDate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.registrationDate = registrationDate;
        this.updatedDate = updatedDate;
    }
}
