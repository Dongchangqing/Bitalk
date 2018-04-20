package com.duozhuan.bitalk;


import android.os.Handler;

import com.duozhuan.bitalk.base.base.BaseActivity;
import com.duozhuan.bitalk.views.browser.WebActivity;

/**
 * 创建日期：2018/4/10 on 16:08
 * 描述:
 * 作者:dcqing duozhuan
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initEventAndData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.actionActivity(SplashActivity.this);
                finish();

            }
        },1000);
    }
}
