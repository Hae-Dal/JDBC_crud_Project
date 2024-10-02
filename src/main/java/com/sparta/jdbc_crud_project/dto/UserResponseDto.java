package com.sparta.jdbc_crud_project.dto;

import com.sparta.jdbc_crud_project.entity.User;
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

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.registrationDate = user.getRegistrationDate();
        this.updatedDate = user.getUpdatedDate();
    }
}
