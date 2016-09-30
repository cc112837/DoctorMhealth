package com.cc.doctormhealth.model;

/**
 * 项目名称：DoctorMhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/9/30 9:16
 * 修改人：Administrator
 * 修改时间：2016/9/30 9:16
 * 修改备注：
 */
public class UserReg {

    /**
     * status : 1
     * userid : 1
     * mobile : 1809990988
     * data : 注册成功！
     * member : 2
     */

    private String status;
    private String userid;
    private String mobile;
    private String data;
    private int member;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getUserid() {
        return userid;
    }

    public String getMobile() {
        return mobile;
    }

    public String getData() {
        return data;
    }

    public int getMember() {
        return member;
    }
}
