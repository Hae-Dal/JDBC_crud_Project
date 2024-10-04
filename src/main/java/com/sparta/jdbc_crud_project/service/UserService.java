package com.sparta.jdbc_crud_project.service;

import com.sparta.jdbc_crud_project.dto.UserRequestDto;
import com.sparta.jdbc_crud_project.dto.UserResponseDto;
import com.sparta.jdbc_crud_project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        return userRepository.save(userRequestDto);
    }

    public UserResponseDto getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll();
    }
}
