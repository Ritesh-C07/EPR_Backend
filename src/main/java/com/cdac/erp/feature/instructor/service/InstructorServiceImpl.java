package com.cdac.erp.feature.instructor.service;

import com.cdac.erp.core.model.Instructor;
import com.cdac.erp.core.repository.InstructorRepository;
import com.cdac.erp.feature.instructor.dto.InstructorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements IInstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public Instructor createInstructor(InstructorRequest request) {
        Instructor instructor = new Instructor();
        instructor.setFirstName(request.getFirstName());
        instructor.setLastName(request.getLastName());
        instructor.setEmail(request.getEmail());
        instructor.setContactNo(request.getContactNo());
        
        return instructorRepository.save(instructor);
    }
}