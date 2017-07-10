package com.aframe.appframe.mvp.model;

import com.aframe.appframe.mvp.view.DataResultView;

import java.util.Map;


/**
 * Description
 * Created by pwx on 2016/12/21.
 * Company MingThink
 */

public interface IWeatherModel<T> {
    void getWeather(Map<String,String> param, boolean isList , Class<T> cla, DataResultView<T> dataListener);
    void postData(Map<String,String> param, boolean isList , Class<T> cla, DataResultView<T> dataListener);
}
