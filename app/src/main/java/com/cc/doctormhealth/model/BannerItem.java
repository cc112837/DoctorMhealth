package com.cc.doctormhealth.model;

/**
 * 项目名称：mhealth
 * 类描述：轮播图实体类
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/11/7 10:53
 * 修改人：Administrator
 * 修改时间：2016/11/7 10:53
 * 修改备注：
 */
public class BannerItem {
    String url;
    String title;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
