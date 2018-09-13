package com.zhaorou.zrapplication.network.update;

/**
 * 更新app回调接口
 *
 * @author kw
 */

public interface IRecoveryDownload {

    /**
     * 更新app回调接口
     *
     * @param isDownOk 下载是否完成
     * @param tips     提示
     * @param path     缓存文件夹的路径
     */
    void downloadApk(boolean isDownOk, String tips, String path);

}
