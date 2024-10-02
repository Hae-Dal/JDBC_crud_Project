package com.sparta.jdbc_crud_project.repository;

import com.sparta.jdbc_crud_project.dto.ScheduleRequestDto;
import com.sparta.jdbc_crud_project.dto.ScheduleResponseDto;
import com.sparta.jdbc_crud_project.dto.ScheduleUpdateRequestDto;
import com.sparta.jdbc_crud_project.exception.InvalidPasswordException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ScheduleResponseDto save(ScheduleRequestDto scheduleRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        String sql = "INSERT INTO SCHEDULE (title, content, postDate, updatedDate, userName, password) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, scheduleRequestDto.getTitle(), scheduleRequestDto.getContent(), now, now, scheduleRequestDto.getUserName(), scheduleRequestDto.getPassword());

        Long scheduleId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        return new ScheduleResponseDto(scheduleId, scheduleRequestDto.getTitle(), scheduleRequestDto.getContent(), now, now, scheduleRequestDto.getUserName(), scheduleRequestDto.getPassword());
    }


    public ScheduleResponseDto findById(Long scheduleId) {
        String sql = "SELECT * FROM SCHEDULE WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ScheduleRowMapper(), scheduleId);
    }

    public List<ScheduleResponseDto> findAll() {
        String sql = "SELECT * FROM SCHEDULE";
        return jdbcTemplate.query(sql, new ScheduleRowMapper());
    }

    public ScheduleResponseDto update(ScheduleUpdateRequestDto scheduleUpdateRequestDto) {

        String sqlCheckPassword = "SELECT password FROM SCHEDULE WHERE id = ?";
        String existingPassword = jdbcTemplate.queryForObject(sqlCheckPassword, String.class, scheduleUpdateRequestDto.getId());

        if (!Objects.requireNonNull(existingPassword).equals(scheduleUpdateRequestDto.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        String sqlGetSchedule = "SELECT * FROM SCHEDULE WHERE id = ?";
        ScheduleResponseDto existingSchedule = jdbcTemplate.queryForObject(sqlGetSchedule, new ScheduleRowMapper(), scheduleUpdateRequestDto.getId());

        LocalDateTime now = LocalDateTime.now();
        String sql = "UPDATE SCHEDULE SET content = ?, updatedDate = ?, userName = ? WHERE id = ?";
        jdbcTemplate.update(sql, scheduleUpdateRequestDto.getContent(), now, scheduleUpdateRequestDto.getUserName(), scheduleUpdateRequestDto.getId());

        return new ScheduleResponseDto(
                Objects.requireNonNull(existingSchedule).getId(),
                existingSchedule.getTitle(),
                scheduleUpdateRequestDto.getContent(),
                existingSchedule.getPostDate(),
                now,
                scheduleUpdateRequestDto.getUserName(),
                existingSchedule.getPassword()
        );
    }

    public void delete(Long scheduleId) {
        String sql = "DELETE FROM SCHEDULE WHERE id = ?";
        jdbcTemplate.update(sql, scheduleId);
    }

    private static class ScheduleRowMapper implements RowMapper<ScheduleResponseDto> {
        @Override
        public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            ScheduleResponseDto schedule = new ScheduleResponseDto();
            schedule.setId(rs.getLong("id"));
            schedule.setTitle(rs.getString("title"));
            schedule.setContent(rs.getString("content"));
            schedule.setPostDate(rs.getObject("postDate", LocalDateTime.class));
            schedule.setUpdateDate(rs.getObject("updatedDate", LocalDateTime.class));
            schedule.setUserName(rs.getString("userName"));
            schedule.setPassword(rs.getString("password"));
            return schedule;
        }
    }
}

