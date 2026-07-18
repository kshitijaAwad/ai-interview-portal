package com.interviewportal.dto;

import java.time.LocalDateTime;

import com.interviewportal.entity.Difficulty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyInterviewResponse {

	private Long sessionId;
	
	private String domain;
	
	private Difficulty difficulty;
	
	private LocalDateTime createdAt;
}
