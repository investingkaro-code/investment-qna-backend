package com.investment_qna.DTO;

public class ResumeAnswerDTO {

    private Long questionId;
    private String answerText;

    public ResumeAnswerDTO(Long questionId, String answerText) {
        this.questionId = questionId;
        this.answerText = answerText;
    }

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
    
}
