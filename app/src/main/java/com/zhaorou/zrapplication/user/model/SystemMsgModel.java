package com.zhaorou.zrapplication.user.model;

import com.zhaorou.zrapplication.base.BaseModel;

import java.util.List;

/**
 * @author kang
 */
public class SystemMsgModel  extends BaseModel {


    /**
     * data : {"list":[{"id":9,"title":"现在网站全新升级，访问速度跟坐火箭一样","content":"现在网站全新升级，访问速度跟坐火箭一样，欢迎大家体验和反馈意见，有你们在会更好！","is_delete":0,"is_publish":1,"create_time":"2018-08-31 17:20:40","publish_time":"2018-08-31 17:20:40"},{"id":10,"title":"关于放假","content":"放假日期为：9月20号到9月22号，期间正常审核，晚上统一有值班人员审核！大家勿催！","is_delete":0,"is_publish":1,"create_time":"2018-09-20 01:13:11","publish_time":"2018-09-20 09:57:22"}],"pages":null}
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
         * list : [{"id":9,"title":"现在网站全新升级，访问速度跟坐火箭一样","content":"现在网站全新升级，访问速度跟坐火箭一样，欢迎大家体验和反馈意见，有你们在会更好！","is_delete":0,"is_publish":1,"create_time":"2018-08-31 17:20:40","publish_time":"2018-08-31 17:20:40"},{"id":10,"title":"关于放假","content":"放假日期为：9月20号到9月22号，期间正常审核，晚上统一有值班人员审核！大家勿催！","is_delete":0,"is_publish":1,"create_time":"2018-09-20 01:13:11","publish_time":"2018-09-20 09:57:22"}]
         * pages : null
         */

        private Object pages;
        private List<ListBean> list;

        public Object getPages() {
            return pages;
        }

        public void setPages(Object pages) {
            this.pages = pages;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 9
             * title : 现在网站全新升级，访问速度跟坐火箭一样
             * content : 现在网站全新升级，访问速度跟坐火箭一样，欢迎大家体验和反馈意见，有你们在会更好！
             * is_delete : 0
             * is_publish : 1
             * create_time : 2018-08-31 17:20:40
             * publish_time : 2018-08-31 17:20:40
             */

            private int id;
            private String title;
            private String content;
            private int is_delete;
            private int is_publish;
            private String create_time;
            private String publish_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }

            public int getIs_publish() {
                return is_publish;
            }

            public void setIs_publish(int is_publish) {
                this.is_publish = is_publish;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getPublish_time() {
                return publish_time;
            }

            public void setPublish_time(String publish_time) {
                this.publish_time = publish_time;
            }
        }
    }
}
