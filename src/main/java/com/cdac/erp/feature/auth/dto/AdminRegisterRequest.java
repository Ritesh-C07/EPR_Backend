package com.cdac.erp.feature.auth.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password; // Plain text password from the user
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private String role;
    private Integer departmentId; // ID of the department to associate with
}