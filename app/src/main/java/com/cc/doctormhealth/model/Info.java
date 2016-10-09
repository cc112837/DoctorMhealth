package com.cc.doctormhealth.model;

/**
 * 项目名称：DoctorMhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/10/9 14:44
 * 修改人：Administrator
 * 修改时间：2016/10/9 14:44
 * 修改备注：
 */
public class Info {

    /**
     * status : 1
     * data : 图片上传成功！
     */

    private String status;
    private String data;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }
}
