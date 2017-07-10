package com.aframe.appframe.mvp.presenter;

import com.aframe.appframe.mvp.model.FileUploadModelImpl;
import com.aframe.appframe.mvp.model.IFileUploadModel;
import com.aframe.appframe.mvp.view.DataResultView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * Description
 * Created by pwx on 2016/12/23.
 * Company MingThink
 */

public class FileUploadPresenterImpl<T> implements IFileUploadPresenter<T> {
    private IFileUploadModel<T> iFileUploadModel;
    private DataResultView<T> dataResultView;

    public FileUploadPresenterImpl(DataResultView<T> dataResultView) {
        this.dataResultView = dataResultView;
        iFileUploadModel = new FileUploadModelImpl<>();
    }

    @Override
    public void fileUpload(String action, Map<String, RequestBody> param, boolean isList, Class<T> cla) {
        iFileUploadModel.fileUpload(action,param,isList,cla,dataResultView);
    }
}
