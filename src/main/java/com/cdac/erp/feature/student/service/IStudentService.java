package com.cdac.erp.feature.student.service;

import com.cdac.erp.core.model.Student;
import com.cdac.erp.feature.student.dto.StudentCreateRequest;
// ... other imports if you have them

public interface IStudentService {
    // TODO add more methods like getAllStudents later
    Student createStudent(StudentCreateRequest createRequest);
}