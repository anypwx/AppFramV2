package com.aframe.appframe.mvp.view;

/**
 * Description  这个类相当于一个外部接口，用于将net好的数据向外传出
 * Created by pwx on 2016/12/21.
 * Company MingThink
 */

public interface DataResultView<T> {
    void success(T d);
    void fail(String s,int erCode);
    void progressData(int p);
}
