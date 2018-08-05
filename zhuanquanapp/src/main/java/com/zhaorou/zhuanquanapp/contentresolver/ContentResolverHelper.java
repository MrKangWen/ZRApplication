package com.zhaorou.zhuanquanapp.contentresolver;

import android.content.ContextWrapper;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContentResolverHelper {
    private static final String TAG = "ContentResolverHelper";
    private static Gson gson;

    private ContentResolverHelper() {
    }

    /**
     * 从系统相册中获取图片
     *
     * @param contextWrapper
     * @return
     */
    public static List<ImageModel> queryImagesFromExternal(ContextWrapper contextWrapper) {
        List<ImageModel> imageModelList = new ArrayList<>();
        Cursor cursor = contextWrapper.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            gson = new Gson();
            while (cursor.moveToNext()) {
                try {
                    JSONObject json = new JSONObject();
                    String[] columnNames = cursor.getColumnNames();
                    for (String columnName : columnNames) {
                        String s = cursor.getString(cursor.getColumnIndex(columnName));
                        json.put(columnName, s);
                    }
                    ImageModel imageModel = gson.fromJson(json.toString(), ImageModel.class);
                    imageModelList.add(imageModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return imageModelList;
    }

    public static List<MusicModel> queryMusicsFromExternal(ContextWrapper contextWrapper) {
        List<MusicModel> musicModelList = new ArrayList<>();
        Cursor cursor = contextWrapper.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            gson = new Gson();
            while (cursor.moveToNext()) {
                try {
                    JSONObject json = new JSONObject();
                    String[] columnNames = cursor.getColumnNames();
                    for (String columnName : columnNames) {
                        String s = cursor.getString(cursor.getColumnIndex(columnName));
                        json.put(columnName, s);
                    }
                    MusicModel musicModel = gson.fromJson(json.toString(), MusicModel.class);
                    musicModelList.add(musicModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return musicModelList;
    }

    public static List<ContactModel> queryContacts(ContextWrapper contextWrapper) {
        List<ContactModel> contactModelList = new ArrayList<>();
        Cursor cursor = contextWrapper.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            gson = new Gson();
            while (cursor.moveToNext()) {
                try {
                    JSONObject json = new JSONObject();
                    String[] columnNames = cursor.getColumnNames();
                    for (String columnName : columnNames) {
                        String s = cursor.getString(cursor.getColumnIndex(columnName));
                        json.put(columnName, s);
                    }
                    ContactModel contactModel = gson.fromJson(json.toString(), ContactModel.class);
                    contactModelList.add(contactModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return contactModelList;
    }
}
