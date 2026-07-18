package com.interviewportal.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvalutionResponse {
	private Integer score;
	private String feedback;
}
