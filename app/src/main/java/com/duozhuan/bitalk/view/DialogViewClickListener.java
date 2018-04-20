package com.duozhuan.bitalk.view;

import android.support.v4.app.DialogFragment;

/**
 * 对话框点击事件接口
 * <p>
 * Created by 徐极凯 on 2018/1/13.
 */

public interface DialogViewClickListener {

    /**
     * 点击取消按钮
     */
    void onCancelViewClick(DialogFragment dialog);

    /**
     * 点击确定按钮
     */
    void onConfirmViewClick(DialogFragment dialog);

}
