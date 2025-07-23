package com.cdac.erp.feature.feedback.service;

import com.cdac.erp.common.exception.ResourceNotFoundException;
import com.cdac.erp.core.model.*;
import com.cdac.erp.core.model.Module;
import com.cdac.erp.core.repository.*;
import com.cdac.erp.feature.feedback.dto.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements IFeedbackService {

    @Autowired private FeedbackRepository feedbackRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private InstructorRepository instructorRepository;
    @Autowired private ModuleRepository moduleRepository;
    @Autowired private FeedbackSessionRepository feedbackSessionRepository;

    @Override
    public void submitFeedback(String studentPrn, FeedbackRequest request) {
        Student student = studentRepository.findById(studentPrn)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        
        FeedbackSession session = feedbackSessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Feedback session not found"));

        Feedback feedback = new Feedback();
        feedback.setStudent(student);
        feedback.setFeedbackSession(session);
        feedback.setTeachingStyleRating(request.getTeachingStyleRating());
        feedback.setDoubtClearingRating(request.getDoubtClearingRating());
        feedback.setComments(request.getComments());
        feedback.setFeedbackDate(LocalDate.now());
        feedback.setStatus("Submitted");

        feedbackRepository.save(feedback);
    }
    
    @Override
    public void createFeedbackSession(FeedbackSessionRequest request) {
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new ResourceNotFoundException("Module not found"));
        Instructor instructor = instructorRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        FeedbackSession session = new FeedbackSession();
        session.setModule(module);
        session.setInstructor(instructor);
        session.setCreatedDate(LocalDate.now());
        session.setActive(true);

        feedbackSessionRepository.save(session);
    }

    @Override
    public List<ActiveSessionResponse> getActiveFeedbackSessions() {
        return feedbackSessionRepository.findByIsActive(true).stream()
                .map(session -> new ActiveSessionResponse(
                        session.getSessionId(),
                        session.getModule().getModuleName(),
                        session.getInstructor().getFirstName() + " " + session.getInstructor().getLastName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackAveragesResponse> getFeedbackAverages() {
        // This method will need to be updated to work with the new session model
        // For now, it's left as a placeholder to avoid breaking the application
        return List.of();
    }

    @Override
    public List<AnonymousFeedbackResponse> getAnonymousFeedbackFor(Integer instructorId, String moduleId) {
        // This method will also need to be updated to work with sessions
        return List.of();
    }

    @Override
    public void deleteFeedback(Integer feedbackId) {
        if (!feedbackRepository.existsById(feedbackId)) {
            throw new ResourceNotFoundException("Feedback not found with id: " + feedbackId);
        }
        feedbackRepository.deleteById(feedbackId);
    }
    
    @Override
    public FeedbackStatsResponse getFeedbackStats(Integer sessionId) {
        FeedbackSession session = feedbackSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback session not found"));

        // 1. Get all feedback for this session
        List<Feedback> feedbackList = feedbackRepository.findByFeedbackSession_SessionId(sessionId);

        // 2. Calculate average rating
        double avgTeaching = feedbackList.stream().mapToInt(Feedback::getTeachingStyleRating).average().orElse(0.0);
        double avgDoubt = feedbackList.stream().mapToInt(Feedback::getDoubtClearingRating).average().orElse(0.0);
        double overallAvg = (avgTeaching + avgDoubt) / 2.0;

        // 3. Get total submissions
        long totalSubmissions = feedbackList.size();

        // 4. Calculate pending submissions
        long totalStudentsInDept = studentRepository.countByDepartment_DepartmentId(session.getModule().getDepartment().getDepartmentId());
        long pendingSubmissions = totalStudentsInDept - totalSubmissions;

        return new FeedbackStatsResponse(overallAvg, totalSubmissions, pendingSubmissions);
    }
    
    @Override
    public List<AnonymousFeedbackResponse> getAnonymousFeedbackForSession(Integer sessionId) {
        List<Feedback> feedbackList = feedbackRepository.findByFeedbackSession_SessionId(sessionId);
        AtomicInteger counter = new AtomicInteger(1); // For serial number

        return feedbackList.stream().map(feedback -> {
            AnonymousFeedbackResponse dto = new AnonymousFeedbackResponse();
            dto.setSerialNumber(counter.getAndIncrement());
            dto.setFeedbackId(feedback.getFeedbackId());
            dto.setTeachingStyleRating(feedback.getTeachingStyleRating());
            dto.setDoubtClearingRating(feedback.getDoubtClearingRating());
            dto.setComments(feedback.getComments());
            return dto;
        }).collect(Collectors.toList());
    }
}