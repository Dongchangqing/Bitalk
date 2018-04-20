package com.duozhuan.bitalk.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duozhuan.bitalk.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提示dialog
 */

public class TipsDialog extends DialogFragment {
    private static final String ARGS_TITLE = "title";
    private static final String ARGS_MESSAGE = "message";
    private static final String ARGS_CANCEL = "cancel";
    private static final String ARGS_CONFIRM = "confirm";

    @BindView(R.id.tv_generaldialog_title)
    TextView titleText;
    @BindView(R.id.tv_generaldialog_content)
    TextView contentText;
    @BindView(R.id.tv_generaldialog_cancel)
    TextView cancelText;
    @BindView(R.id.tv_generaldialog_confirm)
    TextView confirmText;

    DialogViewClickListener clickListener;

    public static TipsDialog newInstance(String title, String message,
                                         String cancelString, String confirmString) {
        TipsDialog instance = new TipsDialog();

        Bundle args = new Bundle();
        args.putString(ARGS_TITLE, title);
        args.putString(ARGS_MESSAGE, message);
        args.putString(ARGS_CANCEL, cancelString);
        args.putString(ARGS_CONFIRM, confirmString);
        instance.setArguments(args);

        return instance;
    }

    public static TipsDialog newInstance(String message, String cancelString, String confirmString) {
        return newInstance(null, message, cancelString, confirmString);
    }

    public static TipsDialog newInstance(String message) {
        return newInstance(null, message, "取消", "确定");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_tips, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString(ARGS_TITLE, null);
            if (title != null) {
                titleText.setText(bundle.getString(ARGS_TITLE));
            } else {
                titleText.setVisibility(View.GONE);
            }

            String content = bundle.getString(ARGS_MESSAGE, null);
            if (content != null) {
                contentText.setLineSpacing(16f, 1f);
                contentText.setText(content);
            } else {
                contentText.setVisibility(View.INVISIBLE);
            }

            String cancel = bundle.getString(ARGS_CANCEL, null);
            if (cancel != null) {
                cancelText.setText(cancel);
            } else {
                cancelText.setVisibility(View.GONE);
            }

            String confirm = bundle.getString(ARGS_CONFIRM, null);
            if (confirm != null) {
                confirmText.setText(confirm);
            }

        }
    }

    @OnClick({R.id.tv_generaldialog_cancel, R.id.tv_generaldialog_confirm})
    public void onViewClick(View v) {
        if (clickListener == null) {
            dismiss();
        } else {
            switch (v.getId()) {
                case R.id.tv_generaldialog_cancel:
                    clickListener.onCancelViewClick(TipsDialog.this);
                    break;

                case R.id.tv_generaldialog_confirm:
                    clickListener.onConfirmViewClick(TipsDialog.this);
                    break;
            }
        }
    }

    public TipsDialog setOnDialogViewClickListener(DialogViewClickListener listener) {
        clickListener = listener;
        return this;
    }

}
