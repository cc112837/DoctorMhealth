package com.cc.doctormhealth.model;

import java.io.Serializable;
import java.util.List;

public class UserEvaluation implements Serializable {


	/**
	 * userName  : 18516128315
	 * evaluate  : 评价
	 * satisfy : 满意
	 * createTime  : 2016-09-13 08:00:00
	 */

	private List<DataEntity> data;
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setData(List<DataEntity> data) {
		this.data = data;
	}

	public List<DataEntity> getData() {
		return data;
	}

	public static class DataEntity {
		private String userName;
		private String evaluate;
		private String satisfy;
		private String createTime;

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public void setEvaluate(String evaluate) {
			this.evaluate = evaluate;
		}

		public void setSatisfy(String satisfy) {
			this.satisfy = satisfy;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getUserName() {
			return userName;
		}

		public String getEvaluate() {
			return evaluate;
		}

		public String getSatisfy() {
			return satisfy;
		}

		public String getCreateTime() {
			return createTime;
		}
	}
}
