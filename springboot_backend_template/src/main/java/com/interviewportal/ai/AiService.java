package com.interviewportal.ai;

import java.util.List;

import com.interviewportal.entity.Difficulty;

public interface AiService {

	List<String> generateQuestions(
            String domain,
            Difficulty difficulty);
	
	String evaluateAnswer(String prompt);
}
