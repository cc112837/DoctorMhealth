package com.cc.doctormhealth.model;

import java.util.List;

/**
 * 项目名称：mhealth
 * 类描述：资讯列表
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/7/16 10:23
 * 修改人：Administrator
 * 修改时间：2016/7/16 10:23
 * 修改备注：
 */
public class NewsList {
    /**
     * medicalId : f5a2e99d591646a00159165f70a80003
     * medicalTitle : 天生聚宝又旺夫的三大生肖女
     * medicalImage : http://117.34.105.29:8818/jeecg/upload/files/20161219171750HWvK1w5f.jpg
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String medicalId;
        private String medicalTitle;
        private String medicalImage;

        public void setMedicalId(String medicalId) {
            this.medicalId = medicalId;
        }

        public void setMedicalTitle(String medicalTitle) {
            this.medicalTitle = medicalTitle;
        }

        public void setMedicalImage(String medicalImage) {
            this.medicalImage = medicalImage;
        }

        public String getMedicalId() {
            return medicalId;
        }

        public String getMedicalTitle() {
            return medicalTitle;
        }

        public String getMedicalImage() {
            return medicalImage;
        }
    }
}
