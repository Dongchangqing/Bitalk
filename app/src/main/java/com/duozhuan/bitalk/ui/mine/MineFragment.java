package com.duozhuan.bitalk.ui.mine;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.duozhuan.bitalk.DataCleanManager;
import com.duozhuan.bitalk.LoginActivity;
import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.base.base.BaseFragment;
import com.duozhuan.bitalk.data.network.ApiService;
import com.duozhuan.bitalk.util.SPUtils;
import com.duozhuan.bitalk.views.browser.CookieUtils;
import com.duozhuan.bitalk.views.browser.DefaultWebViewSetting;
import com.duozhuan.bitalk.views.browser.WebActivity;
import com.duozhuan.bitalk.widget.CommonDialog;
import com.google.gson.JsonObject;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.duozhuan.bitalk.app.Constants.EVENT_LOGIN_SUCCESS;

public class MineFragment extends BaseFragment {
    @BindView(R.id.fragment_mine_web)
    WebView mWebView;
    @BindView(R.id.rl_mine_logout)
    RelativeLayout mLogout;
    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initEventAndData() {
        RxBus.get().register(this);
        DefaultWebViewSetting.init((AppCompatActivity) mContext, mWebView, true, false,false);
        initView();
    }

    @OnClick({R.id.rl_mine_persondata, R.id.rl_mine_draft, R.id.rl_mine_collect, R.id.rl_mine_moneypack, R.id.rl_mine_setting, R.id.rl_mine_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_persondata:
                if (SPUtils.isLogin()) {
                    WebActivity.actionWeb(mContext, Constants.PERSONALEDIT + SPUtils.getString(Constants.LOGIN_USERNAME),"我的个人资料");
                }else {
                    LoginActivity.actionActivity(mContext);
                }
                break;
            case R.id.rl_mine_draft:
                if (SPUtils.isLogin()) {
                    WebActivity.actionWeb(mContext, Constants.DRAFTS,"草稿");
                }else {
                    LoginActivity.actionActivity(mContext);
                }
                break;
            case R.id.rl_mine_collect:
                if (SPUtils.isLogin()) {
                    WebActivity.actionWeb(mContext, Constants.BOOKMARKS,"收藏");
                }else {
                    LoginActivity.actionActivity(mContext);
                }
                break;
            case R.id.rl_mine_moneypack:
                if (SPUtils.isLogin()) {
                    WebActivity.actionWeb(mContext, Constants.WALLET,"钱包");
                }else {
                    LoginActivity.actionActivity(mContext);
                }
                break;
            case R.id.rl_mine_setting:
                if (SPUtils.isLogin()) {
                    WebActivity.actionWeb(mContext, Constants.SETTING,"设置");
                }else {
                    LoginActivity.actionActivity(mContext);
                }
                break;
            case R.id.rl_mine_logout:
                CommonDialog dialog = new CommonDialog(mActivity);
                dialog.setTitle("提示");
                dialog.setMessage("再次登录需要重新输入用户名、密码。\n" +
                        "确定要退出登录吗？");
                dialog.setListener(new CommonDialog.CommonDialogInterface() {
                    @Override
                    public void onOK() {

                        mWebView.loadUrl(Constants.LOGINOUTURL);

                        CookieUtils.clearCookie();
                        SPUtils.setString(Constants.LOGIN_ACCENTTOKEN,"");
                        CookieUtils.setCookie("");
                        CookieUtils.setCookies("");
                        RxBus.get().post(Constants.EVENT_LOGOUT_SUCCESS,"");
                        initView();
                        clearWebViewCache();
                        DataCleanManager.DeleteFile(new File("data/data/com.duozhuan.bitalk"));
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                dialog.show();
                break;
        }
    }
    public void post1(){
            ApiService.getInstance().getApi().revoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<JsonObject>() {
                @Override
                public void accept(JsonObject jsonObject) throws Exception {
                    Log.i("dddd", jsonObject.toString());
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    Log.i("dddd",throwable.toString());
                }
            });
    }
    public void post() {
        //创建网络处理的对象
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        //post请求来获得数据
        //创建一个RequestBody，存放重要数据的键值对
        RequestBody body = new FormBody.Builder()
                //.add("showapi_appid", "13074")
                //.add("showapi_sign", "ea5b4bf2e140498bb772d1bf2a51a7a0")
                .build();
        //创建一个请求对象，传入URL地址和相关数据的键值对的对象
        Log.i("dddd-1",SPUtils.getString(Constants.LOGIN_ACCENTTOKEN));
        Request request = new Request.Builder()
                .addHeader(":authority","steemconnect.com")
                .addHeader(":method","POST")
                .addHeader(":path","/api/oauth2/token/revoke")
                .addHeader(":scheme","https")
                .addHeader("accept","application/json, text/plain, */*")
                .addHeader("accept-encoding","gzip, deflate, br")
                .addHeader("accept-language","zh-CN,zh;q=0.9")
                .addHeader("authorization",SPUtils.getString(Constants.LOGIN_ACCENTTOKEN))
                .addHeader("content-length","223")
                .addHeader("content-type","application/json")
                .addHeader("origin","https://steem.duozhuan.cn")
                .addHeader("referer","https://steem.duozhuan.cn/@"+SPUtils.getString(Constants.LOGIN_USERNAME))
                .addHeader("user-agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Mobile Safari/537.36")
                .url("https://steemconnect.com/api/oauth2/token/revoke")
                .post(body).build();

        //创建一个能处理请求数据的操作类
        Call call = client.newCall(request);

        //使用异步任务的模式请求数据
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dddd",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("dddds",response.body().string());
            }
        });
    }

    public void initView(){
        if (!TextUtils.isEmpty(SPUtils.getString(Constants.LOGIN_ACCENTTOKEN))){
            mLogout.setVisibility(View.VISIBLE);
        }else{
            mLogout.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_LOGIN_SUCCESS)
    })
    public void loginSuccess(String access_token) {
        initView();
    }
    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache(){

        //清理Webview缓存数据库
        try {
            mContext.deleteDatabase("webview.db");
            mContext.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(mContext.getFilesDir().getAbsolutePath()+"/webviewcache");

        File webviewCacheDir = new File(mContext.getCacheDir().getAbsolutePath()+"/webviewCache");

        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {


        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }
}
