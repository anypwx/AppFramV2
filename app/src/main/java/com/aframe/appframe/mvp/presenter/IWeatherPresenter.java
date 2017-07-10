package com.aframe.appframe.mvp.presenter;

import com.aframe.appframe.mvp.view.DataResultView;

import java.util.Map;

/**
 * Description
 * Created by pwx on 2016/12/21.
 * Company MingThink
 */

public interface IWeatherPresenter<T> {
    void getWeather(Map<String,String> param, boolean isList , Class<T> cla);
    void postData(Map<String,String> param, boolean isList , Class<T> cla);
}
