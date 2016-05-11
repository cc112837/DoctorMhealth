package com.cc.doctormhealth.model;


public class Friend {
	public String id;
	public String username;
	public String userHead;
//	public ItemStatus status; // 为null则不在线
//	public ItemType type;
	public String mood;
	public boolean isChose;
	
	
	public Friend(String username) {
		super();
		this.username = username;
	}

	public Friend(String id, String username, String mood) {
		super();
		this.id = id;
		this.username = username;
		this.mood = mood;
	}
	
	@Override
	 public boolean equals(Object obj) {
	  boolean isEqual = false;
	  if (obj instanceof Friend) {
		  Friend t = (Friend) obj;
		  isEqual = this.username.equals(t.username);
		  return isEqual;
	  }
	  return super.equals(obj);
	 }
}
