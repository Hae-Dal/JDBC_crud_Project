package com.sparta.jdbc_crud_project.controller;

import com.sparta.jdbc_crud_project.dto.ErrorResponseDto;
import com.sparta.jdbc_crud_project.dto.ScheduleRequestDto;
import com.sparta.jdbc_crud_project.dto.ScheduleResponseDto;
import com.sparta.jdbc_crud_project.service.ScheduleService;
import com.sparta.jdbc_crud_project.exception.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto createdSchedule = scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity.ok().body(createdSchedule);
    }

    // 일정 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
        ScheduleResponseDto schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok().body(schedule);
    }

    // 전체 일정 조회 (페이지네이션 적용)
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
            @RequestParam int page,
            @RequestParam int size) {
        List<ScheduleResponseDto> schedules = scheduleService.getAllSchedulesByPage(page, size);
        int totalSchedules = scheduleService.countAllSchedules();
        return ResponseEntity.ok().body(schedules);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        try {
            scheduleRequestDto.setId(id);
            ScheduleResponseDto updatedSchedule = scheduleService.updateSchedule(scheduleRequestDto);
            return ResponseEntity.ok().body(updatedSchedule);
        } catch (InvalidPasswordException e) {
            ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    // 일정 삭제
    @DeleteMapping("/{id}/{password}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id, @PathVariable String password) {
        try {
            scheduleService.deleteSchedule(id, password);
            return ResponseEntity.ok().body(Map.of("message", "Schedule successfully deleted"));
        } catch (InvalidPasswordException e) {
            ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }
}
