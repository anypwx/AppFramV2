package com.aframe.appframe;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aframe.appframe.mvp.presenter.FileDownloadPresenterImpl;
import com.aframe.appframe.mvp.presenter.FileUploadPresenterImpl;
import com.aframe.appframe.mvp.presenter.IFileDownloadPresenter;
import com.aframe.appframe.mvp.presenter.IFileUploadPresenter;
import com.aframe.appframe.mvp.presenter.IWeatherPresenter;
import com.aframe.appframe.mvp.presenter.WeatherPresenterImpl;
import com.aframe.appframe.mvp.view.DataResultView;
import com.aframe.appframe.tools.SaveDisk;
import com.aframe.appframe.tools.Utils;
import com.aframe.appframe.tools.dialog.MProgressDialog;
import com.aframe.appframe.tools.listener.IDialogProgressListener;
import com.aframe.appframe.ui.activity.ListLoadMoreActivity;
import com.aframe.appframe.ui.applications.Apps;
import com.aframe.appframe.ui.bean.WeatherBean;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity{
    @BindView(R.id.tv_he)
    TextView tv_he;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.btn_sub)
    Button btn_sub;
    @BindView(R.id.tv_uploadurl)
    TextView tv_uploadurl;
    @BindView(R.id.btn_upload)
    Button btn_upload;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.btn_go)
    Button btn_go;
    @BindView(R.id.btn_down)
    Button btn_down;

    Bundle bundle;

    private Context mc = this;
    private IWeatherPresenter<WeatherBean> iWeatherPresenter;
    private IFileUploadPresenter<WeatherBean> fileUploadPresenter;
    private IFileDownloadPresenter<ResponseBody> fileDownloadPresenter;
    private static final int REQUEST_ALBUM_OK = 1000;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1030;
    private Map<String,RequestBody> requestBodyMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bundle = new Bundle();
//        getWeather();
//        imgListener();


    }

    @OnClick(R.id.btn_sub)
    public void btnSub(){
        postData();
    }
    @OnClick(R.id.btn_go)
    public void goPage(){
        Intent intent = new Intent(this, ListLoadMoreActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_down)
    public void downLoads(){
        Apps.apps.getAlertDialog(MainActivity.this).showDialog();
        fileDownloadPresenter = new FileDownloadPresenterImpl<>(new DataResultView<ResponseBody>() {
            @Override
            public void success(ResponseBody d) {
                SaveDisk.writeResponseBodyToDisk(mc, d, new IDialogProgressListener() {
                    @Override
                    public void progressInt(int p) {
//                        Apps.apps.getAlertDialog(MainActivity.this).setProgress(p);
//                        MProgressDialog mProgressDialog = new MProgressDialog();
//                        bundle.putInt("p",p);
//                        mProgressDialog.setArguments(bundle);
//                        mProgressDialog.show(getSupportFragmentManager(),"ee");

                    }
                });
            }

            @Override
            public void fail(String s, int erCode) {

            }

            @Override
            public void progressData(int p) {
                handler.sendEmptyMessage(p);
            }
        });

        YConstance.paramMap.clear();
        fileDownloadPresenter.fileDownload(BuildConfig.DOWNLOAD_ACTION,YConstance.paramMap,false,ResponseBody.class);




    }

    @OnClick(R.id.btn_upload)
    public void setUpload(){
        //第二个参数是需要申请的权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        }else {
            //权限已经被授予，在这里直接写要执行的相应方法即可
            openPics();
        }

    }

    private void openPics(){
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, REQUEST_ALBUM_OK);
    }


    private void getWeather(){
        iWeatherPresenter = new WeatherPresenterImpl(new DataResultView<WeatherBean>() {
            @Override
            public void success(WeatherBean d) {
                tv_he.setText(d.getCity());
            }

            @Override
            public void fail(String s, int erCode) {

            }

            @Override
            public void progressData(int p) {

            }
        });
        YConstance.paramMap.clear();
        YConstance.paramMap.put(YConstance.ACTION,YConstance.API_ACTION);
        YConstance.paramMap.put("cityname","南昌");
        iWeatherPresenter.getWeather(YConstance.paramMap,false,WeatherBean.class);
    }

    private void postData(){
        iWeatherPresenter = new WeatherPresenterImpl(new DataResultView<WeatherBean>() {
            @Override
            public void success(WeatherBean d) {
                tv_he.setText(d.getCity());
            }

            @Override
            public void fail(String s, int erCode) {

            }

            @Override
            public void progressData(int p) {

            }
        });
        YConstance.paramMap.clear();
        YConstance.paramMap.put(YConstance.ACTION,YConstance.USER_ACTION);
        YConstance.paramMap.put("id","1");
        iWeatherPresenter.postData(YConstance.paramMap,false,WeatherBean.class);
    }

    private File uri2File(Uri uri) {
        File file = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor actualimagecursor = managedQuery(uri, proj, null,
                null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        file = new File(img_path);
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK != resultCode){
            return;
        }
        switch (requestCode){
            case REQUEST_ALBUM_OK:
                Uri s = data.getData();
                File file = uri2File(s);
                Utils.isExist(file);
                Glide.with(this).load(file).into(iv_img);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
                fileUploadPresenter = new FileUploadPresenterImpl<>(new DataResultView<WeatherBean>() {
                    @Override
                    public void success(WeatherBean d) {

                    }

                    @Override
                    public void fail(String s, int erCode) {
                        Log.i("pwx","文件上传错误返回: "+s+","+erCode);
                    }

                    @Override
                    public void progressData(int p) {

                    }
                });
                requestBodyMap.put("photos\"; filename=\"icon.png",requestBody);
                fileUploadPresenter.fileUpload(YConstance.UPLOAD_ACTION,requestBodyMap,false,WeatherBean.class);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openPics();
            } else
            {
                // Permission Denied
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Apps.apps.getAlertDialog(MainActivity.this).setProgress(msg.what);
        }
    };
}
