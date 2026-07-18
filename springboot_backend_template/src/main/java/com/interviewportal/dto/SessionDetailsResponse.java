package com.interviewportal.dto;

import java.util.List;

import com.interviewportal.entity.Difficulty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionDetailsResponse {
	
	private Long sessionId;
	
	private String domain;
	
	private Difficulty difficulty;
	
	private List<QuestionDetailsResponse> questions;
}
