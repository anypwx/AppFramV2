package com.aframe.appframe.ui.bean;


import com.google.gson.JsonElement;


/**
 * Description
 * Created by pwx on 2016/12/21.
 * Company MingThink
 */


public class BaseBean {
    private int errNum;
    private String errMsg;
    private JsonElement retData;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public JsonElement getRetData() {
        return retData;
    }

    public void setRetData(JsonElement retData) {
        this.retData = retData;
    }
}
