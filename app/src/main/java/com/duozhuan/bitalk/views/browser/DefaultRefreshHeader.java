package com.duozhuan.bitalk.views.browser;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.util.TimeUtils;



public class DefaultRefreshHeader extends FrameLayout implements RefreshHeader {


    private Context mContext;

    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;

    @BindView(R.id.tv_refresh_time)
    TextView mTvRefreshTime;


    @BindView(R.id.iv_refresh)
    ImageView mIvRefresh;

    @BindView(R.id.iv_refresh_loading)
    ImageView mIvRefreshLoading;

    private Date mLastTime = new Date();

    public DefaultRefreshHeader(@NonNull Context context) {
        this(context, null);
    }

    public DefaultRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_refresh_header, this);
        ButterKnife.bind(view);
    }


    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @Override
    public void onRefreshReleased(RefreshLayout layout, int headerHeight, int extendHeight) {

    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        if (success) {
            mTvRefresh.setText("刷新完成");
            mLastTime=new Date();
        } else {
            mTvRefresh.setText("刷新失败");
        }
        return 200;//延迟200毫秒之后再弹回
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        mTvRefreshTime.setText(TimeUtils.formatDate(mLastTime));
        switch (newState) {
            case None:
            case PullDownToRefresh:
                mTvRefresh.setText("下拉开始刷新");
                mIvRefreshLoading.clearAnimation();
                mIvRefreshLoading.setVisibility(GONE);//隐藏动画
                mIvRefresh.setVisibility(VISIBLE);//显示下拉箭头
                mIvRefresh.animate().rotation(0);//还原箭头方向
                break;
            case Refreshing:
                mTvRefresh.setText("正在刷新");
                mIvRefreshLoading.setVisibility(VISIBLE);//显示加载动画
                mIvRefresh.clearAnimation();
                mIvRefresh.setVisibility(GONE);//隐藏箭头
                startRotation();
                break;
            case ReleaseToRefresh:
                mTvRefresh.setText("松开立即刷新");
                mIvRefreshLoading.clearAnimation();
                mIvRefreshLoading.setVisibility(GONE);
                mIvRefresh.setVisibility(VISIBLE);
                mIvRefresh.animate().rotation(180);//显示箭头改为朝上
                break;
        }
    }

    private void startRotation() {
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mIvRefreshLoading, "rotation", 0f, 359.9f);
        rotate.setDuration(700);
        rotate.setRepeatCount(-1);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.start();
    }
}
