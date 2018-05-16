package com.duozhuan.bitalk.ui.discover;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.base.base.BaseFragment;
import com.duozhuan.bitalk.event.DiscoverLabelEvent;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.duozhuan.bitalk.app.Constants.EVENT_DISCOVER_LABEL;
import static com.duozhuan.bitalk.app.Constants.EVENT_HIDDEN_BOTTOM_AND_TOP;
import static com.duozhuan.bitalk.app.Constants.EVENT_HIDDEN_TOP;
import static com.duozhuan.bitalk.app.Constants.EVENT_SHOW_BOTTOM_AND_TOP;
import static com.duozhuan.bitalk.app.Constants.EVENT_SHOW_TOP;


public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.tab_discover)
    TabLayout mTabLayout;
    @BindView(R.id.vp_discover)
    ViewPager mViewPager;

    public static String[] typeStr = {"阅读", "问答", "美食", "旅游","财经","全部"};
    public static String[] type = {"read", "ask", "food", "travel","money","all"};
    public static String[] typeUrl = {Constants.READ, Constants.ASK, Constants.FOOD, Constants.TRAVEL,Constants.MONEY,Constants.ALL};

    List<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> mTitleList;

    private DiscoverAdapter mAdapter;
    @Override
    protected int getLayout() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initEventAndData() {
        RxBus.get().register(this);
        for (int i = 0; i < type.length; i++) {
            DiscoverListFragment fragment = new DiscoverListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.IT_DISCOVER_URL, typeUrl[i]);
            mTabLayout.addTab(mTabLayout.newTab().setText(typeStr[i]));
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        mAdapter = new DiscoverAdapter(getChildFragmentManager(), fragments);
        //标题列表添加
        mTitleList = new ArrayList<>();
        for (int i=0;i<typeStr.length;i++){
            mTitleList.add(typeStr[i]);
        }
        mAdapter.addTitles(mTitleList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(5);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }


    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(EVENT_DISCOVER_LABEL)
    })
    public void onCurrentItem(DiscoverLabelEvent event) {
        String label=event.getLabel();
        //cn-reader cn-ask food travel cn-money

        //根据label进行判断跳转
        //mViewPager.setCurrentItem(integer);
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
}
