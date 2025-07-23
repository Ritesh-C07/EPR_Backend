package com.cdac.erp.feature.student.service;

import com.cdac.erp.common.exception.ResourceNotFoundException;
import com.cdac.erp.core.model.Attendance;
import com.cdac.erp.core.model.Department;
import com.cdac.erp.core.model.FeedbackSession;
import com.cdac.erp.core.model.Score;
import com.cdac.erp.core.model.Student;
import com.cdac.erp.core.model.TimetableEntry;
import com.cdac.erp.core.repository.AttendanceRepository;
import com.cdac.erp.core.repository.DepartmentRepository;
import com.cdac.erp.core.repository.FeedbackSessionRepository;
import com.cdac.erp.core.repository.ModuleRepository;
import com.cdac.erp.core.repository.ScoreRepository;
import com.cdac.erp.core.repository.StudentRepository;
import com.cdac.erp.core.repository.TimetableEntryRepository;
import com.cdac.erp.feature.feedback.dto.ActiveSessionResponse;
import com.cdac.erp.feature.grades.dto.ScoreResponse;
import com.cdac.erp.feature.student.dto.StudentCreateRequest;
import com.cdac.erp.feature.student.dto.StudentDashboardStatsDto;
import com.cdac.erp.feature.student.dto.StudentResponse;
import com.cdac.erp.feature.student.dto.StudentUpdateRequest;
import com.cdac.erp.feature.student.dto.TodaysClassResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private ScoreRepository scoreRepository;
	
	//for dashboard
	@Autowired
	private ModuleRepository moduleRepository;
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired private TimetableEntryRepository timetableEntryRepository;
	
	@Autowired private FeedbackSessionRepository feedbackSessionRepository;


	@Override
	public StudentResponse createStudent(StudentCreateRequest createRequest) {
		Department department = departmentRepository.findById(createRequest.getDepartmentId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Department not found with id: " + createRequest.getDepartmentId()));

		Student student = new Student();
		// ... set fields from createRequest
		student.setPrn(createRequest.getPrn());
		student.setFirstName(createRequest.getFirstName());
		student.setLastName(createRequest.getLastName());
		student.setEmail(createRequest.getEmail());
		student.setPhoneNumber(createRequest.getPhoneNumber());
		student.setDateOfBirth(createRequest.getDateOfBirth());
		student.setAddress(createRequest.getAddress());
		student.setDepartment(department);
		student.setPasswordHash(passwordEncoder.encode(createRequest.getPassword()));

		Student savedStudent = studentRepository.save(student);
		return convertToDto(savedStudent);
	}

	@Override
	@Transactional
	public StudentResponse updateStudent(String prn, StudentUpdateRequest updateRequest) {
		Student existingStudent = studentRepository.findById(prn)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with prn: " + prn));
		Department department = departmentRepository.findById(updateRequest.getDepartmentId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Department not found with id: " + updateRequest.getDepartmentId()));

		// ... set fields from updateRequest
		existingStudent.setFirstName(updateRequest.getFirstName());
		existingStudent.setLastName(updateRequest.getLastName());
		existingStudent.setEmail(updateRequest.getEmail());
		existingStudent.setPhoneNumber(updateRequest.getPhoneNumber());
		existingStudent.setDateOfBirth(updateRequest.getDateOfBirth());
		existingStudent.setAddress(updateRequest.getAddress());
		existingStudent.setDepartment(department);

		Student updatedStudent = studentRepository.save(existingStudent);
		return convertToDto(updatedStudent);
	}

	@Override
	public void deleteStudent(String prn) {
		if (!studentRepository.existsById(prn)) {
			throw new ResourceNotFoundException("Student not found with prn: " + prn);
		}
		studentRepository.deleteById(prn);
	}

	@Override
	public StudentResponse getStudentByPrn(String prn) {
		Student student = studentRepository.findById(prn)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with prn: " + prn));
		return convertToDto(student);
	}

//    @Override
//    public List<StudentResponse> getAllStudents() {
//        return studentRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
//    }

	@Override
	public Page<StudentResponse> getAllStudents(Pageable pageable) {
		Page<Student> studentPage = studentRepository.findAll(pageable);
		return studentPage.map(this::convertToDto);
	}

	private StudentResponse convertToDto(Student student) {
		StudentResponse dto = new StudentResponse();
		dto.setPrn(student.getPrn());
		dto.setFirstName(student.getFirstName());
		dto.setLastName(student.getLastName());
		dto.setEmail(student.getEmail());
		dto.setPhoneNumber(student.getPhoneNumber());
		dto.setDateOfBirth(student.getDateOfBirth());
		dto.setAddress(student.getAddress());
		if (student.getDepartment() != null) {
			dto.setDepartmentId(student.getDepartment().getDepartmentId());
			dto.setDepartmentName(student.getDepartment().getDepartmentName());
		}
		return dto;
	}

	// dashboard cards
	@Override
	public StudentDashboardStatsDto getStudentDashboardStats(String studentPrn) {
	    Student student = studentRepository.findById(studentPrn)
	            .orElseThrow(() -> new ResourceNotFoundException("Student not found with prn: " + studentPrn));

	    long totalCourses = moduleRepository.countByDepartment_DepartmentId(student.getDepartment().getDepartmentId());

	    long totalAttendanceRecords = attendanceRepository.countByStudentPrn(studentPrn);
	    long presentRecords = attendanceRepository.countByStudentPrnAndIsPresent(studentPrn, true);

	    // --- Add these temporary debug lines ---
	    System.out.println("=============================================");
	    System.out.println("DEBUG: Checking stats for Student PRN -> " + studentPrn);
	    System.out.println("DEBUG: Total Attendance Records Found -> " + totalAttendanceRecords);
	    System.out.println("DEBUG: Present Records Found -> " + presentRecords);
	    System.out.println("=============================================");
	    // ------------------------------------

	    double attendancePercentage = 0;
	    if (totalAttendanceRecords > 0) {
	        attendancePercentage = ((double) presentRecords / totalAttendanceRecords) * 100;
	    }

	    return new StudentDashboardStatsDto(totalCourses, attendancePercentage);
	}
	
	@Override
    public List<TodaysClassResponseDto> getTodaysClasses(String studentPrn) {
        Student student = studentRepository.findById(studentPrn)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with prn: " + studentPrn));

        LocalDate today = LocalDate.now();

        // 1. Get all of today's classes for the student's department
        List<TimetableEntry> todaysSchedule = timetableEntryRepository
                .findByModule_Department_DepartmentIdAndLectureDateOrderByStartTimeAsc(student.getDepartment().getDepartmentId(), today);

        // 2. Get all of the student's attendance records for today for efficient lookup
        Map<Integer, Attendance> todaysAttendanceMap = attendanceRepository
                .findByStudent_PrnAndAttendanceDate(studentPrn, today).stream()
                .collect(Collectors.toMap(att -> att.getTimetableEntry().getTimetableEntryId(), att -> att));

        // 3. Map the schedule to the DTO, adding the attendance status
        return todaysSchedule.stream().map(session -> {
            String status = "Upcoming"; // Default status
            Attendance attendanceRecord = todaysAttendanceMap.get(session.getTimetableEntryId());
            if (attendanceRecord != null) {
                status = attendanceRecord.getPresent() ? "Attended" : "Absent";
            }
            return new TodaysClassResponseDto(
                session.getModule().getModuleName(),
                session.getStartTime(),
                session.getEndTime(),
                status
            );
        }).collect(Collectors.toList());
        
    }
	
	@Override
    public List<ActiveSessionResponse> getPendingFeedbackTasks(String studentPrn) {
        Student student = studentRepository.findById(studentPrn)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with prn: " + studentPrn));

        List<FeedbackSession> pendingSessions = feedbackSessionRepository
                .findPendingFeedbackSessionsForStudent(studentPrn, student.getDepartment().getDepartmentId());

        return pendingSessions.stream()
                .map(session -> new ActiveSessionResponse(
                        session.getSessionId(),
                        session.getModule().getModuleName(),
                        session.getInstructor().getFirstName() + " " + session.getInstructor().getLastName()
                ))
                .collect(Collectors.toList());
    }
	
	@Override
    public List<ScoreResponse> getMyMarks(String studentPrn) {
        // We need a way to convert Score to ScoreResponse. Let's add a helper for now.
        // In a larger refactor, this might live in the ScoreService.
        List<Score> scores = scoreRepository.findByStudent_Prn(studentPrn);
        return scores.stream().map(this::convertScoreToDto).collect(Collectors.toList());
    }

    private ScoreResponse convertScoreToDto(Score score) {
        ScoreResponse dto = new ScoreResponse();
        dto.setScoreId(score.getScoreId());
        dto.setLabExamMarks(score.getLabExamMarks());
        dto.setInternalMarks(score.getInternalMarks());
        
        if (score.getStudent() != null) {
            dto.setStudentPrn(score.getStudent().getPrn());
            dto.setStudentName(score.getStudent().getFirstName() + " " + score.getStudent().getLastName());
        }
        
        if (score.getExam() != null) {
            dto.setExamId(score.getExam().getExamId());
            dto.setExamName(score.getExam().getExamName());
            if (score.getExam().getModule() != null) {
                dto.setModuleName(score.getExam().getModule().getModuleName());
            }
        }
        return dto;
    }
}