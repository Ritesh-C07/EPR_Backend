package com.cdac.erp.core.repository;

import com.cdac.erp.core.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    // No custom methods are needed here for now.
    // The JpaRepository already provides findById(String prn).
}