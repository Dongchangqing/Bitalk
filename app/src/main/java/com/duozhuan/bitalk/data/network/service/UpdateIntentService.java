package com.duozhuan.bitalk.data.network.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.data.network.okhttputils.FileResponseBody;
import io.reactivex.disposables.CompositeDisposable;


public class UpdateIntentService extends IntentService{
    public static final String ACTION_DOWNLOAD = "intentservice.action.download";
    public static final String DOWNLOAD_URL = "downloadUrl";
    public static final String APK_PATH = "apkPath";

    private CompositeDisposable cd = new CompositeDisposable();
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public UpdateIntentService() {
        super("UpdateIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                builder = new NotificationCompat.Builder(this, "Download")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setAutoCancel(true)
                        .setContentText("正在下载新版本");

                notificationManager.notify(0, builder.build());

                String url = intent.getStringExtra(DOWNLOAD_URL);
                String apkPath = intent.getStringExtra(APK_PATH);

                DownloadService.download(this, url, apkPath, cd, true, onDownloadSizeChangedLisenter);
            }
        }
    }

    FileResponseBody.OnDownloadSizeChangedListener onDownloadSizeChangedLisenter = new FileResponseBody.OnDownloadSizeChangedListener() {
        @Override
        public void downloadedSize(int progress) {
            builder.setProgress(100, progress, false);
            notificationManager.notify(0, builder.build());

            if (progress == 100) {
                notificationManager.cancelAll();
            }
        }
    };
}
