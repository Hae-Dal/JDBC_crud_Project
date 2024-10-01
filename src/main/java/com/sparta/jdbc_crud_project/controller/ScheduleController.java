package com.sparta.jdbc_crud_project.controller;

import com.sparta.jdbc_crud_project.dto.ScheduleRequestDto;
import com.sparta.jdbc_crud_project.dto.ScheduleResponseDto;
import com.sparta.jdbc_crud_project.entitiy.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // Store to DB
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO schedule (title, content, postDate, updatedDate, userName, password) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, schedule.getTitle());
            preparedStatement.setString(2, schedule.getContent());
            preparedStatement.setDate(3, schedule.getPostDate());
            preparedStatement.setDate(4, schedule.getUpdateDate());
            preparedStatement.setString(5, schedule.getUserName());
            preparedStatement.setString(6, schedule.getPassword());

            return preparedStatement;
        }, keyHolder);

        // Schedule Max ID Check
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        schedule.setScheduleId(id);

        // Entity -> ResponseDto
        return new ScheduleResponseDto(schedule);
    }

}
