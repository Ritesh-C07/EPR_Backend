package com.cdac.erp.feature.grades.service;

import com.cdac.erp.feature.grades.dto.ExamRequest;
import com.cdac.erp.feature.grades.dto.ExamResponse;
import java.util.List;

public interface IExamService {
    ExamResponse createExam(ExamRequest request);
    List<ExamResponse> getAllExams();
}