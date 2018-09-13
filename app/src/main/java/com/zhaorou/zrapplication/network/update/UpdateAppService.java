package com.zhaorou.zrapplication.network.update;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;


import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.utils.FileUtils;

import java.io.File;

/**
 * 更新服务
 */
public class UpdateAppService extends IntentService implements IRecoveryDownload {


    public static final String IsNeedWifiDownloading = "isNeedWifiDownloading";
    public static final String TipsInstall = "tipsInstall";
    public static final String MD5_KEY = "MD5_KEY";
    public static final String APP_URL = "APP_URL";
    private RecoveryDownloadPresenter downloadPresenter;
    private String mMD5 = "";

    public UpdateAppService() {
        super("UpdateAppService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, new ServiceInsidePublicNotification(this).getNotification(
                    getResources().getString(R.string.app_name),
                    "正在更新...."));
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            String appUrl = intent.getStringExtra(APP_URL);
            mMD5 = intent.getStringExtra(MD5_KEY);
            if (TextUtils.isEmpty(mMD5) || TextUtils.isEmpty(appUrl)) {
                return;
            }
            boolean isNeedWifiDownloading = intent.getBooleanExtra(IsNeedWifiDownloading, false);
            boolean mTipsInstall = intent.getBooleanExtra(TipsInstall, true);


            downloadPresenter = RecoveryDownloadPresenter.getRecoveryDownload(this);
            downloadPresenter.setTipsInstall(mTipsInstall);

            String path;
            File file = getExternalCacheDir();
            if (file == null) {
                //容错策略 ,一般不会到这里
                file = Environment.getExternalStorageDirectory();
                if (file == null) {
                    return;
                }
                path = file.getAbsolutePath() + "/ZrApp/apk";

            } else {
                path = file.getPath();
            }

            if (!TextUtils.isEmpty(path)) {
                downloadPresenter.downloadFile(getApplicationContext(), mMD5, appUrl, path, isNeedWifiDownloading);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void downloadApk(boolean isDownOk, String tips, String path) {


        if (!isDownOk) {
            Looper.prepare();
            Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_LONG).show();
            Looper.loop();
            return;
        }

        //添加二次md5检验
        if (downloadPresenter.isTipsInstall() && FileUtils.getFileMD5ToString(path).equals(mMD5)) {
            startInstall(this, path);
        }

    }

    /**
     * 兼容7.0系统安装
     *
     * @param context
     * @param downloadFileUrl
     */
    private void startInstall(Context context, String downloadFileUrl) {

        File file = new File(downloadFileUrl);
        if (!file.isFile()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        } else {
            //在 AndroidManifest.xml 中定义 provider
            String name = context.getPackageName();
            Uri uri = FileProvider.getUriForFile(context.getApplicationContext(), name + ".fileprovider", file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
