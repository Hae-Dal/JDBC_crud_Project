package com.sparta.jdbc_crud_project.repository;

import com.sparta.jdbc_crud_project.dto.ScheduleRequestDto;
import com.sparta.jdbc_crud_project.dto.ScheduleResponseDto;
import com.sparta.jdbc_crud_project.dto.ScheduleSearchCriteria;
import com.sparta.jdbc_crud_project.entity.Schedule;
import com.sparta.jdbc_crud_project.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 일정 추가
    public ScheduleResponseDto save(ScheduleRequestDto scheduleRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        String sql = "INSERT INTO SCHEDULE (title, content, postDate, updatedDate, userId, password) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, scheduleRequestDto.getTitle(), scheduleRequestDto.getContent(), now, now, scheduleRequestDto.getUserId(), scheduleRequestDto.getPassword());

        Long scheduleId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        return new ScheduleResponseDto(scheduleId, scheduleRequestDto.getTitle(), scheduleRequestDto.getContent(), now, now, scheduleRequestDto.getUserId(), scheduleRequestDto.getPassword());
    }

    // 일정 단건 조회
    public ScheduleResponseDto findById(Long scheduleId) {
        String sql = "SELECT * FROM SCHEDULE WHERE scheduleID = ?";
        return jdbcTemplate.queryForObject(sql, new ScheduleRowMapper(), scheduleId);
    }

    // 일정 전체 조회
    public List<ScheduleResponseDto> findAll(ScheduleSearchCriteria criteria) {
        StringBuilder sql = new StringBuilder("SELECT * FROM SCHEDULE WHERE 1=1");

        if (criteria.getUpdatedDate() != null) {
            sql.append(" AND DATE(updatedDate) = ?");
        }
        if (criteria.getUserName() != null) {
            sql.append(" AND userName = ?");
        }

        sql.append(" ORDER BY updatedDate DESC");

        return jdbcTemplate.query(sql.toString(), new ScheduleRowMapper(), getParams(criteria));
    }

    // 조회 조건 검사
    private Object[] getParams(ScheduleSearchCriteria criteria) {
        if (criteria.getUpdatedDate() != null && criteria.getUserName() != null) {
            return new Object[]{LocalDate.parse(criteria.getUpdatedDate()), criteria.getUserName()};
        } else if (criteria.getUpdatedDate() != null) {
            return new Object[]{LocalDate.parse(criteria.getUpdatedDate())};
        } else if (criteria.getUserName() != null) {
            return new Object[]{criteria.getUserName()};
        } else {
            return new Object[]{};
        }
    }

    // 일정 수정
    public ScheduleResponseDto update(ScheduleRequestDto scheduleRequestDto) {
        String sqlCheckPassword = "SELECT COUNT(*) FROM SCHEDULE WHERE scheduleID = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sqlCheckPassword, Integer.class, scheduleRequestDto.getId(), scheduleRequestDto.getPassword());

        if (count == null || count == 0) {
            throw new InvalidPasswordException("Invalid username or password");
        }

        String sqlGetSchedule = "SELECT * FROM SCHEDULE WHERE scheduleID = ?";
        ScheduleResponseDto existingSchedule = jdbcTemplate.queryForObject(sqlGetSchedule, new ScheduleRowMapper(), scheduleRequestDto.getId());

        LocalDateTime now = LocalDateTime.now();
        String sql = "UPDATE SCHEDULE SET content = ?, updatedDate = ?, userId = ? WHERE scheduleID = ?";
        jdbcTemplate.update(sql, scheduleRequestDto.getContent(), now, scheduleRequestDto.getUserId(), scheduleRequestDto.getId());

        scheduleRequestDto.setId(Objects.requireNonNull(existingSchedule).getId());
        scheduleRequestDto.setTitle(existingSchedule.getTitle());
        scheduleRequestDto.setPostDate(existingSchedule.getPostDate());
        scheduleRequestDto.setUpdateDate(now);

        return new ScheduleResponseDto(new Schedule(scheduleRequestDto));
    }

    // 일정 삭제
    public void delete(Long scheduleId, String password) {
        String sqlCheckPassword = "SELECT COUNT(*) FROM SCHEDULE WHERE scheduleID = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sqlCheckPassword, Integer.class, scheduleId, password);

        if (count == null || count == 0) {
            throw new InvalidPasswordException("Invalid password");
        }

        String sql = "DELETE FROM SCHEDULE WHERE scheduleID = ?";
        jdbcTemplate.update(sql, scheduleId);
    }

    private static class ScheduleRowMapper implements RowMapper<ScheduleResponseDto> {
        @Override
        public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            ScheduleResponseDto schedule = new ScheduleResponseDto();
            schedule.setId(rs.getLong("scheduleID"));
            schedule.setTitle(rs.getString("title"));
            schedule.setContent(rs.getString("content"));
            schedule.setPostDate(rs.getObject("postDate", LocalDateTime.class));
            schedule.setUpdateDate(rs.getObject("updatedDate", LocalDateTime.class));
            schedule.setUserId(rs.getLong("userId"));
            schedule.setPassword(rs.getString("password"));
            return schedule;
        }
    }
}
