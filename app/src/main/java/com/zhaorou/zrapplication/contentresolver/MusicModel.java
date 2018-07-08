package com.zhaorou.zrapplication.contentresolver;

import com.google.gson.annotations.SerializedName;

public class MusicModel {

    /**
     * _id : 98061
     * _data : /storage/emulated/0/MIUI/ringtone/金光布袋戏 - 初心无悔 [mqms2].mp3
     * _display_name : 金光布袋戏 - 初心无悔 [mqms2].mp3
     * _size : 8522462
     * mime_type : audio/mpeg
     * date_added : 1515629643
     * is_drm : 0
     * date_modified : 1514789096
     * title : HKC371701671-初心无悔
     * title_key :
     * duration : 213032
     * artist_id : 29
     * album_id : 45
     * track : 0
     * is_ringtone : 0
     * is_music : 1
     * is_alarm : 0
     * is_notification : 0
     * is_podcast : 0
     * artist_id:1 : 29
     * artist_key :
     * artist : 金光布袋戏
     * album_id:1 : 45
     * album_key :
     * album : 金光御九界之东皇战影 剧集原声带
     */

    @SerializedName("_id")
    private String id;
    @SerializedName("_data")
    private String path;
    @SerializedName("_display_name")
    private String displayName;
    @SerializedName("_size")
    private long size;
    @SerializedName("mime_type")
    private String mimeType;
    @SerializedName("date_added")
    private long dateAdded;
    @SerializedName("is_drm")
    private boolean isDrm;
    @SerializedName("date_modified")
    private long dateModified;
    private String title;
    @SerializedName("title_key")
    private String titleKey;
    private long duration;
    @SerializedName("artist_id")
    private int artistId;
    @SerializedName("album_id")
    private int albumId;
    private String track;
    @SerializedName("is_ringtone")
    private boolean isRingtone;
    @SerializedName("is_music")
    private boolean isMusic;
    @SerializedName("is_alarm")
    private boolean isAlarm;
    @SerializedName("is_notification")
    private boolean isNotification;
    @SerializedName("is_podcast")
    private boolean isPodcast;
    @SerializedName("artist_id:1")
    private int artistId_1;
    @SerializedName("artist_key")
    private String artistKey;
    private String artist;
    @SerializedName("album_id:1")
    private int albumId_1;
    private String album_key;
    private String album;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isDrm() {
        return isDrm;
    }

    public void setIsDrm(boolean isDrm) {
        this.isDrm = isDrm;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public boolean isRingtone() {
        return isRingtone;
    }

    public void setIsRingtone(boolean isRingtone) {
        this.isRingtone = isRingtone;
    }

    public boolean isMusic() {
        return isMusic;
    }

    public void setIsMusic(boolean isMusic) {
        this.isMusic = isMusic;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

    public boolean isNotification() {
        return isNotification;
    }

    public void setIsNotification(boolean isNotification) {
        this.isNotification = isNotification;
    }

    public boolean isPodcast() {
        return isPodcast;
    }

    public void setIsPodcast(boolean isPodcast) {
        this.isPodcast = isPodcast;
    }

    public int getArtistId_1() {
        return artistId_1;
    }

    public void setArtistId_1(int artistId_1) {
        this.artistId_1 = artistId_1;
    }

    public String getArtistKey() {
        return artistKey;
    }

    public void setArtistKey(String artistKey) {
        this.artistKey = artistKey;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getAlbumId_1() {
        return albumId_1;
    }

    public void setAlbumId_1(int albumId_1) {
        this.albumId_1 = albumId_1;
    }

    public String getAlbum_key() {
        return album_key;
    }

    public void setAlbum_key(String album_key) {
        this.album_key = album_key;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
