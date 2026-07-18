package com.interviewportal.dto;

import com.interviewportal.entity.Difficulty;

import lombok.Data;

@Data
public class InterviewRequest {

    private String domain;

    private Difficulty difficulty;
}