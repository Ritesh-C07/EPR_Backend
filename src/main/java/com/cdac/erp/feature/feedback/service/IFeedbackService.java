package com.cdac.erp.feature.feedback.service;

import com.cdac.erp.feature.feedback.dto.ActiveSessionResponse;
import com.cdac.erp.feature.feedback.dto.AnonymousFeedbackResponse;
import com.cdac.erp.feature.feedback.dto.FeedbackAveragesResponse;
import com.cdac.erp.feature.feedback.dto.FeedbackRequest;
import com.cdac.erp.feature.feedback.dto.FeedbackSessionRequest;
import com.cdac.erp.feature.feedback.dto.FeedbackStatsResponse;

import java.util.List;

public interface IFeedbackService {
    void submitFeedback(String studentPrn, FeedbackRequest request);
    List<FeedbackAveragesResponse> getFeedbackAverages();
    List<AnonymousFeedbackResponse> getAnonymousFeedbackFor(Integer instructorId, String moduleId);
    void deleteFeedback(Integer feedbackId);
    void createFeedbackSession(FeedbackSessionRequest request);
    List<ActiveSessionResponse> getActiveFeedbackSessions();
    FeedbackStatsResponse getFeedbackStats(Integer sessionId);
    List<AnonymousFeedbackResponse> getAnonymousFeedbackForSession(Integer sessionId);
}