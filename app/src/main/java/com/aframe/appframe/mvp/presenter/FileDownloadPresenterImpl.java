package com.aframe.appframe.mvp.presenter;

import com.aframe.appframe.mvp.model.FileDownloadModelImpl;
import com.aframe.appframe.mvp.model.IFileDownloadModel;
import com.aframe.appframe.mvp.view.DataResultView;

import java.util.Map;

/**
 * Created by pwx on 2017/6/1.
 */

public class FileDownloadPresenterImpl<T> implements IFileDownloadPresenter<T> {
    private IFileDownloadModel<T> iFileDownloadModel;
    private DataResultView<T> dataResultView;

    public FileDownloadPresenterImpl(DataResultView<T> dataResultView) {
        this.dataResultView = dataResultView;
        iFileDownloadModel = new FileDownloadModelImpl<>();
    }

    @Override
    public void fileDownload(String action, Map<String, String> param, boolean isList, Class<T> cla) {
        iFileDownloadModel.fileDownload(action,param,isList,cla,dataResultView);
    }
}
