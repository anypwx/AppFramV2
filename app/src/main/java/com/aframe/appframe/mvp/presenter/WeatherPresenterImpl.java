package com.aframe.appframe.mvp.presenter;

import com.aframe.appframe.mvp.model.IWeatherModel;
import com.aframe.appframe.mvp.model.WeatherImpl;
import com.aframe.appframe.mvp.view.DataResultView;
import com.aframe.appframe.ui.bean.WeatherBean;

import java.util.Map;

/**
 * Description
 * Created by pwx on 2016/12/21.
 * Company MingThink
 */

public class WeatherPresenterImpl<T> implements IWeatherPresenter<T>{

    private IWeatherModel<T> iWeatherModel;
    private DataResultView<T> dataResultView;

    public WeatherPresenterImpl(DataResultView<T> dataResultView) {
        this.dataResultView = dataResultView;
        iWeatherModel = new WeatherImpl();
    }

    @Override
    public void getWeather(Map<String,String> param, boolean isList, Class<T> cla) {
        iWeatherModel.getWeather(param,isList,cla,dataResultView);
    }

    @Override
    public void postData(Map<String, String> param, boolean isList, Class<T> cla) {
        iWeatherModel.postData(param,isList,cla,dataResultView);
    }
}
