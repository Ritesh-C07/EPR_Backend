package com.cdac.erp.feature.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FeedbackStatsResponse {
    private double averageRating;
    private long totalSubmissions;
    private long pendingSubmissions;
}