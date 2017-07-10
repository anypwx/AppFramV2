package com.aframe.appframe.mvp.model;

import com.aframe.appframe.mvp.view.DataResultView;

import java.util.Map;

/**
 * Created by pwx on 2017/6/1.
 */

public interface IFileDownloadModel<T> {
    void fileDownload(String action, Map<String, String> param, boolean isList , Class<T> cla, DataResultView<T> dataListener);
}
