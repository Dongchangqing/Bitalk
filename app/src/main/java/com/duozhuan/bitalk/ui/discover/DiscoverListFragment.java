package com.duozhuan.bitalk.ui.discover;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.base.base.BaseFragment;
import com.duozhuan.bitalk.util.NetWorkUtils;
import com.duozhuan.bitalk.views.browser.DefaultRefreshHeader;
import com.duozhuan.bitalk.views.browser.DefaultWebViewSetting;
import com.duozhuan.bitalk.views.browser.WebActivity;
import com.duozhuan.bitalk.widget.LoadingDialog;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

import static com.duozhuan.bitalk.app.Constants.EVENT_LOGIN_SUCCESS;
import static com.duozhuan.bitalk.app.Constants.EVENT_LOGOUT_SUCCESS;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_NEW_PAGE;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_ERROR;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_FINISH;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PAGE_START;
import static com.duozhuan.bitalk.app.Constants.EVENT_WEBVIEW_PROGRESS_REFRESH;


public class DiscoverListFragment extends BaseFragment {

    @BindView(R.id.fragment_discover_web)
    WebView mWebContent;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.ll_error_page)
    LinearLayout mLlErrorPage;
    //@BindView(R.id.pb_web)
    //ProgressBar mPbWeb;

    String mUrl="";

    //加载对话框loading
    private LoadingDialog loadingDialog;
    private Timer mTimer;
    private String lastUrl="";
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
            loadContent();
        });
        mTimer=new Timer();
        startTimer();
        loadContent();
    }
    public void startTimer(){
        if (mTimer!=null) {
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (mWebContent!=null) {
                        mWebContent.post(new Runnable() {
                            @Override
                            public void run() {
                                String url = mWebContent.getUrl();
                                Log.i("uuu", url);
                                if (!mUrl.equals(url)) {
                                    if (!lastUrl.equals(url)) {
                                        lastUrl = url;
                                        backPreviousPage();
                                        if (!url.contains("steemconnect.com/oauth2/authorize")&&!url.contains("signup.steemit.com"))
                                            WebActivity.actionWeb(mContext, url, "");
                                    }
                                }
                            }
                        });
                    }


                }
            }, 500, 500);
        }
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
        },500);
    }

    @Override
    public void onResume() {
        super.onResume();
        lastUrl="";
    }

    private void loadContent() {
        mWebContent.loadUrl(mUrl);
        loadingDialog = new LoadingDialog(mContext, "玩命加载中");
        loadingDialog.show();
    }


    private void showLoadFail() {
        mWebContent.setVisibility(View.GONE);
        mLlErrorPage.setVisibility(View.VISIBLE);
    }

    private void showLoadStart() {
        mWebContent.setVisibility(View.VISIBLE);
        mLlErrorPage.setVisibility(View.GONE);
    }
    @OnClick(R.id.ll_error_page)
    public void onClick() {
        loadContent();

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
        if (!NetWorkUtils.isNetworkConnected(mContext))
            showLoadFail();
        else
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
        //showLoadFail();
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
        mWebContent.loadUrl(mUrl);
    }


    //退出成功
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_LOGOUT_SUCCESS)
    })
    public void logoutSuccess(String access_token) {
        mWebContent.reload();
    }

}
