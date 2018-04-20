package com.duozhuan.bitalk;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.base.base.BaseActivity;
import com.duozhuan.bitalk.event.DiscoverLabelEvent;
import com.duozhuan.bitalk.event.MessageCountEvent;
import com.duozhuan.bitalk.event.UserInfoEvent;
import com.duozhuan.bitalk.ui.attention.AttentionFragment;
import com.duozhuan.bitalk.ui.discover.DiscoverFragment;
import com.duozhuan.bitalk.ui.mine.MineFragment;
import com.duozhuan.bitalk.ui.notification.NotificationFragment;
import com.duozhuan.bitalk.util.CommonUtils;
import com.duozhuan.bitalk.util.SPUtils;
import com.duozhuan.bitalk.view.DialogViewClickListener;
import com.duozhuan.bitalk.view.TipsDialog;
import com.duozhuan.bitalk.views.browser.CookieUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.duozhuan.bitalk.app.Constants.EVENT_DISCOVER_LABEL;
import static com.duozhuan.bitalk.app.Constants.EVENT_HIDDEN_BOTTOM;
import static com.duozhuan.bitalk.app.Constants.EVENT_HIDDEN_BOTTOM_AND_TOP;
import static com.duozhuan.bitalk.app.Constants.EVENT_MESSAGE_COUNT;
import static com.duozhuan.bitalk.app.Constants.EVENT_SAVE_USERINFO;
import static com.duozhuan.bitalk.app.Constants.EVENT_SHOW_BOTTOM;
import static com.duozhuan.bitalk.app.Constants.EVENT_SHOW_BOTTOM_AND_TOP;
import static com.duozhuan.bitalk.app.Constants.EVENT_USER_EXIT;
import static com.duozhuan.bitalk.app.Constants.EVENT_USER_LOGIN;


public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_data_attention)
    TextView mTvDataAttention;

    @BindView(R.id.tv_data_discover)
    TextView mTvDataDiscover;

    @BindView(R.id.tv_data_notification)
    TextView mTvDataNotification;

    @BindView(R.id.tv_data_mine)
    TextView mTvMine;

    @BindView(R.id.container)
    FrameLayout mContainer;

    @BindView(R.id.tv_tab_message_count)
    TextView mMessageCount;

    @BindDrawable(R.mipmap.img_attention_checked)
    Drawable mImgAttentionChecked;

    @BindDrawable(R.mipmap.img_attention_unchecked)
    Drawable mImgAttentionUnChecked;

    @BindDrawable(R.mipmap.img_discover_checked)
    Drawable mImgDiscoverChecked;

    @BindDrawable(R.mipmap.img_discover_unchecked)
    Drawable mImgDiscoverUnChecked;

    @BindDrawable(R.mipmap.img_notification_checked)
    Drawable mImgNotificationChecked;

    @BindDrawable(R.mipmap.img_notification_unchecked)
    Drawable mImgNotificationUnChecked;

    @BindDrawable(R.mipmap.img_mine_checked)
    Drawable mImgMineChecked;

    @BindDrawable(R.mipmap.img_mine_unchecked)
    Drawable mImgMineUnChecked;

    private FragmentManager mSupportFragmentManager;

    private Fragment mShowingFragment;
    private AttentionFragment mDataAttentionFragment;
    private DiscoverFragment mDataDiscoverFragment;
    private NotificationFragment mDataNotificationFragment;
    private MineFragment mMineFragment;

    @BindView(R.id.tab)
    LinearLayout mTabLayout;

    public static int notificationCount = 0;
    public static int replyCount = 0;
    public static int dynamicCount = 0;

    public static boolean isAttention = true;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        RxBus.get().register(this);
        mSupportFragmentManager = getSupportFragmentManager();
        mDataAttentionFragment = AttentionFragment.newInstance();
        mDataDiscoverFragment = DiscoverFragment.newInstance();
        mDataNotificationFragment = NotificationFragment.newInstance();
        mMineFragment = MineFragment.newInstance();
        showFragment(mDataAttentionFragment);

        showMessageCount(notificationCount + replyCount + dynamicCount);
    }


    @OnClick({R.id.tv_data_attention, R.id.tv_data_discover, R.id.tv_data_notification, R.id.tv_data_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_data_attention:

                if (isAttention){
                    RxBus.get().post(Constants.EVENT_ATTENTION_RELODAD,"");
                }
                isAttention=true;
                showFragment(mDataAttentionFragment);
                mTvDataAttention.setTextColor(CommonUtils.getColor(R.color.color_f15779));
                mTvDataDiscover.setTextColor(CommonUtils.getColor(R.color.color_999999));
                mTvDataNotification.setTextColor(CommonUtils.getColor(R.color.color_999999));
                mTvMine.setTextColor(CommonUtils.getColor(R.color.color_999999));

                mTvDataAttention.setCompoundDrawablesWithIntrinsicBounds(null, mImgAttentionChecked, null, null);
                mTvDataDiscover.setCompoundDrawablesWithIntrinsicBounds(null, mImgDiscoverUnChecked, null, null);
                mTvDataNotification.setCompoundDrawablesWithIntrinsicBounds(null, mImgNotificationUnChecked, null, null);
                mTvMine.setCompoundDrawablesWithIntrinsicBounds(null, mImgMineUnChecked, null, null);
                break;
            case R.id.tv_data_discover:
                isAttention=false;
                showFragment(mDataDiscoverFragment);

                mTvDataAttention.setTextColor(CommonUtils.getColor(R.color.color_999999));
                mTvDataDiscover.setTextColor(CommonUtils.getColor(R.color.color_f15779));
                mTvDataNotification.setTextColor(CommonUtils.getColor(R.color.color_999999));
                mTvMine.setTextColor(CommonUtils.getColor(R.color.color_999999));

                mTvDataAttention.setCompoundDrawablesWithIntrinsicBounds(null, mImgAttentionUnChecked, null, null);
                mTvDataDiscover.setCompoundDrawablesWithIntrinsicBounds(null, mImgDiscoverChecked, null, null);
                mTvDataNotification.setCompoundDrawablesWithIntrinsicBounds(null, mImgNotificationUnChecked, null, null);
                mTvMine.setCompoundDrawablesWithIntrinsicBounds(null, mImgMineUnChecked, null, null);
                break;
            case R.id.tv_data_notification:
                isAttention=false;
                showFragment(mDataNotificationFragment);

                mTvDataAttention.setTextColor(CommonUtils.getColor(R.color.color_999999));
                mTvDataDiscover.setTextColor(CommonUtils.getColor(R.color.color_999999));
                mTvDataNotification.setTextColor(CommonUtils.getColor(R.color.color_f15779));
                mTvMine.setTextColor(CommonUtils.getColor(R.color.color_999999));

                mTvDataAttention.setCompoundDrawablesWithIntrinsicBounds(null, mImgAttentionUnChecked, null, null);
                mTvDataDiscover.setCompoundDrawablesWithIntrinsicBounds(null, mImgDiscoverUnChecked, null, null);
                mTvDataNotification.setCompoundDrawablesWithIntrinsicBounds(null, mImgNotificationChecked, null, null);
                mTvMine.setCompoundDrawablesWithIntrinsicBounds(null, mImgMineUnChecked, null, null);
                break;
            case R.id.tv_data_mine:
                isAttention=false;
                showFragment(mMineFragment);

                mTvDataAttention.setTextColor(CommonUtils.getColor(R.color.color_999999));
                mTvDataDiscover.setTextColor(CommonUtils.getColor(R.color.color_999999));
                mTvDataNotification.setTextColor(CommonUtils.getColor(R.color.color_999999));
                mTvMine.setTextColor(CommonUtils.getColor(R.color.color_f15779));

                mTvDataAttention.setCompoundDrawablesWithIntrinsicBounds(null, mImgAttentionUnChecked, null, null);
                mTvDataDiscover.setCompoundDrawablesWithIntrinsicBounds(null, mImgDiscoverUnChecked, null, null);
                mTvDataNotification.setCompoundDrawablesWithIntrinsicBounds(null, mImgNotificationUnChecked, null, null);
                mTvMine.setCompoundDrawablesWithIntrinsicBounds(null, mImgMineChecked, null, null);
                break;
        }
    }

    private void showFragment(Fragment showFragment) {
        if (showFragment != mShowingFragment) {
            if (showFragment.isAdded()) { // 被添加了
                if (mShowingFragment != null) {
                    mSupportFragmentManager.beginTransaction()
                            .hide(mShowingFragment)
                            .show(showFragment)
                            .commit();
                } else {
                    mSupportFragmentManager.beginTransaction()
                            .show(showFragment)
                            .commit();
                }

            } else {
                if (mShowingFragment != null) {
                    mSupportFragmentManager.beginTransaction()
                            .hide(mShowingFragment)
                            .add(R.id.container, showFragment)
                            .commit();
                } else {
                    mSupportFragmentManager.beginTransaction()
                            .add(R.id.container, showFragment)
                            .commit();
                }
            }
        }
        mShowingFragment = showFragment;
    }


    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public void showMessageCount(int count) {
        if (count > 0) {

            mMessageCount.setVisibility(View.VISIBLE);
            if (count > 99) {
                mMessageCount.setText("99+");
                mMessageCount.setTextSize(8.0f);
            } else if (count > 10) {
                mMessageCount.setText(count + "");
                mMessageCount.setTextSize(8.0f);
            } else {
                mMessageCount.setText(count + "");
                mMessageCount.setTextSize(10.0f);
            }

        } else {
            mMessageCount.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Override
    public void onBackPressed() {
        TipsDialog.newInstance("退出提示", "您确定要退出吗?", "取消", "确定")
                .setOnDialogViewClickListener(new DialogViewClickListener() {
                    @Override
                    public void onCancelViewClick(DialogFragment dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onConfirmViewClick(DialogFragment dialog) {
                        dialog.dismiss();
                        moveTaskToBack(true);
                        finish();
                        //overridePendingTransition(R.anim.right_to_current,R.anim.current_stay_scale);
                    }
                })
                .show(getSupportFragmentManager(), MainActivity.class.getSimpleName());
    }

    //发现页面tab切换
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_DISCOVER_LABEL)
    })
    public void onChangDiscover(DiscoverLabelEvent event) {
        onClick(mTvDataDiscover);
    }

    //显示底部
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_SHOW_BOTTOM)
    })
    public void showBottom(String str) {
        mTabLayout.setVisibility(View.VISIBLE);
    }

    //隐藏底部
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_HIDDEN_BOTTOM)
    })
    public void hiddenBottom(String str) {
        mTabLayout.setVisibility(View.GONE);
    }

    //显示底部
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_SHOW_BOTTOM_AND_TOP)
    })
    public void showBottomAndTop(String str) {
        mTabLayout.setVisibility(View.VISIBLE);
    }

    //隐藏底部
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_HIDDEN_BOTTOM_AND_TOP)
    })
    public void hiddenBottomAndTop(String str) {
        mTabLayout.setVisibility(View.GONE);
    }

    //保存用户信息
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_SAVE_USERINFO)
    })
    public void saveUserInfo(UserInfoEvent event) {

        if (!TextUtils.isEmpty(event.getToken())) {
            SPUtils.setString(Constants.LOGIN_ACCENTTOKEN, event.getToken());
        }
        if (!TextUtils.isEmpty(event.getName()))
            SPUtils.setString(Constants.LOGIN_USERNAME, event.getName());

    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_USER_EXIT)
    })
    public void userExit(UserInfoEvent event) {
        SPUtils.setString(Constants.LOGIN_ACCENTTOKEN, event.getToken());
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_USER_LOGIN)
    })
    public void userLogin(UserInfoEvent event) {
        SPUtils.setString(Constants.LOGIN_ACCENTTOKEN, event.getToken());
    }

    //消息数量
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_MESSAGE_COUNT)
    })
    public void messageCount(MessageCountEvent messageCountEvent) {
        showMessageCount(messageCountEvent.getNotificationCount() + messageCountEvent.getReplyCount() + messageCountEvent.getDynamicCount());
    }

}
