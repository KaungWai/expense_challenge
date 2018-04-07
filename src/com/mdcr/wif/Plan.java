package com.mdcr.wif;

public class Plan {
	 private int id;
	 private String name;
	 private String startDate;
	 private String endDate;
	 private float amount;
	 private int status;
	 
	 public Plan(){}
	 
	 public Plan(String name, String startDate, String endDate, int amount, int status) {
		 super();
		 this.name = name;
	     this.startDate = startDate;
	     this.endDate = endDate;
	     this.amount = amount;
	     this.status = status;
	 }
	    
	 @Override
	 public String toString() {
		 return "Plan [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", amount=" + amount + ", status=" + status + "]";
	 }
	    
	 //getters & setters
	 public int getId() {
		 return id;
	 }

	 public void setId(int id) {
		 this.id = id;
	 }

	 public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	 }

	 public void setStartDate(String startDate) {
		 this.startDate = startDate;
	 }
	 
	 public String getEndDate() {
			return endDate;
		 }

	 public void setEndDate(String endDate) {
		 this.endDate = endDate;
	 }
	
	 public float getAmount() {
		 return amount;
	 }

	 public void setAmount(float amount) {
		 this.amount = amount;
	 }

	 public int getStatus() {
			return status;
		 }

	 public void setStatus(int status) {
		 this.status = status;
	 }

}
