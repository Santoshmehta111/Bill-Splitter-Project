package com.cognizant.billsplitter.bean;

public class Bill {
	private String billName;
	private int noOfPerson;
	private String personsName;
	private String paidBy;
	private String dateOfBill;
	private float amount;
	private long userId;
	
	public long getUserId() {
		return this.userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getBillName() {
		return billName;
	}
	public void setBillName(String billName) {
		this.billName = billName;
	}
	public int getNoOfPerson() {
		return noOfPerson;
	}
	public void setNoOfPerson(int noOfPerson) {
		this.noOfPerson = noOfPerson;
	}
	public String getPersonsName() {
		return personsName;
	}
	public void setPersonsName(String personsName) {
		this.personsName = personsName;
	}
	public String getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}
	public String getDateOfBill() {
		return dateOfBill;
	}
	public void setDateOfBill(String b_date) {
		this.dateOfBill = b_date;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
}
