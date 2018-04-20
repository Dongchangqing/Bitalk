package com.duozhuan.bitalk.data.network.okhttputils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.duozhuan.bitalk.R;




public class NetworkToast {

    public static Toast netWorkError(Context context){
        return customToast(context, "您与服务器的连接出现问题", R.drawable.ic_error_network, Color.WHITE, 500, true);
    }

    public static Toast otherError(Context context, CharSequence message){
        return customToast(context, message, R.drawable.ic_error_outline, Color.WHITE, 500, true);
    }

    public static Toast customToast(Context context, CharSequence message, int iconRes, int textColor, int duration, boolean displayIcon){
        final Toast currentToast = new Toast(context);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.view_networktoast, null);
        final ImageView toastIcon =  toastLayout.findViewById(R.id.iv_networktoast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.tv_networktoast_text);

        toastLayout.setBackground(context.getResources().getDrawable(R.drawable.toast_frame));

        if (displayIcon) {
           toastIcon.setBackground(context.getResources().getDrawable(iconRes));
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setTextColor(textColor);
        toastTextView.setText(message);
        toastTextView.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));

        currentToast.setView(toastLayout);
        currentToast.setDuration(duration);
        return currentToast;
    }
}
