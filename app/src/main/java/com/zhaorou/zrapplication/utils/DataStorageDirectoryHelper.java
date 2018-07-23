package com.zhaorou.zrapplication.utils;


import android.content.ContextWrapper;
import android.os.Environment;

import java.io.File;

/**
 * Created by star on 2018/3/20.
 */

public class DataStorageDirectoryHelper {
    private DataStorageDirectoryHelper() {
    }

    /**
     * 获取Internal Storage文件系统目录(e.g. /data/data/包名/files)
     *
     * @param contextWrapper
     * @return
     */
    public static File getInternalFilesDir(ContextWrapper contextWrapper) {
        return contextWrapper.getFilesDir();
    }

    /**
     * 在Internal Storage中新建（或打开现有的）目录。
     * 即在 data/data/包名 目录下新建（或打开现有的）目录
     *
     * @param contextWrapper
     * @param dirName
     * @param mode
     * @return
     */
    public static File getInternalFilesDir(ContextWrapper contextWrapper, String dirName, int mode) {
        return contextWrapper.getDir(dirName, mode);
    }

    /**
     * Internal Storage删除 /data/data/包名/files 目录中name名的文件
     *
     * @param contextWrapper
     * @param fileName
     * @return
     */
    public static boolean deleteInternalFile(ContextWrapper contextWrapper, String fileName) {
        return contextWrapper.deleteFile(fileName);
    }

    /**
     * Internal Storage应用的缓存目录(/data/data/包名/cache)
     *
     * @param contextWrapper
     * @return
     */
    public static File getCacheDir(ContextWrapper contextWrapper) {
        return isExternalStorageAvailable() ? contextWrapper.getCacheDir() : null;
    }

    /**
     * 检测External Storage是否可用（可读写）
     *
     * @return
     */
    public static boolean isExternalStorageAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * External Storage获取系统Pictures目录（截图）
     *
     * @return
     */
    public static File getExternalPublicPicturesDir() {
        return isExternalStorageAvailable() ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) : null;
    }

    /**
     * External Storage获取系统Music目录
     *
     * @return
     */
    public static File getExternalPublicMusicDir() {
        return isExternalStorageAvailable() ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) : null;
    }

    /**
     * External Storage获取系统Downloads目录
     *
     * @return
     */
    public static File getExternalPublicDownloadsDir() {
        return isExternalStorageAvailable() ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) : null;
    }

    /**
     * External Storage获取系统Alarms目录
     *
     * @return
     */
    public static File getExternalPublicAlarmsDir() {
        return isExternalStorageAvailable() ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS) : null;
    }

    /**
     * External Storage获取系统DCIM目录（相册）
     *
     * @return
     */
    public static File getExternalPublicDcimDir() {
        return isExternalStorageAvailable() ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) : null;
    }

    /**
     * External Storage获取系统Documents目录
     *
     * @return
     */
    public static File getExternalPublicDocumentsDir() {
        return isExternalStorageAvailable() ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) : null;
    }

    /**
     * External Storage获取系统Movies目录
     *
     * @return
     */
    public static File getExternalPublicMoviesDir() {
        return isExternalStorageAvailable() ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) : null;
    }

    /**
     * External Storage获取系统Notifications目录
     *
     * @return
     */
    public static File getExternalPublicNotificationsDir() {
        return isExternalStorageAvailable() ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS) : null;
    }

    /**
     * External Storage获取系统Podcasts目录
     *
     * @return
     */
    public static File getExternalPublicPodcastsDir() {
        return isExternalStorageAvailable() ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS) : null;
    }

    /**
     * External Storage获取系统Ringtones目录
     *
     * @return
     */
    public static File getExternalPublicRingtonesDir() {
        return isExternalStorageAvailable() ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES) : null;
    }

    /**
     * External Storage获取应用私有目录(/storage/emulated/0/Android/data/包名/files/)
     *
     * @param contextWrapper
     * @param type           (null,Environment.DIRECTORY_RINGTONES,Environment.DIRECTORY_PODCASTS,
     *                       Environment.DIRECTORY_NOTIFICATIONS,Environment.DIRECTORY_MOVIES,
     *                       Environment.DIRECTORY_DOCUMENTS,Environment.DIRECTORY_DCIM,
     *                       Environment.DIRECTORY_ALARMS,Environment.DIRECTORY_DOWNLOADS,
     *                       Environment.DIRECTORY_MUSIC,Environment.DIRECTORY_PICTURES)
     * @return
     */
    public static File getExternalFilesDir(ContextWrapper contextWrapper, String type) {
        return isExternalStorageAvailable() ? contextWrapper.getExternalFilesDir(type) : null;
    }

    /**
     * External Storage应用缓存目录(/storage/emulated/0/Android/data/包名/cache)
     *
     * @param contextWrapper
     * @return
     */
    public static File getExternalCacheDir(ContextWrapper contextWrapper) {
        return isExternalStorageAvailable() ? contextWrapper.getExternalCacheDir() : null;
    }
}
