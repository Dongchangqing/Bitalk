package com.duozhuan.bitalk.data.network.okhttputils;

import java.io.IOException;

import com.duozhuan.bitalk.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        builder.removeHeader("User-Agent");
        builder.addHeader("User-Agent", BuildConfig.USER_AGENT);


        builder.method(chain.request().method(), chain.request().body());

        return chain.proceed(builder.build());
    }


}
