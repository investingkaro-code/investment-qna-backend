package com.investment_qna.DTO;

import java.util.List;

public class SubCategoryReportDTO {
    private String subCategoryName;
    private List<QuestionAnswerDTO> questions;
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public List<QuestionAnswerDTO> getQuestions() {
		return questions;
	}
	public void setQuestions(List<QuestionAnswerDTO> questions) {
		this.questions = questions;
	}
    
}

