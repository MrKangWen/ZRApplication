package com.zhaorou.zrapplication.contentresolver;

import com.google.gson.annotations.SerializedName;

public class ContactModel {

    /**
     * account_type : com.xiaomi
     * data_version : 0
     * name_verified : 0
     * display_name_alt : 叶海
     * sort_key_alt : 叶海
     * starred : 0
     * has_phone_number : 1
     * raw_contact_id : 304
     * contact_account_type : com.xiaomi
     * carrier_presence : 0
     * contact_last_updated_timestamp : 1516769191377
     * phonebook_bucket : 25
     * display_name : 叶海
     * sort_key : 叶海
     * version : 3
     * in_default_directory : 1
     * times_contacted : 0
     * _id : 957
     * account_type_and_data_set : com.xiaomi
     * name_raw_contact_id : 304
     * phonebook_bucket_alt : 25
     * last_time_contacted : 0
     * pinned : 0
     * is_primary : 0
     * contact_id : 339
     * in_visible_group : 1
     * phonebook_label : Y
     * account_name : 412979213
     * display_name_source : 40
     * dirty : 0
     * sourceid : 32302221821509761
     * phonetic_name_style : 0
     * send_to_voicemail : 0
     * lookup : 3430i32302221821509761
     * phonebook_label_alt : Y
     * is_super_primary : 0
     * data4 : 19024329121
     * data2 : 2
     * data1 : 190 2432 9121
     * raw_contact_is_user_profile : 0
     * mimetype : vnd.android.cursor.item/phone_v2
     */

    @SerializedName("account_type")
    private String accountType;
    @SerializedName("data_version")
    private String dataVersion;
    @SerializedName("name_verified")
    private String nameVerified;
    @SerializedName("display_name_alt")
    private String displayNameAlt;
    @SerializedName("sort_key_alt")
    private String sortKeyAlt;
    private String starred;
    @SerializedName("has_phone_number")
    private boolean hasPhoneNumber;
    @SerializedName("raw_contact_id")
    private String rawContactId;
    @SerializedName("contact_account_type")
    private String contactAccountType;
    @SerializedName("carrier_presence")
    private String carrierPresence;
    @SerializedName("contact_last_updated_timestamp")
    private long contactLastPpdatedTimestamp;
    @SerializedName("phonebook_bucket")
    private int phonebookBucket;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("sort_key")
    private String sortKey;
    private String version;
    @SerializedName("in_default_directory")
    private boolean inDefaultdirectory;
    @SerializedName("times_contacted")
    private int timesContacted;
    @SerializedName("_id")
    private String id;
    @SerializedName("account_type_and_data_set")
    private String accountTypeAndDataSet;
    @SerializedName("name_raw_contact_id")
    private String nameRawContactId;
    @SerializedName("phonebook_bucket_alt")
    private String phonebookBucketAlt;
    @SerializedName("last_time_contacted")
    private String lastTimeContacted;
    private String pinned;
    @SerializedName("is_primary")
    private boolean isPrimary;
    @SerializedName("contact_id")
    private String contactId;
    @SerializedName("in_visible_group")
    private boolean inVisibleGroup;
    @SerializedName("phonebook_label")
    private String phonebookLabel;
    @SerializedName("account_name")
    private String accountName;
    @SerializedName("display_name_source")
    private String displayNameSource;
    private String dirty;
    private String sourceid;
    @SerializedName("phonetic_name_style")
    private String phoneticNameStyle;
    @SerializedName("send_to_voicemail")
    private String sendToVoicemail;
    private String lookup;
    @SerializedName("phonebook_label_alt")
    private String phonebookLabelAlt;
    @SerializedName("is_super_primary")
    private boolean isSuperPrimary;
    private String data4;
    private String data2;
    private String data1;
    @SerializedName("raw_contact_is_user_profile")
    private String rawContactIsUserProfile;
    private String mimetype;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getNameVerified() {
        return nameVerified;
    }

    public void setNameVerified(String nameVerified) {
        this.nameVerified = nameVerified;
    }

    public String getDisplayNameAlt() {
        return displayNameAlt;
    }

    public void setDisplayNameAlt(String displayNameAlt) {
        this.displayNameAlt = displayNameAlt;
    }

    public String getSortKeyAlt() {
        return sortKeyAlt;
    }

    public void setSortKeyAlt(String sortKeyAlt) {
        this.sortKeyAlt = sortKeyAlt;
    }

    public String getStarred() {
        return starred;
    }

    public void setStarred(String starred) {
        this.starred = starred;
    }

    public boolean isHasPhoneNumber() {
        return hasPhoneNumber;
    }

    public void setHasPhoneNumber(boolean hasPhoneNumber) {
        this.hasPhoneNumber = hasPhoneNumber;
    }

    public String getRawContactId() {
        return rawContactId;
    }

    public void setRawContactId(String rawContactId) {
        this.rawContactId = rawContactId;
    }

    public String getContactAccountType() {
        return contactAccountType;
    }

    public void setContactAccountType(String contactAccountType) {
        this.contactAccountType = contactAccountType;
    }

    public String getCarrierPresence() {
        return carrierPresence;
    }

    public void setCarrierPresence(String carrierPresence) {
        this.carrierPresence = carrierPresence;
    }

    public long getContactLastPpdatedTimestamp() {
        return contactLastPpdatedTimestamp;
    }

    public void setContactLastPpdatedTimestamp(long contactLastPpdatedTimestamp) {
        this.contactLastPpdatedTimestamp = contactLastPpdatedTimestamp;
    }

    public int getPhonebookBucket() {
        return phonebookBucket;
    }

    public void setPhonebookBucket(int phonebookBucket) {
        this.phonebookBucket = phonebookBucket;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isInDefaultdirectory() {
        return inDefaultdirectory;
    }

    public void setInDefaultdirectory(boolean inDefaultdirectory) {
        this.inDefaultdirectory = inDefaultdirectory;
    }

    public int getTimesContacted() {
        return timesContacted;
    }

    public void setTimesContacted(int timesContacted) {
        this.timesContacted = timesContacted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountTypeAndDataSet() {
        return accountTypeAndDataSet;
    }

    public void setAccountTypeAndDataSet(String accountTypeAndDataSet) {
        this.accountTypeAndDataSet = accountTypeAndDataSet;
    }

    public String getNameRawContactId() {
        return nameRawContactId;
    }

    public void setNameRawContactId(String nameRawContactId) {
        this.nameRawContactId = nameRawContactId;
    }

    public String getPhonebookBucketAlt() {
        return phonebookBucketAlt;
    }

    public void setPhonebookBucketAlt(String phonebookBucketAlt) {
        this.phonebookBucketAlt = phonebookBucketAlt;
    }

    public String getLastTimeContacted() {
        return lastTimeContacted;
    }

    public void setLastTimeContacted(String lastTimeContacted) {
        this.lastTimeContacted = lastTimeContacted;
    }

    public String getPinned() {
        return pinned;
    }

    public void setPinned(String pinned) {
        this.pinned = pinned;
    }

    public boolean isIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public boolean isInVisibleGroup() {
        return inVisibleGroup;
    }

    public void setInVisibleGroup(boolean inVisibleGroup) {
        this.inVisibleGroup = inVisibleGroup;
    }

    public String getPhonebookLabel() {
        return phonebookLabel;
    }

    public void setPhonebookLabel(String phonebookLabel) {
        this.phonebookLabel = phonebookLabel;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDisplayNameSource() {
        return displayNameSource;
    }

    public void setDisplayNameSource(String displayNameSource) {
        this.displayNameSource = displayNameSource;
    }

    public String getDirty() {
        return dirty;
    }

    public void setDirty(String dirty) {
        this.dirty = dirty;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getPhoneticNameStyle() {
        return phoneticNameStyle;
    }

    public void setPhoneticNameStyle(String phoneticNameStyle) {
        this.phoneticNameStyle = phoneticNameStyle;
    }

    public String getSendToVoicemail() {
        return sendToVoicemail;
    }

    public void setSendToVoicemail(String sendToVoicemail) {
        this.sendToVoicemail = sendToVoicemail;
    }

    public String getLookup() {
        return lookup;
    }

    public void setLookup(String lookup) {
        this.lookup = lookup;
    }

    public String getPhonebookLabelAlt() {
        return phonebookLabelAlt;
    }

    public void setPhonebookLabelAlt(String phonebookLabelAlt) {
        this.phonebookLabelAlt = phonebookLabelAlt;
    }

    public boolean isIsSuperPrimary() {
        return isSuperPrimary;
    }

    public void setIsSuperPrimary(boolean isSuperPrimary) {
        this.isSuperPrimary = isSuperPrimary;
    }

    public String getData4() {
        return data4;
    }

    public void setData4(String data4) {
        this.data4 = data4;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getRawContactIsUserProfile() {
        return rawContactIsUserProfile;
    }

    public void setRawContactIsUserProfile(String rawContactIsUserProfile) {
        this.rawContactIsUserProfile = rawContactIsUserProfile;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
}
