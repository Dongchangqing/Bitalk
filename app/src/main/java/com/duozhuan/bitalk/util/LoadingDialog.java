package com.duozhuan.bitalk.util;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duozhuan.bitalk.R;


/**
 * Created by wangliang on 2018/1/28.
 * 公用的 加载对话框
 */

public class LoadingDialog extends DialogFragment {

    private LoadingCancelListener mLoadingCancelListener;

    public LoadingDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, container, false);
        return view;
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mLoadingCancelListener != null) {
            mLoadingCancelListener.onLoadingCancel();
        }
    }

    public interface LoadingCancelListener {
        void onLoadingCancel();
    }


    public static LoadingDialog newInstance() {
        LoadingDialog loadingDialog = new LoadingDialog();
        return loadingDialog;
    }

    public LoadingDialog setLoadingCancelListener(LoadingCancelListener loadingCancelListener) {
        mLoadingCancelListener = loadingCancelListener;
        return this;
    }
}
