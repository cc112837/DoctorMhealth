package com.cc.doctormhealth.model;


public class Patients {
	public String id;
	public String username;
	public String userHead;
	public String userAge;
	public String userSex;
	public String date;
	public String miaoshu;
	
	
	public Patients(String username) {
		super();
		this.username = username;
	}

	public Patients(String id, String username, String userAge, String userSex,String date,String miaoshu) {
		super();
		this.id = id;
		this.username = username;
		this.userAge = userAge;
		this.userSex = userSex;
		this.date = date;
		this.miaoshu = miaoshu;
	}
	
	@Override
	 public boolean equals(Object obj) {
	  boolean isEqual = false;
	  if (obj instanceof Patients) {
		  Patients t = (Patients) obj;
		  isEqual = this.username.equals(t.username);
		  return isEqual;
	  }
	  return super.equals(obj);
	 }
}
