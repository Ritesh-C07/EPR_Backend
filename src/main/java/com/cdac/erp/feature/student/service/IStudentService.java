package com.cdac.erp.feature.student.service;


import com.cdac.erp.feature.feedback.dto.ActiveSessionResponse;
import com.cdac.erp.feature.grades.dto.ScoreResponse;
// ... imports
import com.cdac.erp.feature.student.dto.StudentCreateRequest;
import com.cdac.erp.feature.student.dto.StudentDashboardStatsDto;
import com.cdac.erp.feature.student.dto.StudentResponse;
import com.cdac.erp.feature.student.dto.StudentUpdateRequest;
import com.cdac.erp.feature.student.dto.TodaysClassResponseDto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStudentService {
	StudentResponse createStudent(StudentCreateRequest createRequest); // Changed

	StudentResponse updateStudent(String prn, StudentUpdateRequest updateRequest); // Changed

	void deleteStudent(String prn);

	StudentResponse getStudentByPrn(String prn);

//	List<StudentResponse> getAllStudents();
	Page<StudentResponse> getAllStudents(Pageable pageable); 
	
	//dashboard
	StudentDashboardStatsDto getStudentDashboardStats(String studentPrn);
	
	
	List<TodaysClassResponseDto> getTodaysClasses(String studentPrn);
	
	//widget on dashboard
	List<ActiveSessionResponse> getPendingFeedbackTasks(String studentPrn);
	
	//showing students in marks tab
	List<ScoreResponse> getMyMarks(String studentPrn);
}