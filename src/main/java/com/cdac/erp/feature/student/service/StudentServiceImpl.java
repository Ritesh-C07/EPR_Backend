package com.cdac.erp.feature.student.service;

import com.cdac.erp.common.exception.ResourceNotFoundException;
import com.cdac.erp.core.model.Department;
import com.cdac.erp.core.model.Student;
import com.cdac.erp.core.repository.DepartmentRepository;
import com.cdac.erp.core.repository.StudentRepository;
import com.cdac.erp.feature.student.dto.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Student createStudent(StudentCreateRequest createRequest) {
        // Find the department for the student
        Department department = departmentRepository.findById(createRequest.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + createRequest.getDepartmentId()));

        // Create a new Student entity
        Student student = new Student();
        student.setPrn(createRequest.getPrn());
        student.setFirstName(createRequest.getFirstName());
        student.setLastName(createRequest.getLastName());
        student.setEmail(createRequest.getEmail());
        student.setPhoneNumber(createRequest.getPhoneNumber());
        student.setDateOfBirth(createRequest.getDateOfBirth());
        student.setAddress(createRequest.getAddress());
        student.setDepartment(department);

        // Hash the initial password before saving
        student.setPasswordHash(passwordEncoder.encode(createRequest.getPassword()));

        return studentRepository.save(student);
    }
}