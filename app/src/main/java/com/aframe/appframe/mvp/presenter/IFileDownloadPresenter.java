package com.aframe.appframe.mvp.presenter;

import java.util.Map;

/**
 * Created by pwx on 2017/6/1.
 */

public interface IFileDownloadPresenter<T> {
    void fileDownload(String action, Map<String, String> param, boolean isList , Class<T> cla);
}
