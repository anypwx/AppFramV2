package com.aframe.appframe.tools.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.widget.TextView;

import com.aframe.appframe.R;
import com.aframe.appframe.ui.applications.Apps;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pwx on 2017/6/2.
 */

public class MProgressAlertDialog extends AlertDialog {
    @BindView(R.id.tv_titleBar)
    TextView tv_titleBar;
    @BindView(R.id.cpb_bar_alert)
    ContentLoadingProgressBar cpb_bar_alert;


    public MProgressAlertDialog(Context context) {
        super(context);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertdialog_progress);
        ButterKnife.bind(this);
        setCancelable(false);

    }

    public void setTile(String t){
        tv_titleBar.setText(Apps.appCox.getString(R.string.tv_downing,t));
    }

    public void setProgress(int p){
        setTile(p+"");
        cpb_bar_alert.setProgress(p);
    }

    public MProgressAlertDialog showDialog(){
        this.show();
        return this;
    }
}
