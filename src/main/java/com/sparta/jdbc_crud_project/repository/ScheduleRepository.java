package com.sparta.jdbc_crud_project.repository;

import com.sparta.jdbc_crud_project.dto.ScheduleRequestDto;
import com.sparta.jdbc_crud_project.dto.ScheduleResponseDto;
import com.sparta.jdbc_crud_project.dto.ScheduleSearchCriteria;
import com.sparta.jdbc_crud_project.entity.Schedule;
import com.sparta.jdbc_crud_project.exception.InvalidPasswordException;
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

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 일정 추가
    public ScheduleResponseDto save(ScheduleRequestDto scheduleRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        String sql = "INSERT INTO SCHEDULE (title, content, postDate, updatedDate, userName, password) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, scheduleRequestDto.getTitle(), scheduleRequestDto.getContent(), now, now, scheduleRequestDto.getUserName(), scheduleRequestDto.getPassword());

        Long scheduleId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        scheduleRequestDto.setId(scheduleId);
        scheduleRequestDto.setPostDate(now);
        scheduleRequestDto.setUpdateDate(now);

        return new ScheduleResponseDto(new Schedule(scheduleRequestDto));
    }

    // 단건 일정 조회
    public ScheduleResponseDto findById(Long scheduleId) {
        String sql = "SELECT * FROM SCHEDULE WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ScheduleRowMapper(), scheduleId);
    }

    // 전체 일정 조회
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

        String sqlCheckPassword = "SELECT password FROM SCHEDULE WHERE id = ?";
        String existingPassword = jdbcTemplate.queryForObject(sqlCheckPassword, String.class, scheduleRequestDto.getId());

        if (!Objects.requireNonNull(existingPassword).equals(scheduleRequestDto.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        String sqlGetSchedule = "SELECT * FROM SCHEDULE WHERE id = ?";
        ScheduleResponseDto existingSchedule = jdbcTemplate.queryForObject(sqlGetSchedule, new ScheduleRowMapper(), scheduleRequestDto.getId());

        LocalDateTime now = LocalDateTime.now();
        String sql = "UPDATE SCHEDULE SET content = ?, updatedDate = ?, userName = ? WHERE id = ?";
        jdbcTemplate.update(sql, scheduleRequestDto.getContent(), now, scheduleRequestDto.getUserName(), scheduleRequestDto.getId());

        scheduleRequestDto.setId(Objects.requireNonNull(existingSchedule).getId());
        scheduleRequestDto.setUpdateDate(now);

        return new ScheduleResponseDto(new Schedule(scheduleRequestDto));
    }

    // 일정 삭제
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

