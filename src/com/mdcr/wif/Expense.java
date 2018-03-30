package com.mdcr.wif;

public class Expense {
	 private int id;
	 private int planId;
	 private int categoryId;
	 private String categoryName;
	 private String dateTime;
	 private float amount;
	 private String remark;
	 
	 public Expense(){}
	 
	 public Expense(int planId, int categoryId, String expandDate, float amount, String remark) {
		 super();
		 this.planId = planId;
	     this.categoryId = categoryId;
	     this.dateTime = expandDate;
	     this.amount = amount;
	     this.remark = remark;
	 }
	    
	 @Override
	 public String toString() {
		 return "Expense [expensedId=" + id + ", planId=" + planId + ", categoryID=" + categoryId + ", expenseDate=" + dateTime + ", amount=" + amount + ", remark=" + remark + "]";
	 }
	    
	 //getters & setters
	 public int getId() {
		 return id;
	 }

	 public void setId(int expenseId) {
		 this.id = expenseId;
	 }
	 
	 public int getPlanId() {
		 return planId;
	 }

	 public void setPlanId(int planId) {
		 this.planId = planId;
	 }
	 
	 public int getCategoryId() {
		 return categoryId;
	 }

	 public void setCategoryId(int categoryID) {
		 this.categoryId = categoryID;
	 }
	 
	 public String getCategoryName() {
		return categoryName;
	 }

	 public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	 }
	
	 public String getDateTime() {
		return dateTime;
	 }

	 public void setDateTime(String expenseDate) {
		 this.dateTime = expenseDate;
	 }
	 
	 public float getAmount() {
		 return amount;
	 }

	 public void setAmount(float amount) {
		 this.amount = amount;
	 }

	 public String getRemark() {
			return remark;
		 }

	 public void setRemark(String remark) {
		 this.remark = remark;
	 }


}
