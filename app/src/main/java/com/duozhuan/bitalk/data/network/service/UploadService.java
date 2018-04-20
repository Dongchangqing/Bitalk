package com.duozhuan.bitalk.data.network.service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.duozhuan.bitalk.data.network.Api;
import com.duozhuan.bitalk.data.network.ApiService;
import com.duozhuan.bitalk.data.network.okhttputils.FileRequestBody;
import com.duozhuan.bitalk.data.network.okhttputils.HeaderInterceptor;
import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class UploadService {
    private String url;
    private LinkedHashMap<String, File> files;
    private LinkedHashMap<String, String> params;
    private FileRequestBody.OnUploadSizeChangedListener uploadSizeChangedLisenter;
    private long totalByteCount = 0;

    public UploadService(String url,
                         LinkedHashMap<String, String> params,
                         LinkedHashMap<String, File> files,
                         FileRequestBody.OnUploadSizeChangedListener lisenter) {
        this.url = url;
        this.params = params;
        this.files = files;
        this.uploadSizeChangedLisenter = lisenter;
    }

    public long getTotalByteCount(){
        return  totalByteCount;
    }

    private RequestBody getRequestBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        if (files != null && files.size() > 0) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), entry.getValue());
                totalByteCount += entry.getValue().length();

                builder.addFormDataPart("file[]", entry.getKey(), requestBody).build();
            }
        }

        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    public Observable<ResponseBody> getUploadService() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if(request.body() == null){
                            return chain.proceed(request);
                        }

                        Request build = request.newBuilder()
                                .method(request.method(),
                                        new FileRequestBody(request.body(), uploadSizeChangedLisenter))
                                .build();
                        return chain.proceed(build);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(Api.class).uploadFiles(url, getRequestBody());
    }
}
