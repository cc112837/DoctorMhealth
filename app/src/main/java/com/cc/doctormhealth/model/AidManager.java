package com.cc.doctormhealth.model;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：DoctorMhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2017/2/15 14:58
 * 修改人：Administrator
 * 修改时间：2017/2/15 14:58
 * 修改备注：
 */

public class AidManager {


    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{
        /**
         * sex : 女
         * caseness : 哈哈
         * illness : 哈哈
         * userId : null
         * age : 24
         * name : 吴聪聪
         * userImage : http://ac-cirdf9pJ.clouddn.com/d71b8dKTdoKMxIKZQA2cALC
         * clinicTime : 02-22星期三上午
         * appointId : f5a2e99d5a4563c2015a4575f4790003
         * checkCase : 0
         */

        private String sex;
        private String caseness;
        private String illness;
        private String userId;
        private String age;
        private String name;
        private String userImage;
        private String clinicTime;
        private String appointId;
        private String checkCase;
        private Boolean initialVisible;
        private String appointStatu;

        public String getAppointStatu() {
            return appointStatu;
        }

        public void setAppointStatu(String appointStatu) {
            this.appointStatu = appointStatu;
        }
        public Boolean getInitialVisible() {
            return initialVisible;
        }

        public void setInitialVisible(Boolean initialVisible) {
            this.initialVisible = initialVisible;
        }
        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCaseness() {
            return caseness;
        }

        public void setCaseness(String caseness) {
            this.caseness = caseness;
        }

        public String getIllness() {
            return illness;
        }

        public void setIllness(String illness) {
            this.illness = illness;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getClinicTime() {
            return clinicTime;
        }

        public void setClinicTime(String clinicTime) {
            this.clinicTime = clinicTime;
        }

        public String getAppointId() {
            return appointId;
        }

        public void setAppointId(String appointId) {
            this.appointId = appointId;
        }

        public String getCheckCase() {
            return checkCase;
        }

        public void setCheckCase(String checkCase) {
            this.checkCase = checkCase;
        }
    }
}
