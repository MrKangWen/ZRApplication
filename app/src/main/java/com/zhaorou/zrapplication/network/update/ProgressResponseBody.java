package com.zhaorou.zrapplication.network.update;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 *
 * 下载进度条
 */

public class ProgressResponseBody extends ResponseBody {
    private final String TAG = ProgressResponseBody.class.getSimpleName();

    //
    private ResponseBody responseBody;
    private ProgressListener listener;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
        this.responseBody = responseBody;
        this.listener = listener;

    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {

        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }

        return bufferedSource;
    }

    private Source source(Source source) {

        return  new ForwardingSource(source) {
            long total = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {

                long bytesRead = super.read(sink, byteCount);
                total += bytesRead != -1 ? bytesRead : 0;
                // total 已经下载或上传字节数
                if (listener != null) {
                    // log.logE(TAG, "source ::: read = "+bytesRead);
                    listener.onProgress(total, responseBody.contentLength(), bytesRead == -1);
                }

                return bytesRead;
            }
        };

    }

    public interface ProgressListener {
        /**
         * @param progress 已经下载或上传字节数
         * @param total    总字节数
         * @param done     是否完成
         */
        void onProgress(long progress, long total, boolean done);
    }
}
