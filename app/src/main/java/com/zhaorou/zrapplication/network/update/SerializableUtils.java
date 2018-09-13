package com.zhaorou.zrapplication.network.update;

import android.content.Context;

import com.zhaorou.zrapplication.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 序列化
 */

public class SerializableUtils {

    /**
     * 保存对象
     *
     * @param ser
     * @throws IOException
     */
    public static boolean saveObject(Context context, Serializable ser) {


        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {

            String filename = FileUtils.getMD5(ser.getClass().getName());
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteObject(Context context, String className) {
        if (context == null) {
            return false;
        }
        File data = context.getFileStreamPath(FileUtils.getMD5(className));
        if (data.exists()) {
            return data.delete();
        }

        return false;
    }

    /**
     * 读取对象
     *
     * @param className
     * @return
     * @throws IOException
     */
    public static Serializable readObject(Context context, String className) {

        String md5Name = FileUtils.getMD5(className);
        if (!isExistDataCache(context, className)) {
            return null;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(md5Name);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        }  catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(md5Name);
                data.delete();
            }
        } finally {

            try {
                if (fis != null) {
                    fis.close();
                }

                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 判断缓存是否存在
     *
     * @param cachefile
     * @return
     */
    public static boolean isExistDataCache(Context context, String cachefile) {
        if (context == null) {
            return false;
        }
        boolean exist = false;
        File data = context.getFileStreamPath(FileUtils.getMD5(cachefile));
        if (data.exists()) {
            exist = true;
        }
        return exist;
    }

}
