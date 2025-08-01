package com.cdac.erp.core.repository;

import com.cdac.erp.core.model.Score;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
    
    List<Score> findByExam_ExamId(Integer examId);

    Optional<Score> findByStudent_PrnAndExam_ExamId(String prn, Integer examId);
    
    //for showing marks of students
    List<Score> findByStudent_Prn(String prn);
}