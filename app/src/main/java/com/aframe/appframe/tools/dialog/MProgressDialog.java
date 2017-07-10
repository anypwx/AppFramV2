package com.aframe.appframe.tools.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aframe.appframe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pwx on 2017/6/2.
 */

public class MProgressDialog extends DialogFragment{
    @BindView(R.id.cpb_bar)
    ContentLoadingProgressBar cpb_bar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_progress,container);
        ButterKnife.bind(this,v);
        Log.i("pwx","dsdddddddddddddddddddddddddddddddddddddd");
       int ss =  getArguments().getInt("p");
        Log.i("pwx","iiyyyyyy:  "+ss);
        setProgress(ss);
        return v;
    }

    private MProgressDialog setProgress(int p){
        Log.i("pwx","wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        cpb_bar.setProgress(p);
        return this;
    }
}
