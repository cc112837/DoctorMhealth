package com.cc.doctormhealth.model;

/**
 * 项目名称：DoctorMhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/10/9 15:16
 * 修改人：Administrator
 * 修改时间：2016/10/9 15:16
 * 修改备注：
 */
public class ConfirmFile {

    /**
     * status : 1
     * data : 资质认证资料提交成功！
     * member : 1
     */

    private String status;
    private String data;
    private int member;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public int getMember() {
        return member;
    }
}
