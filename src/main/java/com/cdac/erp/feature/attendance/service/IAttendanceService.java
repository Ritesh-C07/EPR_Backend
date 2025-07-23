package com.cdac.erp.feature.attendance.service;

import com.cdac.erp.feature.attendance.dto.AttendanceRequest;
import com.cdac.erp.feature.attendance.dto.AttendanceResponse;
import com.cdac.erp.feature.attendance.dto.AttendanceUpdateRequest;
import com.cdac.erp.feature.attendance.dto.BulkAttendanceRequest;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAttendanceService {

    AttendanceResponse markAttendance(AttendanceRequest request);

    void markBulkAttendance(BulkAttendanceRequest request);

    Page<AttendanceResponse> getAttendanceForSession(Integer timetableEntryId, Pageable pageable);

    Page<AttendanceResponse> getAttendanceForSessionAndDate(Integer timetableEntryId, LocalDate date, Pageable pageable);

    AttendanceResponse updateAttendance(Integer attendanceId, AttendanceUpdateRequest request);

    void deleteAttendance(Integer attendanceId);
}