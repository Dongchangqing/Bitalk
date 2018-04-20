package com.duozhuan.bitalk.widget;


import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.duozhuan.bitalk.R;

import java.util.ArrayList;


/**
 * Created by 95 on 2017/5/20.
 * 公共dialog
 */

public class CommonDialog extends Dialog {
    protected Context mContext;
    private TextView title;
    private TextView message;
    private Button yesButton;
    private Button noButton;
    private ArrayList<CommonDialogInterface> mListeners = new ArrayList<>();

    public CommonDialog(Context context) {
        this(context, R.style.MyDialog);
        mContext = context;
        init(context);
    }

    public CommonDialog(Context context, int defStyle) {
        super(context, defStyle);
        init(context);
    }

    protected void init(Context context) {
        setCancelable(false);
        setContentView(R.layout.common_dialog);
        title = (TextView) findViewById(R.id.dialog_title);
        message = (TextView) findViewById(R.id.dialog_textview);
        yesButton = (Button) findViewById(R.id.dialog_button_ok);
        noButton = (Button) findViewById(R.id.dialog_button_cancel);
        yesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (CommonDialogInterface listener : mListeners) {
                    listener.onOK();
                }
                dismiss();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (CommonDialogInterface listener : mListeners) {
                    listener.onCancel();
                }
                dismiss();
            }
        });
    }

    public interface CommonDialogInterface {
        void onOK();
        void onCancel();
    }

    /**设置标题*/
    public void setTitle(String strTitle) {
        title.setText(strTitle);
        title.setVisibility(View.VISIBLE);
    }

    /**设置内容*/
    public void setMessage(String strMessage) {
        message.setVisibility(View.VISIBLE);
        message.setText(strMessage);
    }

    public void setHtmlMessage(String strMessage){
        message.setVisibility(View.VISIBLE);
        message.setText(Html.fromHtml(strMessage));
    }

    /**点击确定*/
    public void setOkButtonText(String text) {
        yesButton.setText(text);
    }

    /**设置确定字体颜色*/

    public void setOkButtonColor(int color){
        yesButton.setTextColor(color);
    }

    /**点击取消*/
    public void setCancelButtonText(String text) {
        noButton.setText(text);
    }

    public void setCancelButtonColor(int color){
        noButton.setTextColor(color);
    }

    public void setListener(CommonDialogInterface listener) {
        mListeners.add(listener);
    }

    /**取消按钮消失*/
    /*public void setCancelButtonGone(){
        noButton.setVisibility(View.GONE);
        findViewById(R.id.line).setVisibility(View.GONE);
    }*/
}

