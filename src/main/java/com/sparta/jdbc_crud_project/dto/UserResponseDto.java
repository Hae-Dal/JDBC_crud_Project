package com.sparta.jdbc_crud_project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String name;
    private String email;
    private LocalDateTime registrationDate;
    private LocalDateTime updatedDate;

    public UserResponseDto(Long userId, String name, String email, LocalDateTime registrationDate, LocalDateTime updatedDate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.registrationDate = registrationDate;
        this.updatedDate = updatedDate;
    }
}
