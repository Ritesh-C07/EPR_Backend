package com.cdac.erp.feature.instructor.service;

import com.cdac.erp.core.model.Instructor;
import com.cdac.erp.feature.instructor.dto.InstructorRequest;
import com.cdac.erp.feature.instructor.dto.InstructorResponse;
import com.cdac.erp.feature.instructor.dto.InstructorUpdateRequest;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IInstructorService {
    Instructor createInstructor(InstructorRequest request);
    Instructor updateInstructor(Integer instructorId, InstructorUpdateRequest request); // Add this
    void deleteInstructor(Integer instructorId); // Add this
//    List<Instructor> getAllInstructors(); // Add this
    Page<InstructorResponse> getAllInstructors(Pageable pageable);
    Instructor getInstructorById(Integer instructorId); // Add this
}