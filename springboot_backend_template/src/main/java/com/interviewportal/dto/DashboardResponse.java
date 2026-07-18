package com.interviewportal.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponse {

	private Long totalInterviews;
	
	private Long totalQuestions;
	
	private Double averageScore;
}
