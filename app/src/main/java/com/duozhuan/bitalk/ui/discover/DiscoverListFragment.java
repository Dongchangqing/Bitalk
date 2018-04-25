package com.duozhuan.bitalk.ui.discover;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.base.base.BaseFragment;
import com.duozhuan.bitalk.views.browser.DefaultRefreshHeader;
import com.duozhuan.bitalk.views.browser.DefaultWebViewSetting;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

import static com.duozhuan.bitalk.app.Constants.EVENT_LOGIN_SUCCESS;
import static com.duozhuan.bitalk.app.Constants.EVENT_LOGOUT_SUCCESS;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_FINISH;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_START;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PROGRESS_REFRESH;


public class DiscoverListFragment extends BaseFragment {

    @BindView(R.id.fragment_discover_web)
    WebView mWebContent;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    //@BindView(R.id.pb_web)
    //ProgressBar mPbWeb;

    String mUrl="";
    @Override
    protected int getLayout() {
        return R.layout.fragment_discoverlist;
    }

    @Override
    protected void initEventAndData() {

        RxBus.get().register(this);
        mUrl = getArguments().getString(Constants.IT_DISCOVER_URL,"");
        DefaultWebViewSetting.init((AppCompatActivity) mContext, mWebContent, true, false,false);
        mRefreshLayout.setRefreshHeader(new DefaultRefreshHeader(getContext()));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mWebContent.reload();
        });
        mWebContent.loadUrl(mUrl);
    }

    private void showLoadStart() {
        mWebContent.setVisibility(View.VISIBLE);
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

    // webview 加载进度
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_WEBVIEW_PROGRESS_REFRESH)
    })
    public void onProgressRefresh(Integer progress) {
         if (getUserVisibleHint()) {
//             if (progress < 100) {
//                 mPbWeb.setVisibility(View.INVISIBLE);
//                 mPbWeb.setProgress(progress);
//             } else {
//                 mPbWeb.setVisibility(View.GONE);
//             }
         }
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
        mRefreshLayout.finishRefresh();
    }


    //登录成功
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_LOGIN_SUCCESS)
    })
    public void loginSuccess(String access_token) {
        mWebContent.reload();
    }


    //退出成功
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_LOGOUT_SUCCESS)
    })
    public void logoutSuccess(String access_token) {
        mWebContent.reload();
    }

}
