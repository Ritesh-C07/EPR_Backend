package com.cdac.erp.feature.instructor.controller;

import com.cdac.erp.core.model.Instructor;
import com.cdac.erp.feature.instructor.dto.InstructorRequest;
import com.cdac.erp.feature.instructor.service.IInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/instructors")
public class InstructorController {

    @Autowired
    private IInstructorService instructorService;

    @PostMapping
    public ResponseEntity<Instructor> createInstructor(@RequestBody InstructorRequest request) {
        Instructor newInstructor = instructorService.createInstructor(request);
        return new ResponseEntity<>(newInstructor, HttpStatus.CREATED);
    }
}