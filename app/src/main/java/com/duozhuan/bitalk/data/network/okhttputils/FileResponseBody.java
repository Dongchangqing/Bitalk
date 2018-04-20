package com.duozhuan.bitalk.data.network.okhttputils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;



public class FileResponseBody extends ResponseBody {
    private ResponseBody responseBody;
    private OnDownloadSizeChangedListener listener;

    public FileResponseBody(ResponseBody responseBody, OnDownloadSizeChangedListener listener) {
        this.responseBody = responseBody;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {// 返回文件的总长度，也就是进度条的max
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(responseBody.source()) {
            long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;

                int progress = (int) Math.round(bytesReaded / (double)contentLength() * 100);

                listener.downloadedSize(progress);

                return bytesRead;
            }
        });
    }

    public interface OnDownloadSizeChangedListener {
        void downloadedSize(int progress);
    }
}
