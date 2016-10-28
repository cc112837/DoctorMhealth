package com.cc.doctormhealth.model;

import java.io.Serializable;

public class UserEvaluation implements Serializable {

//	private String userName;// 用户名字
	private String userid;// 用户id
	private int degree;// 满意度
	private String comment;// 评价
	private int totalRecord;// 问题
	public UserEvaluation(){

	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}

	
}
