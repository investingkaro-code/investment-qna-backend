package com.investment_qna.DTO;

public class QuestionDTO {
    private Long id;
    private String questionText;
    private Long categoryId;  
    private Long subCategoryId; 
    private String categoryName;
    private String subCategoryName;
    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    
    public Long getCategoryId() { return categoryId; }   // <-- add getter
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; } // setter
	public Long getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
    
    

}

