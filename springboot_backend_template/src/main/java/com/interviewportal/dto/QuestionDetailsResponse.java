package com.interviewportal.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDetailsResponse {

	private Long questionId;
	
	private String question;
	
	private String answer;
	
	private Integer score;
	
	private String feedback;
}
