package com.aframe.appframe.mvp.model;

import com.aframe.appframe.mvp.view.DataResultView;
import com.aframe.appframe.tools.net.NetUtils;

import java.util.Map;

/**
 * Created by pwx on 2017/6/1.
 */

public class FileDownloadModelImpl<T> implements IFileDownloadModel<T> {

    @Override
    public void fileDownload(String action, Map<String, String> param, boolean isList, Class<T> cla, DataResultView<T> dataListener) {
        NetUtils.getInstance().setDataListener(dataListener).netDownloadWork(action,param,cla,isList);
    }
}
