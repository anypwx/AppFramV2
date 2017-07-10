package com.aframe.appframe.mvp.presenter;

import com.aframe.appframe.mvp.view.DataResultView;

import java.util.Map;

import okhttp3.RequestBody;


/**
 * Description
 * Created by pwx on 2016/12/23.
 * Company MingThink
 */

public interface IFileUploadPresenter<T> {
    void fileUpload(String action, Map<String, RequestBody> param, boolean isList, Class<T> cla);
}
