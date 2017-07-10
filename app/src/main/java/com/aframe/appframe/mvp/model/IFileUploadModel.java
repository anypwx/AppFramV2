package com.aframe.appframe.mvp.model;

import com.aframe.appframe.mvp.view.DataResultView;

import java.util.Map;

import okhttp3.RequestBody;


/**
 * Description
 * Created by pwx on 2016/12/23.
 * Company MingThink
 */

public interface IFileUploadModel<T> {
    void fileUpload(String action,Map<String, RequestBody> param, boolean isList , Class<T> cla, DataResultView<T> dataListener);
}
