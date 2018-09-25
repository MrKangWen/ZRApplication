package com.zhaorou.zrapplication.user.model;

import com.zhaorou.zrapplication.base.BaseModel;

import java.util.List;

/**
 * has read
 * @author kang
 */
public class HasReadMsgModel extends BaseModel {


    /**
     * data : {"list":[{"id":2456,"type":5,"notice_id":10,"content":"关于放假","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-20 01:48:10"},{"id":2322,"type":1,"notice_id":null,"content":"您提交的文案已被系统采纳，特此奖励积分+10，商品标题【】","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-19 18:59:44"},{"id":2312,"type":7,"notice_id":null,"content":"您已设置【【瘦腿弹力靴】过膝长靴粗跟小辣椒百搭】肉单的直播提醒，直播时间2018-09-18 00:10:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-17 23:53:24"},{"id":2308,"type":7,"notice_id":null,"content":"您已设置【【俞兆林官方旗舰店】罩杯胸垫一体式背心打底衫】肉单的直播提醒，直播时间2018-09-17 23:30:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-17 23:14:22"},{"id":2304,"type":7,"notice_id":null,"content":"您已设置【【俞兆林官方旗舰店】无痕蕾丝边内裤3条装】肉单的直播提醒，直播时间2018-09-18 10:00:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-17 22:24:34"},{"id":2269,"type":7,"notice_id":null,"content":"您已设置【【佳农】菲律宾进口凤梨5斤装】肉单的直播提醒，直播时间2018-09-18 09:50:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-17 12:03:37"},{"id":2268,"type":7,"notice_id":null,"content":"您已设置【商品测试标题】肉单的直播提醒，直播时间2018-09-18 09:30:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-17 12:03:35"},{"id":2267,"type":7,"notice_id":null,"content":"您已设置【【吉得利】奥尔良烤翅腌料【165g*2罐】】肉单的直播提醒，直播时间2018-09-18 09:20:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-17 12:03:32"},{"id":2266,"type":7,"notice_id":null,"content":"您已设置【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】肉单的直播提醒，直播时间2018-09-17 10:50:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-16 22:24:02"},{"id":2265,"type":7,"notice_id":null,"content":"您已设置【【欧诗漫旗舰店】珍珠美白洗面奶100ml】肉单的直播提醒，直播时间2018-09-17 10:20:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-16 21:01:18"},{"id":2264,"type":7,"notice_id":null,"content":"您已设置【【雅酷美】迷你隐形无线运动蓝牙耳机】肉单的直播提醒，直播时间2018-09-17 09:10:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-16 20:52:27"},{"id":2234,"type":7,"notice_id":null,"content":"您已设置【【欧诗漫旗舰店】珍珠美白洗面奶100ml】肉单的直播提醒，直播时间2018-09-16 09:30:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-16 08:59:40"},{"id":2233,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】珍珠美白洗面奶100ml】直播时间2018-09-15 09:30:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-15 20:55:54"},{"id":2232,"type":7,"notice_id":null,"content":"【男士自动扣皮带商务头层纯牛皮青年】直播时间2018-09-08 19:00:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-15 20:55:42"},{"id":2220,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】直播时间2018-09-14 21:35:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-15 09:04:29"},{"id":2192,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】直播时间0000-00-00 00:00:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-14 20:01:44"},{"id":2191,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】直播时间0000-00-00 00:00:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-14 18:53:40"},{"id":2190,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】直播时间0000-00-00 00:00:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-14 18:53:27"},{"id":2189,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】直播时间0000-00-00 00:00:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-14 18:52:22"},{"id":2188,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】直播时间0000-00-00 00:00:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-14 18:52:08"}],"hasMore":true,"total":31}
     * code : 200
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
         * list : [{"id":2456,"type":5,"notice_id":10,"content":"关于放假","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-20 01:48:10"},{"id":2322,"type":1,"notice_id":null,"content":"您提交的文案已被系统采纳，特此奖励积分+10，商品标题【】","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-19 18:59:44"},{"id":2312,"type":7,"notice_id":null,"content":"您已设置【【瘦腿弹力靴】过膝长靴粗跟小辣椒百搭】肉单的直播提醒，直播时间2018-09-18 00:10:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-17 23:53:24"},{"id":2308,"type":7,"notice_id":null,"content":"您已设置【【俞兆林官方旗舰店】罩杯胸垫一体式背心打底衫】肉单的直播提醒，直播时间2018-09-17 23:30:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-17 23:14:22"},{"id":2304,"type":7,"notice_id":null,"content":"您已设置【【俞兆林官方旗舰店】无痕蕾丝边内裤3条装】肉单的直播提醒，直播时间2018-09-18 10:00:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-17 22:24:34"},{"id":2269,"type":7,"notice_id":null,"content":"您已设置【【佳农】菲律宾进口凤梨5斤装】肉单的直播提醒，直播时间2018-09-18 09:50:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-17 12:03:37"},{"id":2268,"type":7,"notice_id":null,"content":"您已设置【商品测试标题】肉单的直播提醒，直播时间2018-09-18 09:30:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-17 12:03:35"},{"id":2267,"type":7,"notice_id":null,"content":"您已设置【【吉得利】奥尔良烤翅腌料【165g*2罐】】肉单的直播提醒，直播时间2018-09-18 09:20:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-17 12:03:32"},{"id":2266,"type":7,"notice_id":null,"content":"您已设置【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】肉单的直播提醒，直播时间2018-09-17 10:50:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-16 22:24:02"},{"id":2265,"type":7,"notice_id":null,"content":"您已设置【【欧诗漫旗舰店】珍珠美白洗面奶100ml】肉单的直播提醒，直播时间2018-09-17 10:20:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-16 21:01:18"},{"id":2264,"type":7,"notice_id":null,"content":"您已设置【【雅酷美】迷你隐形无线运动蓝牙耳机】肉单的直播提醒，直播时间2018-09-17 09:10:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-16 20:52:27"},{"id":2234,"type":7,"notice_id":null,"content":"您已设置【【欧诗漫旗舰店】珍珠美白洗面奶100ml】肉单的直播提醒，直播时间2018-09-16 09:30:00","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-16 08:59:40"},{"id":2233,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】珍珠美白洗面奶100ml】直播时间2018-09-15 09:30:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-15 20:55:54"},{"id":2232,"type":7,"notice_id":null,"content":"【男士自动扣皮带商务头层纯牛皮青年】直播时间2018-09-08 19:00:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-15 20:55:42"},{"id":2220,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】直播时间2018-09-14 21:35:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-15 09:04:29"},{"id":2192,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】直播时间0000-00-00 00:00:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-14 20:01:44"},{"id":2191,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】直播时间0000-00-00 00:00:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-14 18:53:40"},{"id":2190,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】直播时间0000-00-00 00:00:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-14 18:53:27"},{"id":2189,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】直播时间0000-00-00 00:00:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-14 18:52:22"},{"id":2188,"type":7,"notice_id":null,"content":"【【欧诗漫旗舰店】玻尿酸水润隐形面膜共40片】直播时间0000-00-00 00:00:00，直播马上开始","to_user_unionid":"oqR1C1j8K1rjF5qE_Uj0QA7xVRE4","to_user_id":8,"is_read":1,"is_delete":0,"create_time":"2018-09-14 18:52:08"}]
         * hasMore : true
         * total : 31
         */

        private boolean hasMore;
        private int total;
        private List<ListBean> list;

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 2456
             * type : 5
             * notice_id : 10
             * content : 关于放假
             * to_user_unionid : oqR1C1j8K1rjF5qE_Uj0QA7xVRE4
             * to_user_id : 8
             * is_read : 1
             * is_delete : 0
             * create_time : 2018-09-20 01:48:10
             */

            private int id;
            private int type;
            private int notice_id;
            private String content;
            private String to_user_unionid;
            private int to_user_id;
            private int is_read;
            private int is_delete;
            private String create_time;

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

            public int getNotice_id() {
                return notice_id;
            }

            public void setNotice_id(int notice_id) {
                this.notice_id = notice_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTo_user_unionid() {
                return to_user_unionid;
            }

            public void setTo_user_unionid(String to_user_unionid) {
                this.to_user_unionid = to_user_unionid;
            }

            public int getTo_user_id() {
                return to_user_id;
            }

            public void setTo_user_id(int to_user_id) {
                this.to_user_id = to_user_id;
            }

            public int getIs_read() {
                return is_read;
            }

            public void setIs_read(int is_read) {
                this.is_read = is_read;
            }

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
