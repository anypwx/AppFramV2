package com.aframe.appframe.tools.net;

import com.aframe.appframe.tools.listener.IDialogProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by pwx on 2017/6/2.
 */

public class MResponeseBody extends ResponseBody {
    private final ResponseBody responseBody;
    private final IDialogProgressListener iDialogProgressListener;
    private BufferedSource bufferedSource;

    public MResponeseBody(ResponseBody responseBody, IDialogProgressListener iDialogProgressListener) {
        this.responseBody = responseBody;
        this.iDialogProgressListener = iDialogProgressListener;
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
        if(null == bufferedSource){
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                iDialogProgressListener.progressInt((int)(totalBytesRead*1.0 / responseBody.contentLength() *100));
                return bytesRead;
            }
        };
    }
}
