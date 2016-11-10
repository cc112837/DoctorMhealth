package com.cc.doctormhealth.model;

import java.util.List;

/**
 * 项目名称：DoctorMhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/11/10 10:30
 * 修改人：Administrator
 * 修改时间：2016/11/10 10:30
 * 修改备注：
 */
public class Money {

    /**
     * number : 1
     * data : [{"time":"2016-11-03 15:57","price":0.01}]
     * totalPrice : 0.01
     */

    private int number;
    private double totalPrice;
    /**
     * time : 2016-11-03 15:57
     * price : 0.01
     */

    private List<DataEntity> data;

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getNumber() {
        return number;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String time;
        private double price;

        public void setTime(String time) {
            this.time = time;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public double getPrice() {
            return price;
        }
    }
}
