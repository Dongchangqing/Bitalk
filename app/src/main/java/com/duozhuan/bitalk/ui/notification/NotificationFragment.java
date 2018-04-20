package com.duozhuan.bitalk.ui.notification;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duozhuan.bitalk.MainActivity;
import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.base.base.BaseFragment;
import com.duozhuan.bitalk.event.MessageCountEvent;
import com.duozhuan.bitalk.util.SPUtils;
import com.duozhuan.bitalk.views.browser.WebActivity;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.duozhuan.bitalk.app.Constants.EVENT_HIDDEN_BOTTOM;
import static com.duozhuan.bitalk.app.Constants.EVENT_HIDDEN_BOTTOM_AND_TOP;
import static com.duozhuan.bitalk.app.Constants.EVENT_HIDDEN_TOP;
import static com.duozhuan.bitalk.app.Constants.EVENT_LOGIN_SUCCESS;
import static com.duozhuan.bitalk.app.Constants.EVENT_LOGOUT_SUCCESS;
import static com.duozhuan.bitalk.app.Constants.EVENT_MESSAGE_COUNT;
import static com.duozhuan.bitalk.app.Constants.EVENT_SHOW_BOTTOM_AND_TOP;
import static com.duozhuan.bitalk.app.Constants.EVENT_SHOW_TOP;

public class NotificationFragment extends BaseFragment  {

    @BindView(R.id.tab_notification)
    TabLayout mTabLayout;
    @BindView(R.id.vp_notification)
    ViewPager mViewPager;
    @BindView(R.id.rl_login)
    RelativeLayout mRllogin;
    @BindView(R.id.ll_tablayout)
    LinearLayout mTablayout;

    public static String[] typeStr = {"通知", "回复", "动态"};
    public static String[] type = {"notification", "reply", "dynamic"};
    public static String[] typeUrl = {Constants.NOTIFICATION, Constants.REPLY, Constants.DYNAMIC};

    List<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> mTitleList;

    private NotificationAdapter mAdapter;

    TextView mNotifination;
    TextView mReply;
    TextView mDynamic;

    @Override
    protected int getLayout() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void initEventAndData() {
        RxBus.get().register(this);
        initView();
    }

    @OnClick({R.id.btn_login,R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                WebActivity.actionWeb(mContext,Constants.LOGINURL,"登录");
                break;
            case R.id.btn_register:
                WebActivity.actionWeb(mContext, Constants.REGISTERURL,"注册");
                break;
        }
    }


    private void initView() {
        if (SPUtils.isLogin()){
            mRllogin.setVisibility(View.GONE);
            mTablayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < type.length; i++) {
                NotificationListFragment fragment = new NotificationListFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.IT_NOTIFICATION_URL, typeUrl[i]);
                //mTabLayout.addTab(mTabLayout.newTab().setText(typeStr[i]));
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
            mAdapter = new NotificationAdapter(getChildFragmentManager(), fragments);
            //标题列表添加
            mTitleList = new ArrayList<>();
            for (int i=0;i<typeStr.length;i++){
                mTitleList.add(typeStr[i]);
            }
            mAdapter.addTitles(mTitleList);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setOffscreenPageLimit(2);

            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
            mTabLayout.setupWithViewPager(mViewPager);

            mTabLayout.getTabAt(0).setCustomView(R.layout.item_tabitem);
            mNotifination=(TextView)mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_text);
            mNotifination.setText(typeStr[0]);
            mNotifination.setTextColor(Color.parseColor("#f15779"));
            mTabLayout.getTabAt(1).setCustomView(R.layout.item_tabitem);
            mReply=(TextView)mTabLayout.getTabAt(1).getCustomView().findViewById(R.id.tab_text);
            mReply.setText(typeStr[1]);
            mTabLayout.getTabAt(2).setCustomView(R.layout.item_tabitem);
            mDynamic=(TextView)mTabLayout.getTabAt(2).getCustomView().findViewById(R.id.tab_text);
            mDynamic.setText(typeStr[2]);
            if (MainActivity.notificationCount > 0){
                TextView textView = ((TextView)mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tv_tab_message_count));
                if (MainActivity.notificationCount > 99){
                    textView.setText("99+");
                    textView.setTextSize(8.0f);
                }
                else if (MainActivity.notificationCount > 10){
                    textView.setTextSize(8.0f);
                    textView.setText(MainActivity.notificationCount + "");
                }
                else {
                    textView.setText(MainActivity.notificationCount + "");
                    textView.setTextSize(10.0f);
                }
                textView.setVisibility(View.VISIBLE);

            }

            if (MainActivity.replyCount > 0){
                TextView textView = ((TextView)mTabLayout.getTabAt(1).getCustomView().findViewById(R.id.tv_tab_message_count));

                if (MainActivity.replyCount > 99){
                    textView.setText("99+");
                    textView.setTextSize(8.0f);
                }
                else if (MainActivity.replyCount > 10){
                    textView.setTextSize(8.0f);
                    textView.setText(MainActivity.replyCount + "");
                }
                else {
                    textView.setText(MainActivity.replyCount + "");
                    textView.setTextSize(10.0f);
                }
                textView.setVisibility(View.VISIBLE);
            }
             if (MainActivity.dynamicCount > 0){
                TextView textView = ((TextView)mTabLayout.getTabAt(2).getCustomView().findViewById(R.id.tv_tab_message_count));

                if (MainActivity.dynamicCount > 99){
                    textView.setText("99+");
                    textView.setTextSize(8.0f);
                }
                else if (MainActivity.dynamicCount > 10){
                    textView.setTextSize(8.0f);
                    textView.setText(MainActivity.dynamicCount + "");
                }
                else {
                    textView.setText(MainActivity.dynamicCount + "");
                    textView.setTextSize(10.0f);
                }
                textView.setVisibility(View.VISIBLE);
            }

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position==0){
                        mNotifination.setTextColor(Color.parseColor("#f15779"));
                        mReply.setTextColor(Color.parseColor("#666666"));
                        mDynamic.setTextColor(Color.parseColor("#666666"));
                    }else  if (position==1){
                        mNotifination.setTextColor(Color.parseColor("#666666"));
                        mReply.setTextColor(Color.parseColor("#f15779"));
                        mDynamic.setTextColor(Color.parseColor("#666666"));
                    }else if(position==2){
                        mNotifination.setTextColor(Color.parseColor("#666666"));
                        mReply.setTextColor(Color.parseColor("#666666"));
                        mDynamic.setTextColor(Color.parseColor("#f15779"));
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }else{
            mRllogin.setVisibility(View.VISIBLE);
            mTablayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    //登录成功
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_LOGIN_SUCCESS)
    })
    public void loginSuccess(String access_token) {
        initView();
    }



    //退出成功
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_LOGOUT_SUCCESS)
    })
    public void logoutSuccess(String access_token) {
        initView();
    }

    //显示顶部
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_SHOW_TOP)
    })
    public void showTop(String str) {
        mTabLayout.setVisibility(View.VISIBLE);
    }

    //隐藏顶部
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_HIDDEN_TOP)
    })
    public void hiddenTop(String str) {
        mTabLayout.setVisibility(View.GONE);
    }

    //显示顶部
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_SHOW_BOTTOM_AND_TOP)
    })
    public void showBottomAndTop(String str) {
        mTabLayout.setVisibility(View.VISIBLE);
    }

    //隐藏顶部
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_HIDDEN_BOTTOM_AND_TOP)
    })
    public void hiddenBottomAndTop(String str) {
        mTabLayout.setVisibility(View.GONE);
    }

    //消息数量
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_MESSAGE_COUNT)
    })
    public void messageCount(MessageCountEvent messageCountEvent) {

    }

}
