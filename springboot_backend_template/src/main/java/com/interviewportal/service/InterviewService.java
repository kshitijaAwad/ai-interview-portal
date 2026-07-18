package com.interviewportal.service;

import java.util.List;

import com.interviewportal.dto.AnswerRequest;
import com.interviewportal.dto.DashboardResponse;
import com.interviewportal.dto.EvalutionResponse;
import com.interviewportal.dto.InterviewRequest;
import com.interviewportal.dto.InterviewResponse;
import com.interviewportal.dto.MyInterviewResponse;
import com.interviewportal.dto.SessionDetailsResponse;

public interface InterviewService {

	InterviewResponse generateQuestions(InterviewRequest request);
	
	EvalutionResponse evaluateAnswer(AnswerRequest request);
	
	List<MyInterviewResponse> getMyInterviews();
	
	SessionDetailsResponse getSessionDetails(Long sessionId);
	
	DashboardResponse getDashboard();
	
}
