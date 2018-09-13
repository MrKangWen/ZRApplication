package com.zhaorou.zrapplication.network.update;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Log;


import com.zhaorou.zrapplication.utils.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;


/**
 * 断点续传 目前只用于下载更新apk
 * 用于其他用途要改造一下
 */

public class RecoveryDownloadPresenter {

    private static final int BUFFER_SIZE = 1024 * 8;
    private static RecoveryDownloadPresenter mRecoveryDownloadPresenter;
    private final String TAG = RecoveryDownloadPresenter.class.getSimpleName();
    private ProgressResponseBody.ProgressListener mProgressListener;  //下载进度监听
    private long mContentLength;//文件大小
    private Request mRequest;
    private long mDownMark;//上次文件下载的位置
    private okhttp3.Call mCall;
    private boolean isCancelDownload = false; //是否取消下载
    private String mFileMd5 = "";
    private IRecoveryDownload iBase;
    private boolean mIsDownloading = false;//是否在下载中...
    private boolean mTipsInstall = true;


    private RecoveryDownloadPresenter(IRecoveryDownload iBase) {
        this.iBase = iBase;
    }

    public static RecoveryDownloadPresenter getRecoveryDownload(IRecoveryDownload iBase) {
        if (mRecoveryDownloadPresenter == null) {
            synchronized (RecoveryDownloadPresenter.class) {
                if (mRecoveryDownloadPresenter == null) {
                    mRecoveryDownloadPresenter = new RecoveryDownloadPresenter(iBase);
                }
            }
        }
        return mRecoveryDownloadPresenter;
    }

    private static Request getRequestBuiler(String url, long downloadLength) {

        return new Request.Builder()
                .addHeader("RANGE", "bytes=" + downloadLength + "-")//downloadLength 下载范围
                .url(url)
                .build();
    }

    private static Request getRequestBuiler(String url) {
        return new Request.Builder()
                .url(url)
                .build();
    }

    /**
     * 是否提示安装
     *
     * @return
     */
    public boolean isTipsInstall() {
        return mTipsInstall;
    }

    public void setTipsInstall(boolean mTipsInstall) {
        this.mTipsInstall = mTipsInstall;
    }

    public void downloadFile(final Context context, final String fileMd5, final String url, final String path) {

        downloadFile(context, fileMd5, url, path, true);
    }

    /**
     * @param url                   文件URl
     * @param path                  文件路径
     * @param isNeedWifiDownloading 是否在wifi环境才下载...
     */
    public void downloadFile(final Context context, final String fileMd5, final String url, final String path, boolean isNeedWifiDownloading) {

        if (mIsDownloading) {
            d("-----------任务已在下载中ing-------");
            return;
        }
        d("--url----" + url);
        d("-----------========任务在下载中=======-------");
        this.mFileMd5 = fileMd5;
        mIsDownloading = true;
        isCancelDownload = false;

        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            iBase.downloadApk(false, "地址或者SD卡路径不存在", path);
            mIsDownloading = false;
            return;
        }
        final String name = FileUtils.getMD5(url) + ".apk";
        boolean check = checkData(context, url, path);
        if (check) {
            mIsDownloading = false;
            return;
        }

        //是否指定在wifi才下载
        if (isNeedWifiDownloading && !isWifi(context)) {
            d("-----------不再wifi环境下-------");
            mIsDownloading = false;
            return;
        }

        OkHttpClient okHttpClient = getOkHttpClient(context, url);

        mCall = okHttpClient.newCall(mRequest);
        if (mCall == null) {
            mIsDownloading = false;
            iBase.downloadApk(false, "call is null", path + "/" + name);
            return;
        }
        mCall.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                mIsDownloading = false;
                iBase.downloadApk(false, e.getMessage(), path);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                boolean isok = download(response.body(), path, name, mDownMark);
                mIsDownloading = false;
                if (isok) {
                    iBase.downloadApk(true, "下载成功", path + "/" + name);
                } else {

                    iBase.downloadApk(false, "写入文件失败", path + "/" + name);


                }

            }
        });

    }

    private boolean checkData(Context context, String url, String path) {
        String name = "";
        try {

            name = FileUtils.getMD5(url) + ".apk";
            d("------------url name-----------" + name);
            DownLoadTempData data = (DownLoadTempData) SerializableUtils.readObject(context, DownLoadTempData.class.getName());

            File file = new File(path + "/" + name);

            if (data != null && file.exists()) {

                long length = file.length();
                d("-文件存在,大小：" + data.getContentLength());
                d("l文件存在,大小：" + length);
                String md5 = FileUtils.getFileMD5ToString(file);
                d("------------文件存在md5-----------" + md5);
                mContentLength = data.getContentLength();

                if (length == mContentLength) {
                    //判断大小是否一致
                    if (!mFileMd5.equals(md5)) {//容错，判断MD5是否一致
                        d("md5不一致，删除缓存");

                        delete(context, file);
                        mDownMark = 0;
                        mRequest = getRequestBuiler(url);
                    } else {
                        iBase.downloadApk(true, "md5一致，从缓存中取出", path + "/" + name);
                        return true;
                    }
                } else if (mContentLength == 0 || mContentLength < 0 || length < mContentLength) {
                    d("-----文件开始断点下载----length:" + length + "---mContentLength:" + mContentLength);
                    if (length > -1) {// 大于-1
                        mDownMark = length;
                    } else {
                        mDownMark = 0;
                    }
                    mRequest = getRequestBuiler(data.getUrl(), mDownMark);
                } else if (length > mContentLength) {//容错策略，判断下载到本地的文件是否大于原文件

                    d("文件大小大于源大小");
                    delete(context, file);
                    mDownMark = 0;
                    mRequest = getRequestBuiler(data.getUrl(), mDownMark);
                    // iBase.downloadFile(false, "文件大小大于源大小", path + "/" + name);
                }


            } else {
                if (data != null) {
                    SerializableUtils.deleteObject(context, DownLoadTempData.class.getName());
                }
                mDownMark = 0;
                mContentLength = 0;
                mRequest = getRequestBuiler(url);
                d("缓存文件不存在");
            }
        } catch (Exception e) {
            iBase.downloadApk(false, e.getLocalizedMessage(), path + "/" + name);
            // e.printStackTrace();
            return true;
        }

        return false;
    }

    private OkHttpClient getOkHttpClient(final Context context, final String url) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Response orginalResponse = chain.proceed(chain.request());

                        long contentLength = orginalResponse.body().contentLength();
                        if (contentLength == 0 || contentLength < 0) {

                            d("---下载的文件长度不对---");
                        }
                        d("--orginalResponse.body().contentLength--:" + contentLength);

                        if (mContentLength == 0) {
                            mContentLength = contentLength;
                        }

                        DownLoadTempData data = new DownLoadTempData(url, mContentLength, mFileMd5, false);
                        SerializableUtils.saveObject(context, data);

                        return orginalResponse.newBuilder()
                                .body(new ProgressResponseBody(orginalResponse.body(), new ProgressResponseBody.ProgressListener() {
                                    @Override
                                    public void onProgress(long progress, long total, boolean done) {
                                        //  d("progressListener", "total：" + total + "--progress:" + progress);
                                        //int result = (int) (progress * 100 / total);
                                        if (mProgressListener != null) {
                                            mProgressListener.onProgress(progress, total, done);
                                        }
                                    }
                                }))
                                .build();
                    }
                })
                .build();
    }

    /**
     * 取消下载
     */
    public void cancelDownLoad() {
        if (mCall != null) {
            isCancelDownload = true;
            mIsDownloading = false;
            d("--取消下载---");
            mCall.cancel();
        }
    }

    /**
     * 使用断点下载
     *
     * @param body
     * @param path
     * @param name
     * @param downMark
     * @return
     */
    private boolean download(ResponseBody body, String path, String name, long downMark) {
        File f = new File(path);
        //创建文件夹
        if (!f.isDirectory()) {
            boolean mkdirs = f.mkdirs();
            if (!mkdirs) {
                return false;
            }
        }
        InputStream input = null;
        BufferedInputStream in = null;
        RandomAccessFile accessFile = null;
        try {
            File file = new File(path, name);

            if (file == null) {
                return false;
            }

            input = body.byteStream();
            accessFile = new RandomAccessFile(file, "rw");
            accessFile.seek(downMark);
            if (input == null || accessFile == null) return false;
            byte[] bytes = new byte[BUFFER_SIZE];
            in = new BufferedInputStream(input, BUFFER_SIZE);
            int len;
            d("------------使用断点下载 download-----------" + downMark);
            while (((len = in.read(bytes, 0, BUFFER_SIZE)) != -1)) {
                if (isCancelDownload) {
                    d("------------暂停download-----------");
                    break;
                }
                accessFile.write(bytes, 0, len);

            }

            String md5 = FileUtils.getFileMD5ToString(new File(path, name));
            d("------------download  end md5-----------" + mFileMd5);
            d("------------download  end md5-----------" + md5);


            d("------------ md5 是否一致-----------" + mFileMd5.equals(md5));
            return mFileMd5.equals(md5);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            d("---------------------finally ---------------------");
            try {
                if (input != null) {
                    input.close();
                }
                if (in != null) {
                    in.close();
                }
                if (accessFile != null) {
                    accessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mIsDownloading = false;
        }
        return false;
    }

    /**
     * 下载进度监听
     *
     * @param progress
     */
    public void setDownloadProgress(ProgressResponseBody.ProgressListener progress) {
        this.mProgressListener = progress;
    }

    private void delete(Context context, File file) {
        if (file.exists()) {

            boolean isDel = file.delete();
            d("--文件存在,开始删除---删除：" + isDel);

        } else {
            d("--文件不存在，无法删除---");
        }
        SerializableUtils.deleteObject(context, DownLoadTempData.class.getName());
    }

    private void delete(Context context, String url, String path) {
        final String name = FileUtils.getMD5(url) + ".apk";
        File file = new File(path + "/" + name);
        delete(context, file);
    }

    private void d(String msg) {
        Log.d("update", msg);
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {

        try {
            if (context == null) {
                return false;
            }
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
                return false;
            }
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
        }catch (Exception ex){

            return  false;
        }

    }
}
