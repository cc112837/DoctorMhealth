package com.cc.doctormhealth.model;

import java.util.List;

/**
 * 项目名称：DoctorMhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2017/2/18 10:56
 * 修改人：Administrator
 * 修改时间：2017/2/18 10:56
 * 修改备注：
 */

public class Bookmanger {

    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * medicalInstitution : test1
         * medicalId : f5a2e99d5a49e932015a4a0adcae0002
         * mdicalPicture : ["http://117.34.105.29:8818/mhealth/upload/20170217110728.png","http://117.34.105.29:8818/mhealth/upload/20170217110735.png"]
         * medicalDate : 2017年02月17日
         * medicalType : 体检报告
         */

        private String medicalInstitution;
        private String medicalId;
        private String medicalDate;
        private String medicalType;
        private List<String> mdicalPicture;

        public String getMedicalInstitution() {
            return medicalInstitution;
        }

        public void setMedicalInstitution(String medicalInstitution) {
            this.medicalInstitution = medicalInstitution;
        }

        public String getMedicalId() {
            return medicalId;
        }

        public void setMedicalId(String medicalId) {
            this.medicalId = medicalId;
        }

        public String getMedicalDate() {
            return medicalDate;
        }

        public void setMedicalDate(String medicalDate) {
            this.medicalDate = medicalDate;
        }

        public String getMedicalType() {
            return medicalType;
        }

        public void setMedicalType(String medicalType) {
            this.medicalType = medicalType;
        }

        public List<String> getMdicalPicture() {
            return mdicalPicture;
        }

        public void setMdicalPicture(List<String> mdicalPicture) {
            this.mdicalPicture = mdicalPicture;
        }
    }
}
