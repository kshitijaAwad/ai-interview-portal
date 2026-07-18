package com.interviewportal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interviewportal.entity.InterviewSession;
import com.interviewportal.entity.User;

public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long> {

	List<InterviewSession> findByUser(User user); 
	
	Long countByUser(User user);
}
