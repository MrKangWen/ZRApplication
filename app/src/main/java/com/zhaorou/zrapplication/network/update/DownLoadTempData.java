package com.zhaorou.zrapplication.network.update;

import java.io.Serializable;

/**
 * app 更新 临时数据
 */

public class DownLoadTempData implements Serializable {
    private String url;//下载的url
    private long contentLength;//下载文件的总 大小
    // long downCount;
    private boolean isDownOk;//下载是否完成，目前这个值还没用上
    private String md5key;//后台返回下载文件的MD5。

    public DownLoadTempData(String url, long contentLength, String md5key, boolean isDownOk) {
        this.url = url;
        this.contentLength = contentLength;

        this.isDownOk = isDownOk;
        this.md5key = md5key;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public boolean isDownOk() {
        return isDownOk;
    }

    public void setDownOk(boolean downOk) {
        isDownOk = downOk;
    }

    public String getMd5key() {
        return md5key;
    }

    public void setMd5key(String md5key) {
        this.md5key = md5key;
    }


}
