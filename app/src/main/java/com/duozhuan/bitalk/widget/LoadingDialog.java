package com.duozhuan.bitalk.widget;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duozhuan.bitalk.R;
import com.thinkcool.circletextimageview.CircleTextImageView;


/**
 * 创建日期：2018/3/22 on 15:19
 * 描述:
 * 作者:dcqing duozhuan
 */
public class LoadingDialog {
    //LoadCircleAnim mLoadingView;
    Dialog mLoadingDialog;
    CircleTextImageView imageView;
    Context mContext;

    public LoadingDialog(Context context, String msg) {
        mContext=context;
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.loading_dialog_view, null);
        // 获取整个布局
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
        // 页面中的LoadingView
        //mLoadingView = (LoadCircleAnim) view.findViewById(R.id.lv_circularring);
        imageView=(CircleTextImageView)view.findViewById(R.id.loading_img);
       /* // 页面中显示文本
        TextView loadingText = (TextView) view.findViewById(R.id.loading_text);
        // 显示文本
        loadingText.setText(msg);
        // 隐藏文本
        loadingText.setVisibility(View.GONE);*/
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        mLoadingDialog.setCancelable(true);

        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void show(){
        mLoadingDialog.show();
        startAnim();
    }

    private void startAnim() {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.rotate_anim);
//        Animation anim =new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        anim.setFillAfter(true); // 设置保持动画最后的状态
//        anim.setDuration(300000); // 设置动画时间
        anim.setInterpolator(new LinearInterpolator()); // 设置插入器
        imageView.startAnimation(anim);
    }

    public void close(){
        if (mLoadingDialog!=null) {
            mLoadingDialog.dismiss();
            mLoadingDialog=null;
        }
    }

}
