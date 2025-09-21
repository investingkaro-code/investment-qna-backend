package com.investment_qna.DTO;

import com.investment_qna.model.Answer;

public class AnswerDTO {
    private Long questionId;
    private String answerText;

    public AnswerDTO() {}

    public AnswerDTO(Long questionId, String answerText) {
        this.questionId = questionId;
        this.answerText = answerText;
    }

    
    public AnswerDTO(Answer answer) {
		// TODO Auto-generated constructor stub
    	this.questionId = answer.getQuestion().getId();
    	this.answerText = answer.getAnswerText();
	}
	// getters & setters
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public String getAnswerText() { return answerText; }
    public void setAnswerText(String answerText) { this.answerText = answerText; }
}
