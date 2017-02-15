package com.cc.doctormhealth.model;

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

    public static class DataEntity {
        /**
         * sex : 男
         * caseness : 恶心 ，反胃
         * illness : 各种疾病，疑难杂症
         * age : 29
         * name : 赵祥
         * clinicTime : 2016-07-05 上午
         * appointId : f5a2e99d5a26f5f4015a2740e8cb0002
         */

        private String sex;
        private String caseness;
        private String illness;
        private String age;
        private String name;
        private String clinicTime;
        private String appointId;

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
    }
}
