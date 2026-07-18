package com.interviewportal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interviewportal.entity.InterviewQuestion;
import com.interviewportal.entity.User;

public interface InterviewQuestionRepository
        extends JpaRepository<InterviewQuestion, Long> {
	
	List<InterviewQuestion> findBySessionId(Long sessionId);
	
	Long countBySessionUser(User user);

}
