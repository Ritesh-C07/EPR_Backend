package com.cdac.erp.security.service;

import com.cdac.erp.core.model.Admin;
import com.cdac.erp.core.model.Student;
import com.cdac.erp.core.repository.AdminRepository;
import com.cdac.erp.core.repository.StudentRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // First, check if the user is an Admin
        Optional<Admin> adminOptional = adminRepository.findByEmail(username);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("Admin"));
            return new User(admin.getEmail(), admin.getPasswordHash(), authorities);
        }

        // If not an Admin, check if the user is a Student
        Optional<Student> studentOptional = studentRepository.findById(username);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("Student"));
            return new User(student.getPrn(), student.getPasswordHash(), authorities);
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}