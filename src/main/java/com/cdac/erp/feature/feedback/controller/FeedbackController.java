package com.cdac.erp.feature.feedback.controller;

import com.cdac.erp.feature.feedback.dto.*;
import com.cdac.erp.feature.feedback.service.IFeedbackService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    // --- Student Endpoints ---
    @PostMapping("/feedback")
    public ResponseEntity<Void> submitFeedback(@Valid @RequestBody FeedbackRequest request, Authentication authentication) {
        String studentPrn = authentication.getName();
        feedbackService.submitFeedback(studentPrn, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/feedback/sessions/active")
    public ResponseEntity<List<ActiveSessionResponse>> getActiveSessions() {
        List<ActiveSessionResponse> sessions = feedbackService.getActiveFeedbackSessions();
        return ResponseEntity.ok(sessions);
    }

    // --- Admin Endpoints ---
    @PostMapping("/admin/feedback/sessions")
    public ResponseEntity<Void> createFeedbackSession(@Valid @RequestBody FeedbackSessionRequest request) {
        feedbackService.createFeedbackSession(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/admin/feedback/averages")
    public ResponseEntity<List<FeedbackAveragesResponse>> getFeedbackAverages() {
        List<FeedbackAveragesResponse> averages = feedbackService.getFeedbackAverages();
        return ResponseEntity.ok(averages);
    }

    @GetMapping("/admin/feedback/{instructorId}/{moduleId}")
    public ResponseEntity<List<AnonymousFeedbackResponse>> getAnonymousFeedback(
            @PathVariable Integer instructorId, @PathVariable String moduleId) {
        List<AnonymousFeedbackResponse> feedbackList = feedbackService.getAnonymousFeedbackFor(instructorId, moduleId);
        return ResponseEntity.ok(feedbackList);
    }
    
    @GetMapping("/admin/feedback/sessions/{sessionId}/stats")
    public ResponseEntity<FeedbackStatsResponse> getFeedbackStats(@PathVariable Integer sessionId) {
        FeedbackStatsResponse stats = feedbackService.getFeedbackStats(sessionId);
        return ResponseEntity.ok(stats);
    }

    @DeleteMapping("/admin/feedback/{feedbackId}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Integer feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.noContent().build();
    }
    
 // Change this endpoint to use sessionId
    @GetMapping("/admin/feedback/sessions/{sessionId}/anonymous")
    public ResponseEntity<List<AnonymousFeedbackResponse>> getAnonymousFeedback(@PathVariable Integer sessionId) {
        List<AnonymousFeedbackResponse> feedbackList = feedbackService.getAnonymousFeedbackForSession(sessionId);
        return ResponseEntity.ok(feedbackList);
    }
}