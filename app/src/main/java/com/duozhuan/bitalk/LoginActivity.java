package com.duozhuan.bitalk;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.base.base.BaseActivity;
import com.duozhuan.bitalk.views.browser.WebActivity;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity {



    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @OnClick({R.id.iv_back,R.id.btn_login,R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_login:
                WebActivity.actionWeb(this,Constants.LOGINURL,"登录");
                finish();
                break;
            case R.id.btn_register:
                WebActivity.actionWeb(this, Constants.REGISTERURL,"注册");
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
