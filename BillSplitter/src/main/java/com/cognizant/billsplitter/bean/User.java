package com.cognizant.billsplitter.bean;

public class User {
	private long mobile;
	private String name;
	private boolean guestMode;
	
	public long getMobile() {
		return this.mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getGuestMode() {
		return this.guestMode;
	}
	public void setGuestMode(boolean i) {
		this.guestMode = i;
	}
	
}
