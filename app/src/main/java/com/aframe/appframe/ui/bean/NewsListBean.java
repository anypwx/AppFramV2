package com.aframe.appframe.ui.bean;

import java.util.List;

/**
 * Description
 * Created by pwx on 2016/12/27.
 * Company MingThink
 */

public class NewsListBean {

    /**
     * ctime : 2016-12-27 09:30
     * title : 美国驻叙伊司令：消灭“伊斯兰国”需要2年时间
     * description : 搜狐国际
     * picUrl : http://photocdn.sohu.com/20161227/Img477020585_ss.jpg
     * url : http://news.sohu.com/20161227/n477024562.shtml
     */

    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
