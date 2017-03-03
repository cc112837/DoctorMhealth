package com.cc.doctormhealth.model;

import java.util.List;

/**
 * 项目名称：DoctorMhealth
 * 类描述：首页轮播图显示
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2017/2/22 15:33
 * 修改人：Administrator
 * 修改时间：2017/2/22 15:33
 * 修改备注：
 */

public class ConvHome {

    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * doctorImage : http://117.34.105.29:8818/mhealth/upload/files/20170222145436skGh7chs.png
         * doctorImageId : f5a2e99d5a6499a0015a6499a0170000
         * doctorTitle : 欢迎加入一点就医大家庭
         */

        private String doctorImage;
        private String doctorImageId;
        private String doctorTitle;

        public String getDoctorImage() {
            return doctorImage;
        }

        public void setDoctorImage(String doctorImage) {
            this.doctorImage = doctorImage;
        }

        public String getDoctorImageId() {
            return doctorImageId;
        }

        public void setDoctorImageId(String doctorImageId) {
            this.doctorImageId = doctorImageId;
        }

        public String getDoctorTitle() {
            return doctorTitle;
        }

        public void setDoctorTitle(String doctorTitle) {
            this.doctorTitle = doctorTitle;
        }
    }
}
