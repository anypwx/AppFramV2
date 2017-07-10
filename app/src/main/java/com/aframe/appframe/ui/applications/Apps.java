package com.aframe.appframe.ui.applications;

import android.app.Application;
import android.content.Context;

import com.aframe.appframe.tools.dialog.MProgressAlertDialog;
import com.aframe.appframe.tools.dialog.MProgressDialog;

/**
 * Created by pwx on 2017/6/1.
 */

public class Apps extends Application {
    public static Context appCox;
    public static Apps apps;
    private MProgressDialog mProgressDialog;
    private MProgressAlertDialog mProgressAlertDialog;

    @Override
    public void onCreate() {
        super.onCreate();
        appCox = getApplicationContext();
        apps = (Apps) getApplicationContext();
        mProgressDialog = new MProgressDialog();
    }

    public MProgressDialog getDialog(){
        if(null == mProgressDialog){
            mProgressDialog = new MProgressDialog();
        }
        return mProgressDialog;
    }

    public MProgressAlertDialog getAlertDialog(Context c){
        if(null == mProgressAlertDialog){
            mProgressAlertDialog = new MProgressAlertDialog(c);
        }
        return mProgressAlertDialog;
    }

}
