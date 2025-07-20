package com.cdac.erp.feature.instructor.service;

import com.cdac.erp.core.model.Instructor;
import com.cdac.erp.feature.instructor.dto.InstructorRequest;

public interface IInstructorService {
    Instructor createInstructor(InstructorRequest request);
}