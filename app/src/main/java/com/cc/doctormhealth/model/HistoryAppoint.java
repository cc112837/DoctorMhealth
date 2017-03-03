package com.cc.doctormhealth.model;

import java.util.List;

/**
 * 项目名称：DoctorMhealth
 * 类描述：历史预约
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2017/2/27 15:40
 * 修改人：Administrator
 * 修改时间：2017/2/27 15:40
 * 修改备注：
 */

public class HistoryAppoint {

    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * caseness : the fact
         * clinicTime : 2017-02-21
         */

        private String caseness;
        private String clinicTime;

        public String getCaseness() {
            return caseness;
        }

        public void setCaseness(String caseness) {
            this.caseness = caseness;
        }

        public String getClinicTime() {
            return clinicTime;
        }

        public void setClinicTime(String clinicTime) {
            this.clinicTime = clinicTime;
        }
    }
}
