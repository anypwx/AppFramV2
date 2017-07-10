package com.aframe.appframe.tools.net;

import com.aframe.appframe.YConstance;
import com.aframe.appframe.ui.bean.BaseBean;
import com.aframe.appframe.ui.bean.BaseNewsBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

/**
 * Description
 * Created by pwx on 2016/12/21.
 * Company MingThink
 */

public interface IRetorft {

    /**
     *
     * @param action  是
     * @param param
     * @return
     */

    //获取数据
    @GET("{action}")
    Observable<BaseNewsBean> getData(@Path(value = "action",encoded = true) String action, @QueryMap Map<String,String> param);

    //提交数据
    @FormUrlEncoded
    @POST("{action}")
    Observable<BaseBean> subData(@Path(value = "action",encoded = true) String action, @FieldMap(encoded = true) Map<String,String> param);


    //上传文件
    @Multipart
    @POST("{action}")
    Observable<BaseBean> fileUpload(@Path(value = "action",encoded = true) String action, @PartMap Map<String, RequestBody> files);

    //下载文件
    @Streaming
    @GET("{action}")
    Observable<ResponseBody> fileDownload(@Path(value = "action",encoded = true) String action, @QueryMap Map<String,String> param);

}
