package com.zhaorou.zhuanquanapp.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;

/**
 * Created by lykevin on 2018/4/9.
 */
public class PhotoCapturer {
    private Context context;
    private static volatile PhotoCapturer photoPicker;

    private PhotoCapturer(Context context) {
        this.context = context;
    }

    public static PhotoCapturer newInstance(Context context) {
        if (photoPicker == null) {
            synchronized (PhotoCapturer.class) {
                if (photoPicker == null) {
                    photoPicker = new PhotoCapturer(context);
                }
            }
        }
        return photoPicker;
    }

    public Uri takePhoto(Activity activity, int requestCode) {
        Uri uriForFile;
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        String fileName = "img" + System.nanoTime() + ".jpg";
        File outputImage = new File(directory, fileName);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            uriForFile = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", outputImage);
        } else {
            uriForFile = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
        activity.startActivityForResult(intent, requestCode);
        return uriForFile;
    }

    public void selectFromAlbum(Activity activity, int requestCode) {
        Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
        intent1.setType("image/*");
        activity.startActivityForResult(intent1, requestCode);
    }

    public String handleImageFromAlbum(Intent data) {
        String imagePath;
        if (Build.VERSION.SDK_INT >= 19) {
            imagePath = handleImageOnKitKat(data);
        } else {
            imagePath = handleImageBeforeKitKat(data);
        }
        return imagePath;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String handleImageOnKitKat(Intent data) {
        String imagePath = "";
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    private String handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        return imagePath;
    }

    private String getImagePath(Uri uri, String selection) {
        String path = "";
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
