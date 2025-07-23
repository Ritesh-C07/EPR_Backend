package com.cdac.erp.feature.grades.service;

import com.cdac.erp.feature.grades.dto.BulkScoreRequest;
import com.cdac.erp.feature.grades.dto.MarksEntryResponseDto;
import com.cdac.erp.feature.grades.dto.ScoreResponse;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IScoreService {
    void recordBulkScores(BulkScoreRequest request);
    List<ScoreResponse> getScoresForExam(Integer examId);
    Page<MarksEntryResponseDto> getMarksEntrySheetForExam(Integer examId, Pageable pageable);
}