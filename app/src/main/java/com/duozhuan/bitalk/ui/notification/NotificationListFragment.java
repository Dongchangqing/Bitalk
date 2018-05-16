package com.duozhuan.bitalk.ui.notification;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.base.base.BaseFragment;
import com.duozhuan.bitalk.util.SPUtils;
import com.duozhuan.bitalk.views.browser.DefaultRefreshHeader;
import com.duozhuan.bitalk.views.browser.DefaultWebViewSetting;
import com.duozhuan.bitalk.widget.LoadingDialog;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;

import static com.duozhuan.bitalk.app.Constants.EVENT_LOGIN_SUCCESS;
import static com.duozhuan.bitalk.app.Constants.EVENT_LOGOUT_SUCCESS;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_ERROR;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_FINISH;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_START;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PROGRESS_REFRESH;


public class NotificationListFragment extends BaseFragment {

    @BindView(R.id.fragment_notification_web)
    WebView mWebContent;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.ll_error_page)
    LinearLayout mLlErrorPage;
    String mUrl = "";
    //加载对话框loading
    private LoadingDialog loadingDialog;

    @Override
    protected int getLayout() {
        return R.layout.fragment_notificationlist;
    }

    @Override
    protected void initEventAndData() {

        RxBus.get().register(this);
        mUrl = getArguments().getString(Constants.IT_NOTIFICATION_URL, "");
        DefaultWebViewSetting.init((AppCompatActivity) mContext, mWebContent, true, false,false);
        mRefreshLayout.setRefreshHeader(new DefaultRefreshHeader(getContext()));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mWebContent.reload();
            loadingDialog=new LoadingDialog(mContext,"玩命加载中");
            loadingDialog.show();
        });
        mWebContent.loadUrl(mUrl);
        if (SPUtils.isLogin()) {
            loadingDialog = new LoadingDialog(mContext, "玩命加载中");
            loadingDialog.show();
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
    @OnClick(R.id.ll_error_page)
    public void onClick(View view) {
        mWebContent.loadUrl(mUrl);
    }

    // webview 开始加载
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_WEBVIEW_PAGE_START)
    })
    public void onPageStart(String url) {
        showLoadStart();
    }
    // webview 加载失败
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_WEBVIEW_PAGE_ERROR)
    })
    public void onPageFail(String url) {
        if (loadingDialog!=null){
            loadingDialog.close();
        }
        showLoadFail();
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

    //登录成功
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_LOGIN_SUCCESS)
    })
    public void loginSuccess(String access_token) {
        //if (getUserVisibleHint()){
            mWebContent.loadUrl(mUrl);
        //}
    }


    //退出成功
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_LOGOUT_SUCCESS)
    })
    public void logoutSuccess(String access_token) {
        mWebContent.reload();
    }


}
