package com.cdac.erp.feature.student.controller;

import com.cdac.erp.core.model.Student;
import com.cdac.erp.feature.student.dto.StudentCreateRequest;
import com.cdac.erp.feature.student.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/students")
public class AdminStudentController {

    @Autowired
    private IStudentService studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentCreateRequest createRequest) {
        Student newStudent = studentService.createStudent(createRequest);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    //TODO add other admin-related student endpoints here later,
    // like getAllStudents, updateStudent, deleteStudent, etc.
}