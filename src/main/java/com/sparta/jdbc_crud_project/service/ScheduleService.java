package com.sparta.jdbc_crud_project.service;

import com.sparta.jdbc_crud_project.dto.ScheduleRequestDto;
import com.sparta.jdbc_crud_project.dto.ScheduleResponseDto;
import com.sparta.jdbc_crud_project.dto.ScheduleSearchCriteria;
import com.sparta.jdbc_crud_project.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        return scheduleRepository.save(scheduleRequestDto);
    }

    public ScheduleResponseDto getScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId);
    }

    public List<ScheduleResponseDto> getAllSchedules(ScheduleSearchCriteria criteria) {
        return scheduleRepository.findAll(criteria);
    }

    public ScheduleResponseDto updateSchedule(ScheduleRequestDto scheduleRequestDto) {
        return scheduleRepository.update(scheduleRequestDto);
    }

    public void deleteSchedule(Long id, String password) {
        scheduleRepository.delete(id, password);
    }
}
