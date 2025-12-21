package com.investment_qna.DTO;

import java.util.List;

public class StockReportDTO {
    private String stockSymbol;
    private List<SubCategoryReportDTO> subcategories;
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public List<SubCategoryReportDTO> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(List<SubCategoryReportDTO> subcategories) {
		this.subcategories = subcategories;
	}
    
}
