package com.cdac.erp.feature.instructor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InstructorRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
}