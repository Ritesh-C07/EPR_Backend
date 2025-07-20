package com.cdac.erp.feature.auth.service;

import com.cdac.erp.common.exception.ResourceNotFoundException;
import com.cdac.erp.core.model.Admin;
import com.cdac.erp.core.model.Department;
import com.cdac.erp.core.repository.AdminRepository;
import com.cdac.erp.core.repository.DepartmentRepository;
import com.cdac.erp.core.repository.StudentRepository;
import com.cdac.erp.feature.auth.dto.AdminRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cdac.erp.core.model.Student;
import com.cdac.erp.feature.auth.dto.AuthResponse;
import com.cdac.erp.feature.auth.dto.LoginRequest;
import java.util.Optional;
import org.springframework.security.authentication.BadCredentialsException;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Admin registerAdmin(AdminRegisterRequest registerRequest) {
        // Find the department to associate with the admin
        Department department = departmentRepository.findById(registerRequest.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + registerRequest.getDepartmentId()));

        // Create a new Admin entity
        Admin admin = new Admin();
        admin.setFirstName(registerRequest.getFirstName());
        admin.setLastName(registerRequest.getLastName());
        admin.setEmail(registerRequest.getEmail());
        admin.setPhoneNumber(registerRequest.getPhoneNumber());
        admin.setDateOfBirth(registerRequest.getDateOfBirth());
        admin.setAddress(registerRequest.getAddress());
        admin.setRole(registerRequest.getRole());
        admin.setDepartment(department);

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        admin.setPasswordHash(hashedPassword);

        return adminRepository.save(admin);
    }
    
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Try to find an admin first by email
        Optional<Admin> adminOptional = adminRepository.findByEmail(username);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (passwordEncoder.matches(password, admin.getPasswordHash())) {
                return new AuthResponse(admin.getEmail(), admin.getRole(), "Admin login successful");
            }
        }

        // If not an admin, try to find a student by PRN
        Optional<Student> studentOptional = studentRepository.findById(username); // Using findById for PRN
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            if (passwordEncoder.matches(password, student.getPasswordHash())) {
                return new AuthResponse(student.getEmail(), "Student", "Student login successful");
            }
        }
        
        // If user is not found or password doesn't match for either role
        throw new BadCredentialsException("Invalid username or password");
    }
}