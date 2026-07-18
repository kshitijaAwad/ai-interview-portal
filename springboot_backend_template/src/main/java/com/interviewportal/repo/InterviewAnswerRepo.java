package com.interviewportal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.interviewportal.entity.InterviewAnswer;
import com.interviewportal.entity.User;

public interface InterviewAnswerRepo extends JpaRepository<InterviewAnswer, Long> {

	@Query("""
		       SELECT AVG(a.score)
		       FROM InterviewAnswer a
		       WHERE a.question.session.user = :user
		       """)
	Double getAverageScore(User user);
	
}
