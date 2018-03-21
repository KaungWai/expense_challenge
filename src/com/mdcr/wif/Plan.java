package com.mdcr.wif;

import java.sql.Date;

public class Plan {
	 private int id;
	 private String name;
	 private Date startDate;
	 private Date endDate;
	 private int amount;
	 private String status;
	 
	 public Plan(){}
	 
	 public Plan(String name, Date startDate, Date endDate, int amount, String status) {
		 super();
		 this.name = name;
	     this.startDate = startDate;
	     this.endDate = endDate;
	     this.amount = amount;
	     this.status = status;
	 }
	    
	 @Override
	 public String toString() {
		 return "Plan [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + "amount=" + amount + "status=" + status + "]";
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

	public Date getStartDate() {
		return startDate;
	 }

	 public void setStartDate(Date startDate) {
		 this.startDate = startDate;
	 }
	 
	 public Date getEndDate() {
			return endDate;
		 }

	 public void setEndDate(Date endDate) {
		 this.endDate = endDate;
	 }
	
	 public int getAmount() {
		 return amount;
	 }

	 public void setAmount(int amount) {
		 this.amount = amount;
	 }

	 public String getStatus() {
			return status;
		 }

	 public void setStatus(String status) {
		 this.status = status;
	 }

}
