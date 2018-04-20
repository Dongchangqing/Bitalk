package com.duozhuan.bitalk.data.network.okhttputils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;



public class FileRequestBody extends RequestBody {
    private final RequestBody requestBody;
    private final OnUploadSizeChangedListener progressListener;
    private BufferedSink bufferedSink;

    public FileRequestBody(RequestBody requestBody, OnUploadSizeChangedListener progressListener) {
        this.requestBody = requestBody;
        this.progressListener = progressListener;
    }


    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(sink(sink));
        }
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            long bytesWrited = 0;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                bytesWrited += byteCount;

                progressListener.uploadedSize(bytesWrited);
            }
        };
    }

    public interface OnUploadSizeChangedListener {
        void uploadedSize(long progress);
    }
}
