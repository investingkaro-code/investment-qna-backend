package com.investment_qna.DTO;

public class QuestionDTO {
    private Long id;
    private String questionText;
    private Long categoryId;  
    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    
    public Long getCategoryId() { return categoryId; }   // <-- add getter
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; } // setter

}

