package com.cdac.erp.feature.student.controller;

import com.cdac.erp.feature.feedback.dto.ActiveSessionResponse;
import com.cdac.erp.feature.grades.dto.ScoreResponse;
import com.cdac.erp.feature.student.dto.StudentDashboardStatsDto;
import com.cdac.erp.feature.student.dto.StudentResponse;
import com.cdac.erp.feature.student.dto.TodaysClassResponseDto;
import com.cdac.erp.feature.student.service.IStudentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student") // Base path is now shorter
public class StudentController {

    @Autowired
    private IStudentService studentService;

    // This path is now /api/student/dashboard/stats
    @GetMapping("/dashboard/stats")
    public ResponseEntity<StudentDashboardStatsDto> getDashboardStats(Authentication authentication) {
        String studentPrn = authentication.getName();
        StudentDashboardStatsDto stats = studentService.getStudentDashboardStats(studentPrn);
        return ResponseEntity.ok(stats);
    }

    // This path is now /api/student/dashboard/todays-classes
    @GetMapping("/dashboard/todays-classes")
    public ResponseEntity<List<TodaysClassResponseDto>> getTodaysClasses(Authentication authentication) {
        String studentPrn = authentication.getName();
        List<TodaysClassResponseDto> todaysClasses = studentService.getTodaysClasses(studentPrn);
        return ResponseEntity.ok(todaysClasses);
    }
    
    // This path is now /api/student/dashboard/pending-feedback
    @GetMapping("/dashboard/pending-feedback")
    public ResponseEntity<List<ActiveSessionResponse>> getPendingFeedbackTasks(Authentication authentication) {
        String studentPrn = authentication.getName();
        List<ActiveSessionResponse> pendingTasks = studentService.getPendingFeedbackTasks(studentPrn);
        return ResponseEntity.ok(pendingTasks);
    }

    // This path is now correctly /api/student/profile
    @GetMapping("/profile")
    public ResponseEntity<StudentResponse> getMyProfile(Authentication authentication) {
        String studentPrn = authentication.getName();
        StudentResponse studentProfile = studentService.getStudentByPrn(studentPrn);
        return ResponseEntity.ok(studentProfile);
    }
    
    //To show marks of individual students
    @GetMapping("/my-marks")
    public ResponseEntity<List<ScoreResponse>> getMyMarks(Authentication authentication) {
        String studentPrn = authentication.getName();
        List<ScoreResponse> marks = studentService.getMyMarks(studentPrn);
        return ResponseEntity.ok(marks);
    }
}