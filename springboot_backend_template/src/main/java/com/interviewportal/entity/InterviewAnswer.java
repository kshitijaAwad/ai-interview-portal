package com.interviewportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interview_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3000)
    private String answerText;

    private Integer score;

    @Column(length = 5000)
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private InterviewQuestion question;
}