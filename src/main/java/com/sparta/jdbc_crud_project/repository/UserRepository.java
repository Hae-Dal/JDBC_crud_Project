package com.sparta.jdbc_crud_project.repository;

import com.sparta.jdbc_crud_project.dto.UserRequestDto;
import com.sparta.jdbc_crud_project.dto.UserResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserResponseDto save(UserRequestDto userRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        String sql = "INSERT INTO USER (name, email, registrationDate, updatedDate) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, userRequestDto.getName(), userRequestDto.getEmail(), now, now);

        Long userId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        return new UserResponseDto(userId, userRequestDto.getName(), userRequestDto.getEmail(), now, now);
    }

    public UserResponseDto findById(Long userId) {
        String sql = "SELECT * FROM USER WHERE userId = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), userId);
    }

    public List<UserResponseDto> findAll() {
        String sql = "SELECT * FROM USER";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    private static class UserRowMapper implements RowMapper<UserResponseDto> {
        @Override
        public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UserResponseDto(
                    rs.getLong("userId"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getObject("registrationDate", LocalDateTime.class),
                    rs.getObject("updatedDate", LocalDateTime.class)
            );
        }
    }
}
