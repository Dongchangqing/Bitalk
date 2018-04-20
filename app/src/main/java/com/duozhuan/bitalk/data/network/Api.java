package com.duozhuan.bitalk.data.network;

import com.duozhuan.bitalk.data.network.bean.LoginBean;
import com.duozhuan.bitalk.data.network.bean.ResultBean;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;



public interface Api {
    //////////////上传下载通用接口start
    /**
     * 上传文件
     * @param url  上传url地址
     * @param requestBody 文件及参数先包装成requestbody形式再进行上传操作
     * @return
     */
    @POST
    Observable<ResponseBody> uploadFiles(@Url String url, @Body RequestBody requestBody);

    /**
     * 文件下载
     *
     * @param url 文件下载地址
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);
    //////////////上传下载通用接口end


    @GET("oauth2/token/revoke")
    Flowable<JsonObject> revoke();


}
