package com.cdac.erp.feature.student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentDashboardStatsDto {
    private long totalCourses;
    private double attendancePercentage;
}