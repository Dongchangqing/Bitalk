package com.duozhuan.bitalk.views.browser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.base.base.BaseActivity;
import com.duozhuan.bitalk.event.SelectImg6Event;
import com.duozhuan.bitalk.event.SelectImgEvent;
import com.duozhuan.bitalk.util.SPUtils;
import com.duozhuan.bitalk.widget.LoadingDialog;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;

import static com.duozhuan.bitalk.app.Constants.EVENT_SELECT_IMAGE3;
import static com.duozhuan.bitalk.app.Constants.EVENT_SELECT_IMAGE4;
import static com.duozhuan.bitalk.app.Constants.EVENT_SET_WEB_TITLE;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_ERROR;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_FINISH;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_LOADRESOURCE;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_START;


public class WebActivity extends BaseActivity {

    public static final String URL = "url";
    public static final String TITLE = "title";

    @BindView(R.id.web_content)
    WebView mWebContent;

    @BindView(R.id.tv_title)
    TextView mTvTitle;


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;


    @BindView(R.id.ll_error_page)
    LinearLayout mLlErrorPage;

    private String mUrl;
    private String mTitle="";

    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    public final static int CHOOSER_RESULTCODE = 3;
    public final static int CHOOSER_RESULTCODE_FOR_ANDROID = 4;
    //加载对话框loading
    private LoadingDialog loadingDialog;


    @Override
    protected int getLayout() {
        return R.layout.activity_web_defalut;
    }

    @Override
    protected void initEventAndData() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        RxBus.get().register(this);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(URL);
        mTitle = intent.getStringExtra(TITLE);
        if (!TextUtils.isEmpty(mTitle)){
            mTvTitle.setText(mTitle);
        }
        DefaultWebViewSetting.init(this, mWebContent, true, false,true);
        mRefreshLayout.setRefreshHeader(new DefaultRefreshHeader(this));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mWebContent.reload();
            loadingDialog=new LoadingDialog(mContext,"玩命加载中");
            loadingDialog.show();
        });

        loadContent();
        loadingDialog=new LoadingDialog(mContext,"玩命加载中");
        loadingDialog.show();
    }

    public static void actionWeb(Context context, String url,String title) {
        if (context != null && !TextUtils.isEmpty(url)) {

            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(URL, url);
            intent.putExtra(TITLE, title);
            context.startActivity(intent);
        }
    }


    @OnClick({R.id.iv_back,  R.id.ll_error_page})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_error_page:
                loadContent();
                break;
        }
    }


    private void loadContent() {
        mWebContent.loadUrl(mUrl);
    }

    private void showLoadFail() {
        mWebContent.setVisibility(View.GONE);
        mLlErrorPage.setVisibility(View.VISIBLE);
    }

    private void showLoadStart() {
        mWebContent.setVisibility(View.VISIBLE);
        mLlErrorPage.setVisibility(View.GONE);
    }



    // webview 开始加载
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_WEBVIEW_PAGE_START)
    })
    public void onPageStart(String url) {
        showLoadStart();
    }


    // webview 加载结束
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_WEBVIEW_PAGE_FINISH)
    })
    public void onPageFinish(String s) {
        if (loadingDialog!=null){
            loadingDialog.close();
        }
        mRefreshLayout.finishRefresh();
    }

    // webview 加载失败
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_WEBVIEW_PAGE_ERROR)
    })
    public void onPageFail(String s) {
        if (loadingDialog!=null){
            loadingDialog.close();
        }
        showLoadFail();
    }

    // webview url 拦截
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_WEBVIEW_PAGE_LOADRESOURCE)
    })
    public void onLoadSource(String url) {
       if (url.contains("access_token")){
           getValueByName(url);
       }
    }


    // webview 设置标题
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_SET_WEB_TITLE)
    })
    public void onSetTitle(String title) {
        if (TextUtils.isEmpty(mTitle)) {
            mTvTitle.setText(title);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebContent != null) {
            boolean canGoBack = mWebContent.canGoBack();
            if (canGoBack) {
                mWebContent.goBack();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
        if (mWebContent != null) {
            mWebContent.clearHistory();
            mWebContent.clearFormData();
            mWebContent.destroy();
        }
    }
    public void getValueByName(String url) {
        String access_token="";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains("access_token")) {
                access_token= str;
                SPUtils.setString(Constants.LOGIN_ACCENTTOKEN,access_token);
            }
            if (str.contains("username")){
                String username=str.replace("username=", "");
                SPUtils.setString(Constants.LOGIN_USERNAME,username);
            }
        }

        if (!TextUtils.isEmpty(access_token)) {
            RxBus.get().post(Constants.EVENT_LOGIN_SUCCESS, access_token);
        }
        CookieUtils.setCookie(access_token);
        finish();
        overridePendingTransition(0,R.anim.current_to_right);
    }


    // webview 开始加载
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_SELECT_IMAGE3)
    })
    public void openFileChooserImpl(SelectImgEvent event) {
        mUploadMessage = event.getUploadMsg();
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"), CHOOSER_RESULTCODE);
    }

    // webview 开始加载
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_SELECT_IMAGE4)
    })
    public void openFileChooserImplForAndroid5(SelectImg6Event event) {
        mUploadMessageForAndroid5 = event.getUploadMsg();
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");

        startActivityForResult(chooserIntent, CHOOSER_RESULTCODE_FOR_ANDROID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent intent) {
        if (requestCode == CHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null: intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (requestCode == CHOOSER_RESULTCODE_FOR_ANDROID){
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null: intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
    }
}
