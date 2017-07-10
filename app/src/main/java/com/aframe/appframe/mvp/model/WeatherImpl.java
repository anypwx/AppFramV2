package com.aframe.appframe.mvp.model;

import com.aframe.appframe.mvp.view.DataResultView;
import com.aframe.appframe.tools.net.NetUtils;
import com.aframe.appframe.ui.bean.WeatherBean;

import java.util.Map;

/**
 * Description
 * Created by pwx on 2016/12/21.
 * Company MingThink
 */

public class WeatherImpl<T> implements IWeatherModel<T>{

    @Override
    public void getWeather(Map<String,String> param, boolean isList , Class<T> cla, DataResultView<T> dataListener) {
        NetUtils.getInstance().setDataListener(dataListener).netGetWork(param,cla,isList);
//        NetUtils.getInstance().netWork(cla,isList);
    }

    @Override
    public void postData(Map<String, String> param, boolean isList, Class<T> cla, DataResultView<T> dataListener) {
        NetUtils.getInstance().setDataListener(dataListener).netPostWork(param,cla,isList);
    }
}
