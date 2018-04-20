package com.duozhuan.bitalk.ui.mine;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.duozhuan.bitalk.LoginActivity;
import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.base.base.BaseFragment;
import com.duozhuan.bitalk.util.SPUtils;
import com.duozhuan.bitalk.views.browser.CookieUtils;
import com.duozhuan.bitalk.views.browser.WebActivity;
import com.duozhuan.bitalk.widget.CommonDialog;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import butterknife.BindView;
import butterknife.OnClick;

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
        initView();
    }

    @OnClick({R.id.rl_mine_persondata, R.id.rl_mine_draft, R.id.rl_mine_collect, R.id.rl_mine_moneypack, R.id.rl_mine_setting, R.id.rl_mine_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_persondata:
                if (SPUtils.isLogin()) {
                    WebActivity.actionWeb(mContext, Constants.PERSONALEDIT + SPUtils.getString(Constants.LOGIN_USERNAME),"");
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
                dialog.setMessage("确定要退出登录吗？");
                dialog.setListener(new CommonDialog.CommonDialogInterface() {
                    @Override
                    public void onOK() {
                        mWebView.loadUrl(Constants.LOGINOUTURL);
                        CookieUtils.clearCookie();
                        SPUtils.setString(Constants.LOGIN_ACCENTTOKEN,"");
                        RxBus.get().post(Constants.EVENT_LOGOUT_SUCCESS,"");
                        initView();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                dialog.show();
                break;
        }
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

}
