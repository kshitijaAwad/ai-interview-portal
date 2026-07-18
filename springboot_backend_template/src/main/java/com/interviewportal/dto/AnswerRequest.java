package com.interviewportal.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest{
	
	private Long questionId;
	
	private String answer;
}
