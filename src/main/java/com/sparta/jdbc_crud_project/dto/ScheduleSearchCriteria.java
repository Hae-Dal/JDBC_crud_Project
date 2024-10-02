package com.sparta.jdbc_crud_project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleSearchCriteria {
    private String updatedDate; // 형식: YYYY-MM-DD
    private String userName;
}
