package com.duozhuan.bitalk.ui.attention;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duozhuan.bitalk.LoginActivity;
import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.base.base.BaseFragment;
import com.duozhuan.bitalk.event.JumpUrlEvent;
import com.duozhuan.bitalk.event.SelectImg6Event;
import com.duozhuan.bitalk.event.SelectImgEvent;
import com.duozhuan.bitalk.util.SPUtils;
import com.duozhuan.bitalk.views.browser.CookieUtils;
import com.duozhuan.bitalk.views.browser.DefaultRefreshHeader;
import com.duozhuan.bitalk.views.browser.DefaultWebViewSetting;
import com.duozhuan.bitalk.views.browser.WebActivity;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.duozhuan.bitalk.app.Constants.EVENT_ATTENTION_RELODAD;
import static com.duozhuan.bitalk.app.Constants.EVENT_HIDDEN_BOTTOM_AND_TOP;
import static com.duozhuan.bitalk.app.Constants.EVENT_HIDDEN_TOP;
import static com.duozhuan.bitalk.app.Constants.EVENT_INVITATION_DETAIL;
import static com.duozhuan.bitalk.app.Constants.EVENT_LOGIN_SUCCESS;
import static com.duozhuan.bitalk.app.Constants.EVENT_LOGOUT_SUCCESS;
import static com.duozhuan.bitalk.app.Constants.EVENT_SELECT_IMAGE5;
import static com.duozhuan.bitalk.app.Constants.EVENT_SELECT_IMAGE6;
import static com.duozhuan.bitalk.app.Constants.EVENT_SHOW_BOTTOM_AND_TOP;
import static com.duozhuan.bitalk.app.Constants.EVENT_SHOW_TOP;
import static com.duozhuan.bitalk.app.Constants.EVENT_USER_INTRODUCTION;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_NEW_PAGE;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_FINISH;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_LOADRESOURCE;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_START;


public class AttentionFragment extends BaseFragment {

    public static final String URL = "url";

    @BindView(R.id.web_content)
    WebView mWebContent;

    @BindView(R.id.tv_title)
    TextView mTvTitle;


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;


    @BindView(R.id.ll_error_page)
    LinearLayout mLlErrorPage;

    @BindView(R.id.toolbar)
    RelativeLayout mToolbar;

    private String mUrl = Constants.Host;

    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    public final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;

    @Override
    protected int getLayout() {
        return R.layout.fragment_attention;
    }

    @Override
    protected void initEventAndData() {

        RxBus.get().register(this);
        DefaultWebViewSetting.init((AppCompatActivity) mContext, mWebContent, true, false,false);
        mRefreshLayout.setRefreshHeader(new DefaultRefreshHeader(getContext()));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mWebContent.loadUrl(mUrl);
        });

        loadContent();

    }

    private void loadContent() {
        mWebContent.loadUrl(mUrl);
    }

    @OnClick({R.id.ll_error_page, R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                if (SPUtils.isLogin()) {
                    WebActivity.actionWeb(mContext, Constants.SEND,"发帖");
                } else {
                    LoginActivity.actionActivity(mContext);
                }
                break;
            case R.id.ll_error_page:
                loadContent();
                break;
        }
    }

    private void showLoadFail() {
        mWebContent.setVisibility(View.GONE);
        mLlErrorPage.setVisibility(View.VISIBLE);
    }

    private void showLoadStart() {
        mWebContent.setVisibility(View.VISIBLE);
        mLlErrorPage.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxBus.get().unregister(this);
        if (mWebContent != null) {
            mWebContent.clearHistory();
            mWebContent.clearFormData();
            mWebContent.destroy();
        }
    }


    public static AttentionFragment newInstance() {
        return new AttentionFragment();
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
    public void onPageFinish(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        String cookieStr = cookieManager.getCookie(url);
        Log.i("cookie",cookieStr+"");
        if (!TextUtils.isEmpty(cookieStr)){
            if (cookieStr.contains("access_token")){
                if (!TextUtils.isEmpty(SPUtils.getString(Constants.LOGIN_ACCENTTOKEN))) {
                    CookieUtils.setCookie(cookieStr);
                    SPUtils.setString(Constants.LOGIN_ACCENTTOKEN, cookieStr);
                }
            }
        }
        mRefreshLayout.finishRefresh();
    }



    //跳转帖子详情
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_INVITATION_DETAIL)
    })
    public void openInvitationDetail(JumpUrlEvent event) {
        if (event.getOpenNew()){
            //backPreviousPage();
            WebActivity.actionWeb(mContext,event.getUrl(),"");
        }else{
            mWebContent.loadUrl(event.getUrl());
        }
    }

    //登录注册跳转新页面
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_WEBVIEW_NEW_PAGE)
    })
    public void openNewPage(String url) {
        backPreviousPage();
        //WebActivity.actionWeb(mContext,url);
        LoginActivity.actionActivity(mContext);
    }
    //跳转用户简介
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_USER_INTRODUCTION)
    })
    public void openUserIntroduction(JumpUrlEvent event) {
        if (event.getOpenNew()){
            backPreviousPage();
            WebActivity.actionWeb(mContext,event.getUrl(),"");
        }else{
            mWebContent.loadUrl(event.getUrl());
        }
    }


    //显示顶部
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_SHOW_TOP)
    })
    public void showTop(String str) {
        mToolbar.setVisibility(View.VISIBLE);
    }

    //隐藏顶部
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_HIDDEN_TOP)
    })
    public void hiddenTop(String str) {
        mToolbar.setVisibility(View.GONE);
    }

    //显示顶部
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_SHOW_BOTTOM_AND_TOP)
    })
    public void showBottomAndTop(String str) {
        mToolbar.setVisibility(View.VISIBLE);
    }

    //隐藏顶部
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_HIDDEN_BOTTOM_AND_TOP)
    })
    public void hiddenBottomAndTop(String str) {
        mToolbar.setVisibility(View.GONE);
    }

    //登录成功
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_LOGIN_SUCCESS)
    })
    public void loginSuccess(String access_token) {
        mWebContent.loadUrl(mUrl);
    }

    //退出成功
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_LOGOUT_SUCCESS)
    })
    public void logoutSuccess(String access_token) {
        mWebContent.reload();
    }
    public void backPreviousPage(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mWebContent != null) {
                    boolean canGoBack = mWebContent.canGoBack();
                    if (canGoBack) {
                        mWebContent.goBack();
                    }
                }
            }
        },1500);
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

    // 重新load url
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_ATTENTION_RELODAD)
    })
    public void onReloadUrl(String isreload) {
        mWebContent.loadUrl(mUrl);
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
        //解决登录退不出去问题
        //CookieUtils.setCookie(access_token);
    }

    // webview 开始加载
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_SELECT_IMAGE5)
    })
    public void openFileChooserImpl(SelectImgEvent event) {
        mUploadMessage = event.getUploadMsg();
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    // webview 开始加载
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_SELECT_IMAGE6)
    })
    public void openFileChooserImplForAndroid5(SelectImg6Event event) {
        mUploadMessageForAndroid5 = event.getUploadMsg();
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");

        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null: intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5){
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
