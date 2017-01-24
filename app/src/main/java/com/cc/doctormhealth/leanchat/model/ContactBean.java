package com.cc.doctormhealth.leanchat.model;

import java.io.Serializable;

/**
 * 项目名称：DoctorMhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2017/1/24 9:15
 * 修改人：Administrator
 * 修改时间：2017/1/24 9:15
 * 修改备注：
 */

public class ContactBean implements Serializable {
    private int iconId;
    private String title;
    private String phoneNum;
    private String firstHeadLetter;

    public ContactBean(int iconId, String title, String phoneNum, String firstHeadLetter) {
        this.iconId = iconId;
        this.title = title;
        this.phoneNum = phoneNum;
        this.firstHeadLetter=firstHeadLetter;
    }

    public ContactBean() {

    }

    public int getIconId() {
        return iconId;
    }

    public String getFirstHeadLetter() {
        return firstHeadLetter;
    }

    public void setFirstHeadLetter(String firstHeadLetter) {
        this.firstHeadLetter = firstHeadLetter;
    }
    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    @Override
    public String toString() {
        return "ContactBean{" +
                "iconId=" + iconId +
                ", title='" + title + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", firstHeadLetter='" + firstHeadLetter + '}';
    }
}
