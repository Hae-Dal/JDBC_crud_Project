package com.sparta.jdbc_crud_project.controller;

import com.sparta.jdbc_crud_project.dto.ScheduleRequestDto;
import com.sparta.jdbc_crud_project.dto.ScheduleResponseDto;
import com.sparta.jdbc_crud_project.entitiy.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // Schedule Max ID Check
        Long maxId = !scheduleList.isEmpty() ? Collections.max(scheduleList.keySet()) + 1 : 1;
        schedule.setScheduleId(maxId);

        // Store to DB
        scheduleList.put(schedule.getScheduleId(), schedule);

        // Entity -> ResponseDto

        return new ScheduleResponseDto(schedule);
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules() {
        // Map to List
        return scheduleList.values().stream()
                .map(ScheduleResponseDto::new).toList();
    }
}
