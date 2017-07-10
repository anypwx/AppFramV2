package com.aframe.appframe.tools.net;

import android.os.Build;
import android.util.Log;

import com.aframe.appframe.BuildConfig;
import com.aframe.appframe.MainActivity;
import com.aframe.appframe.YConstance;
import com.aframe.appframe.mvp.view.DataResultView;
import com.aframe.appframe.tools.SaveDisk;
import com.aframe.appframe.tools.listener.IDialogProgressListener;
import com.aframe.appframe.ui.applications.Apps;
import com.aframe.appframe.ui.bean.BaseBean;
import com.aframe.appframe.ui.bean.BaseNewsBean;
import com.aframe.appframe.ui.bean.NewsListBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Description
 * Created by pwx on 2016/12/21.
 * Company MingThink
 */

public class NetUtils {
    private static NetUtils netUtils;
    public static Gson gson;
    private OkHttpClient okHttpClient;
    private IRetorft iRetorft;
    private DataResultView dataListener;

    public static NetUtils getInstance(){
        if(null == netUtils){
            netUtils = new NetUtils();
        }
        return netUtils;
    }

    public NetUtils() {
        gson = new Gson();
        initHttpClient2();
        initRetrofit();
    }

    public NetUtils setDataListener(DataResultView dataListener){
        this.dataListener = dataListener;
        return netUtils;
    }

    private void initHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("apikey","15ca76cb5938978cf62bb1c54cdf2c7d");
                        return chain.proceed(builder.build());
                    }
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

    }

    private void initHttpClient2(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("apikey","15ca76cb5938978cf62bb1c54cdf2c7d");
                        return chain.proceed(builder.build());
                    }
                })
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response rsp = chain.proceed(chain.request());
                        Response.Builder b = rsp.newBuilder().body(new MResponeseBody(rsp.body(), new IDialogProgressListener() {
                            @Override
                            public void progressInt(int p) {
                                Log.i("pwx","---下载 ---  "+p);
                                dataListener.progressData(p);
                            }
                        }));
                        return b.build();
                    }
                })
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .build();

    }

    private void initRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.appIp)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        iRetorft = retrofit.create(IRetorft.class);
    }

    public<T> void netGetWork(Map<String,String> param, Class<T> cls, boolean isList){
        Observable<BaseNewsBean> rep = iRetorft.getData(param.get(YConstance.ACTION),param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        connNet(rep,cls,isList);
    }

    public<T> void netPostWork(Map<String,String> param,Class<T> cls,boolean isList){
        Observable<BaseBean> rep = iRetorft.subData(param.get(YConstance.ACTION),param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

//        connNet(rep,cls,isList);
    }

    public<T> void netUploadWork(String action,Map<String, RequestBody> param, Class<T> cls, boolean isList){
        Observable<BaseBean> rep = iRetorft.fileUpload(action,param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

//        connNet(rep,cls,isList);
    }

    public<T> void netDownloadWork(String action,Map<String, String> param, Class<T> cls, boolean isList){
        Observable<ResponseBody> rep = iRetorft.fileDownload(action,param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        connNetDownLoad(rep,cls,isList);
    }


    private<T> void connNet(Observable<BaseNewsBean> rep, final Class<T> cls,final boolean isList){
        rep.subscribe(new Observer<BaseNewsBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseNewsBean baseBean) {
                if(200 != baseBean.getCode()){
                    dataListener.fail(baseBean.getMsg(),baseBean.getCode());
                    return;
                }
                T data;
                if(isList){
//                    Type userType = new TypeToken<T>() {}.getType();
//                    data = gson.fromJson(baseBean.getNewslist(),userType);
                    //这样子写是为了拿到源数据进行list化
                    String jsonStr = gson.toJson(baseBean);
                    data = gson.fromJson(jsonStr,cls);
                }else{
                    data = gson.fromJson(baseBean.getNewslist(),cls);
                }
                if(null != dataListener){
                    dataListener.success(data);
                }

            }

            @Override
            public void onError(Throwable e) {
                dataListener.fail(e.getMessage(),1104);
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private<T> void connNetDownLoad(Observable<ResponseBody> rep, final Class<T> cls,final boolean isList){
        rep.subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody body) {
                dataListener.success(body);
//                SaveDisk.writeResponseBodyToDisk(Apps.appCox,body);
            }

            @Override
            public void onError(Throwable e) {
                dataListener.fail(e.getMessage(),1104);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
