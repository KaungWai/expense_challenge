package com.mdcr.wif;

public class DoughnutData {
	int categoryId;
	String categoryName;
	float totalUsedAmount;
	
	public DoughnutData(){}
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public float getTotalUsedAmount() {
		return totalUsedAmount;
	}
	public void setTotalUsedAmount(float totalUsedAmount) {
		this.totalUsedAmount = totalUsedAmount;
	}
}
