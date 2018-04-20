package com.duozhuan.bitalk.data.network;

import com.duozhuan.bitalk.BuildConfig;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.data.network.okhttputils.HeaderInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {

    public static final String BASE_URL = Constants.REMOVETOKEN;
    private final Api mApi;
    private OkHttpClient mOkHttpClient;

    private volatile static ApiService instance;


    private ApiService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            //DEBUG模式下打印请求的json数据，如需打印所有数据，如请求信息和response，去掉if判断即可。
            builder.addInterceptor(new HttpLoggingInterceptor(message -> {
//                    if (message.matches(Constant.REGEX_LOG))
                Logger.e(message);
            }).setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        mOkHttpClient = builder.build();
        mApi = getInterfaceService(BASE_URL, Api.class);
    }

    public static ApiService getInstance() {
        if (instance == null) {
            synchronized (ApiService.class) {
                if (instance == null)
                    instance = new ApiService();
            }
        }
        return instance;
    }


    private <T> T getInterfaceService(String baseUrl, Class<T> tClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(tClass);
    }


    private Gson buildGson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .create();
        return gson;
    }

    class IntegerDefault0Adapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("")) {
                    return 0;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsInt();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

    public Api getApi() {
        return mApi;
    }
}
