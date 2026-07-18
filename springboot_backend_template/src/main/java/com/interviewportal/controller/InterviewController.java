package com.interviewportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interviewportal.dto.AnswerRequest;
import com.interviewportal.dto.DashboardResponse;
import com.interviewportal.dto.InterviewRequest;
import com.interviewportal.dto.MyInterviewResponse;
import com.interviewportal.dto.SessionDetailsResponse;
import com.interviewportal.service.InterviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Interview APIs")
@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping("/generate")
    @Operation(summary = "Generate interview questions")
    public ResponseEntity<?> generateQuestions(
            @RequestBody InterviewRequest request) {

        return ResponseEntity.ok(
                interviewService.generateQuestions(
                        request));
    }
    
    @PostMapping("/submit-answer")
    public ResponseEntity<?> submitAnswer(@RequestBody AnswerRequest request){
    	return ResponseEntity.ok(interviewService.evaluateAnswer(request));
    }
    
    @GetMapping("/my-sessions")
    public ResponseEntity<List<MyInterviewResponse>> getMyInterviews(){
    	
    	return ResponseEntity.ok(interviewService.getMyInterviews());
    }
    
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<SessionDetailsResponse> getSessionDetails(@PathVariable Long sessionId){
    	System.out.println("Inside getSessionDetails: " + sessionId);

    	return ResponseEntity.ok(interviewService.getSessionDetails(sessionId));
    }
    
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(){
    	
    	return ResponseEntity.ok(interviewService.getDashboard());
    }
}