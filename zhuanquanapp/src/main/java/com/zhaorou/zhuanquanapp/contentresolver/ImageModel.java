package com.zhaorou.zhuanquanapp.contentresolver;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageModel implements Parcelable {

    /**
     * _id : 173463
     * _data : /storage/emulated/0/DCIM/Camera/IMG_20180409_102543_BURST1.jpg
     * _size : 5440606
     * _display_name : IMG_20180409_102543_BURST1.jpg
     * mime_type : image/jpeg
     * title : IMG_20180409_102543_BURST1
     * date_added : 1523240743
     * date_modified : 1523240743
     * latitude : 24.5044
     * longitude : 118.142
     * datetaken : 1523240743224
     * orientation : 90
     * bucket_id : -1739773001
     * bucket_display_name : Camera
     * width : 4032
     * height : 3016
     */

    @SerializedName("_id")
    private String id;
    @SerializedName("_data")
    private String path;
    @SerializedName("_size")
    private long size;
    @SerializedName("_display_name")
    private String displayName;
    @SerializedName("mime_type")
    private String mimeType;
    private String title;
    @SerializedName("date_added")
    private long dateAdded;
    @SerializedName("date_modified")
    private long dateModified;
    private double latitude;
    private double longitude;
    @SerializedName("datetaken")
    private long dateTaken;
    private int orientation;
    private long bucket_id;
    private String bucket_display_name;
    private int width;
    private int height;
    private boolean isSelected;

    public ImageModel() {
    }

    protected ImageModel(Parcel in) {
        id = in.readString();
        path = in.readString();
        size = in.readLong();
        displayName = in.readString();
        mimeType = in.readString();
        title = in.readString();
        dateAdded = in.readLong();
        dateModified = in.readLong();
        latitude = in.readDouble();
        longitude = in.readDouble();
        dateTaken = in.readLong();
        orientation = in.readInt();
        bucket_id = in.readLong();
        bucket_display_name = in.readString();
        width = in.readInt();
        height = in.readInt();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public long getBucket_id() {
        return bucket_id;
    }

    public void setBucket_id(long bucket_id) {
        this.bucket_id = bucket_id;
    }

    public String getBucket_display_name() {
        return bucket_display_name;
    }

    public void setBucket_display_name(String bucket_display_name) {
        this.bucket_display_name = bucket_display_name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(path);
        dest.writeLong(size);
        dest.writeString(displayName);
        dest.writeString(mimeType);
        dest.writeString(title);
        dest.writeLong(dateAdded);
        dest.writeLong(dateModified);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeLong(dateTaken);
        dest.writeInt(orientation);
        dest.writeLong(bucket_id);
        dest.writeString(bucket_display_name);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}
