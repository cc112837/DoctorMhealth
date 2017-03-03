package com.cc.doctormhealth.model;

import java.util.List;

/**
 * 项目名称：DoctorMhealth
 * 类描述：我的钱包实体类
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/11/10 10:30
 * 修改人：Administrator
 * 修改时间：2016/11/10 10:30
 * 修改备注：
 */
public class Money {

    /**
     * data : [{"time":"2017-02-16 ","price":0.01,"type":"在线咨询"},{"time":"2016-12-14 ","price":0.01,"type":"在线咨询"},{"time":"2016-12-14 ","price":0.01,"type":"在线咨询"},{"time":"2016-12-10 ","price":0.01,"type":"在线咨询"},{"time":"2016-12-09 ","price":0.01,"type":"在线咨询"},{"time":"2016-12-09 ","price":0.01,"type":"在线咨询"},{"time":"2016-11-29 ","price":0.01,"type":"在线咨询"},{"time":"2016-11-29 ","price":0.01,"type":"在线咨询"},{"time":"2016-11-29 ","price":0.01,"type":"在线咨询"},{"time":"2016-11-29 ","price":0.01,"type":"在线咨询"},{"time":"2016-11-13 ","price":0.01,"type":"在线咨询"},{"time":"2016-11-03 ","price":0.01,"type":"在线咨询"},{"time":"2017-02-23 ","price":0.01,"type":"预约挂号"},{"time":"2017-02-23 ","price":0.01,"type":"预约挂号"},{"time":"2017-02-23 ","price":0.01,"type":"预约挂号"},{"time":"2017-02-23 ","price":0.01,"type":"预约挂号"},{"time":"2017-02-23 ","price":0.01,"type":"预约挂号"},{"time":"2017-02-21 ","price":0.01,"type":"预约挂号"}]
     * number : 12
     * totalPrice : 0.18
     */

    private int number;
    private double totalPrice;
    private List<DataEntity> data;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * time : 2017-02-16
         * price : 0.01
         * type : 在线咨询
         */

        private String time;
        private double price;
        private String type;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
