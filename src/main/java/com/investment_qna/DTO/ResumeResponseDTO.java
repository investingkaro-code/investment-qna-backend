package com.investment_qna.DTO;

import java.util.List;

public class ResumeResponseDTO {

    private long answeredCount;
    private long totalQuestions;
    private List<ResumeAnswerDTO> answers;
	public long getAnsweredCount() {
		return answeredCount;
	}
	public void setAnsweredCount(long answeredCount) {
		this.answeredCount = answeredCount;
	}
	public long getTotalQuestions() {
		return totalQuestions;
	}
	public void setTotalQuestions(long totalQuestions) {
		this.totalQuestions = totalQuestions;
	}
	public List<ResumeAnswerDTO> getAnswers() {
		return answers;
	}
	public void setAnswers(List<ResumeAnswerDTO> answers) {
		this.answers = answers;
	}

    // getters & setters
    
}
