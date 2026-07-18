package com.interviewportal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interviewportal.ai.AiService;
import com.interviewportal.dto.AnswerRequest;
import com.interviewportal.dto.DashboardResponse;
import com.interviewportal.dto.EvalutionResponse;
import com.interviewportal.dto.InterviewRequest;
import com.interviewportal.dto.InterviewResponse;
import com.interviewportal.dto.MyInterviewResponse;
import com.interviewportal.dto.QuestionDetailsResponse;
import com.interviewportal.dto.QuestionResponse;
import com.interviewportal.dto.SessionDetailsResponse;
import com.interviewportal.entity.InterviewAnswer;
import com.interviewportal.entity.InterviewQuestion;
import com.interviewportal.entity.InterviewSession;
import com.interviewportal.entity.User;
import com.interviewportal.repo.InterviewAnswerRepo;
import com.interviewportal.repo.InterviewQuestionRepository;
import com.interviewportal.repo.InterviewSessionRepository;
import com.interviewportal.repo.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

	private final AiService aiservice;
	private final InterviewSessionRepository sessionRepo;
	private final InterviewQuestionRepository questionRepo;
	private final InterviewAnswerRepo answerRepo;
	private final UserRepository userRepo;
	
	@Override
	public InterviewResponse generateQuestions(InterviewRequest request) {
		
		Authentication authentication =
		        SecurityContextHolder.getContext()
		                .getAuthentication();

		String email = authentication.getName();

		User user = userRepo.findByEmail(email)
		        .orElseThrow();
		
		InterviewSession session = InterviewSession.builder()
				.domain(request.getDomain())
				.difficulty(request.getDifficulty())
				.createdAt(LocalDateTime.now())
				.user(user)
				.build();
		
		session = sessionRepo.save(session);
		
		List<String> aiQuestions =
		        aiservice.generateQuestions(
		                request.getDomain(),
		                request.getDifficulty());

		List<QuestionResponse> questionResponses =
		        new ArrayList<>();

		for (String questionText : aiQuestions) {

		    InterviewQuestion q =
		            InterviewQuestion.builder()
		                    .questionText(questionText)
		                    .createdAt(LocalDateTime.now())
		                    .session(session)
		                    .build();

		    q = questionRepo.save(q);

		    questionResponses.add(
		            QuestionResponse.builder()
		                    .questionId(q.getId())
		                    .question(q.getQuestionText())
		                    .build()
		    );
		}

		return InterviewResponse.builder()
		        .sessionId(session.getId())
		        .questions(questionResponses)
		        .build();
	}

	@Override
	public EvalutionResponse evaluateAnswer(AnswerRequest request) {
		
		InterviewQuestion question = questionRepo.findById(request.getQuestionId())
				.orElseThrow();
		String prompt =
			    "You are a technical interviewer.\n" +
			    "Question: " + question.getQuestionText() + "\n" +
			    "Candidate Answer: " + request.getAnswer() + "\n\n" +
			    "Respond exactly in this format:\n" +
			    "Score: X/10\n" +
			    "Feedback: <short feedback>";
		 
		 String aiResponse = aiservice.evaluateAnswer(prompt);
		 
		 String feedback = aiResponse;
		 
		 if(aiResponse.contains("Feedback : ")) {
			 
			 feedback = aiResponse.split("Feedback:")[1].trim();
		 }
		 
		 Integer score = 5;

		 Pattern pattern = Pattern.compile("(\\d+)/10");
		 Matcher matcher = pattern.matcher(aiResponse);

		 if(matcher.find()) {
		     score = Integer.parseInt(matcher.group(1));
		 }
		  
		 InterviewAnswer answer = InterviewAnswer.builder()
				 .answerText(request.getAnswer())
				 .score(score)
				 .feedback(aiResponse)
				 .question(question)
				 .build();
		 
		 answerRepo.save(answer);
		 
		return EvalutionResponse.builder()
				.score(score)
				.feedback(aiResponse)
				.build();
	}

	@Override
	public List<MyInterviewResponse> getMyInterviews() {
		
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		String email = authentication.getName();
		
		User user = userRepo.findByEmail(email)
				.orElseThrow();
		
		List<InterviewSession> sessions = sessionRepo.findByUser(user);
		return sessions.stream()
				.map(session -> MyInterviewResponse
						.builder()
						.sessionId(session.getId())
						.domain(session.getDomain())
						.difficulty(session.getDifficulty())
						.createdAt(session.getCreatedAt())
						.build())
				.toList();
	}

	@Transactional
	@Override
	public SessionDetailsResponse getSessionDetails(Long sessionId) {
		
		InterviewSession session = sessionRepo.findById(sessionId)
				.orElseThrow();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		
		if(!session.getUser().getEmail().equals(email)) {
			throw new RuntimeException("Access Denied");
		}
		List<InterviewQuestion> questions = questionRepo.findBySessionId(sessionId);
		
		List<QuestionDetailsResponse> questionResponses = 
				questions.stream()
				.map(q -> {
					
					String answer = null;
					Integer score = null;
					String  feedback = null;
					
					if(q.getAnswers() != null && !q.getAnswers().isEmpty()) {
						
						InterviewAnswer a = q.getAnswers().get(0);
						answer = a.getAnswerText();
						score = a.getScore();
						feedback = a.getFeedback();
					}
					return QuestionDetailsResponse.builder()
							.questionId(q.getId())
							.question(q.getQuestionText())
							.answer(answer)
							.score(score)
							.feedback(feedback)
							.build();
				}).toList();
		
		return SessionDetailsResponse.builder()
				.sessionId(session.getId())
				.domain(session.getDomain())
				.difficulty(session.getDifficulty())
				.questions(questionResponses)
				.build();
	}

	@Override
	public DashboardResponse getDashboard() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String email = authentication.getName();
		
		User user = userRepo.findByEmail(email).orElseThrow();
		
		Long totalInterviews = sessionRepo.countByUser(user);
		
		Long totalQuestions = questionRepo.countBySessionUser(user);
		
		Double averageScore = answerRepo.getAverageScore(user);
		
		if(averageScore == null) {
			averageScore = 0.0;
		}
		return DashboardResponse.builder()
				.totalInterviews(totalInterviews)
				.totalQuestions(totalQuestions)
				.averageScore(averageScore)
				.build();
	}

}
