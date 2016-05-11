package com.cc.doctormhealth.model;


public class Patient {
	public String id;
	public String username;
	public String userHead;
	public String userAge;
	public String userSex;
	public String date;
	public String bing;
	
	
	public Patient(String username) {
		super();
		this.username = username;
	}

	public Patient(String id, String username, String userAge, String userSex,String date,String bing) {
		super();
		this.id = id;
		this.username = username;
		this.userAge = userAge;
		this.userSex = userSex;
		this.date = date;
		this.bing = bing;
	}
	
	@Override
	 public boolean equals(Object obj) {
	  boolean isEqual = false;
	  if (obj instanceof Patient) {
		  Patient t = (Patient) obj;
		  isEqual = this.username.equals(t.username);
		  return isEqual;
	  }
	  return super.equals(obj);
	 }
}
