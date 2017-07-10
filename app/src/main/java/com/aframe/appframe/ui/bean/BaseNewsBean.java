package com.aframe.appframe.ui.bean;


import com.google.gson.JsonElement;


/**
 * Description
 * Created by pwx on 2016/12/21.
 * Company MingThink
 */


public class BaseNewsBean {
    private int code;
    private String msg;
    private JsonElement newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JsonElement getNewslist() {
        return newslist;
    }

    public void setNewslist(JsonElement newslist) {
        this.newslist = newslist;
    }
}
