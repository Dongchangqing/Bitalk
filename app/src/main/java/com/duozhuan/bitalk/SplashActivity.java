package com.duozhuan.bitalk;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.duozhuan.bitalk.base.base.BaseActivity;
import com.duozhuan.bitalk.views.browser.WebActivity;

/**
 * 创建日期：2018/4/10 on 16:08
 * 描述:
 * 作者:dcqing duozhuan
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        startIntent();

    }

    private void startIntent() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.actionActivity(SplashActivity.this);
                finish();

            }
        },1000);

    }

//    @Override
//    protected int getLayout() {
//        return R.layout.activity_welcome;
//    }
//
//    @Override
//    protected void initEventAndData() {
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                MainActivity.actionActivity(SplashActivity.this);
//                finish();
//
//            }
//        },1000);
//    }


}
