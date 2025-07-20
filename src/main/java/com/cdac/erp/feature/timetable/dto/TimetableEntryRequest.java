package com.cdac.erp.feature.timetable.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TimetableEntryRequest {
    private String moduleId;
    private Integer instructorId;
    private LocalDate lectureDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek;
    private String roomNumber;
    private boolean isLab;
}