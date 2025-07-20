package com.cdac.erp.feature.student.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentCreateRequest {
    private String prn;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private String email;
    private String password; // Initial password set by the admin
    private Integer departmentId;
}