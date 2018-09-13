package com.zhaorou.zrapplication.home.model;

import com.google.gson.annotations.SerializedName;
import com.zhaorou.zrapplication.base.BaseModel;

/**
 * @author kang
 */
public class AppUpdateModel extends BaseModel {


    /**
     * data : {"entity":{"id":1,"type":1,"version":"1.0","code":"10","download_url":"http://app.zhaoroudan.com/110_0b46f245fce078098ee7afab17d336a3.apk","update_tip":"有新版本","filename":"110_0b46f245fce078098ee7afab17d336a3.apk","md5":"4e8bea49c51426f4802d8490e11569de","update_time":"2018-09-13 14:50:26","is_delete":0}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * entity : {"id":1,"type":1,"version":"1.0","code":"10","download_url":"http://app.zhaoroudan.com/110_0b46f245fce078098ee7afab17d336a3.apk","update_tip":"有新版本","filename":"110_0b46f245fce078098ee7afab17d336a3.apk","md5":"4e8bea49c51426f4802d8490e11569de","update_time":"2018-09-13 14:50:26","is_delete":0}
         */

        private EntityBean entity;

        public EntityBean getEntity() {
            return entity;
        }

        public void setEntity(EntityBean entity) {
            this.entity = entity;
        }

        public static class EntityBean {
            /**
             * id : 1
             * type : 1
             * version : 1.0
             * code : 10
             * download_url : http://app.zhaoroudan.com/110_0b46f245fce078098ee7afab17d336a3.apk
             * update_tip : 有新版本
             * filename : 110_0b46f245fce078098ee7afab17d336a3.apk
             * md5 : 4e8bea49c51426f4802d8490e11569de
             * update_time : 2018-09-13 14:50:26
             * is_delete : 0
             */

            private int id;
            private int type;
            private String version;
            @SerializedName("code")
            private String codeX;
            private String download_url;
            private String update_tip;
            private String filename;
            private String md5;
            private String update_time;
            private int is_delete;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getCodeX() {
                return codeX;
            }

            public void setCodeX(String codeX) {
                this.codeX = codeX;
            }

            public String getDownload_url() {
                return download_url;
            }

            public void setDownload_url(String download_url) {
                this.download_url = download_url;
            }

            public String getUpdate_tip() {
                return update_tip;
            }

            public void setUpdate_tip(String update_tip) {
                this.update_tip = update_tip;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getMd5() {
                return md5;
            }

            public void setMd5(String md5) {
                this.md5 = md5;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }
        }
    }
}
