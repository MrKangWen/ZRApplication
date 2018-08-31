package com.zhaorou.zrapplication.home.model;

public class TaowordsModel {


    /**
     * data : {"tkl":"￥t0nIbdxOhuz￥","request_id":"64fs5oxhwzdx","gao_yong_result":{"data":{"category_id":1625,"coupon_click_url":"https://uland.taobao.com/coupon/edetail?e=ST2YD2A4l/UGQASttHIRqbieIflL4PaxXvY4FzDFTuXgyscmWOOQfk/NNXQre6ZReLbq8R+/QE+ve5tv7VhHxpQ5wfGz/u+NLoaTkhkC4nBdiwTmD3eVNiZ6Y/pkHtT5QS0Flu/fbSovkBQlP112cJ5ECHpSy25Ge6L+f9DtnlWRa28MCMEpFiMMIHEJMzAA&traceId=0b8834ed15357213236354198e","coupon_end_time":"2018-09-02","coupon_info":"满28元减15元","coupon_remain_count":43000,"coupon_start_time":"2018-08-31","coupon_total_count":100000,"coupon_type":3,"item_id":575391771551,"max_commission_rate":"30.00"}}}
     * code : 200
     */

    private DataBeanX data;
    private int code;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBeanX {
        /**
         * tkl : ￥t0nIbdxOhuz￥
         * request_id : 64fs5oxhwzdx
         * gao_yong_result : {"data":{"category_id":1625,"coupon_click_url":"https://uland.taobao.com/coupon/edetail?e=ST2YD2A4l/UGQASttHIRqbieIflL4PaxXvY4FzDFTuXgyscmWOOQfk/NNXQre6ZReLbq8R+/QE+ve5tv7VhHxpQ5wfGz/u+NLoaTkhkC4nBdiwTmD3eVNiZ6Y/pkHtT5QS0Flu/fbSovkBQlP112cJ5ECHpSy25Ge6L+f9DtnlWRa28MCMEpFiMMIHEJMzAA&traceId=0b8834ed15357213236354198e","coupon_end_time":"2018-09-02","coupon_info":"满28元减15元","coupon_remain_count":43000,"coupon_start_time":"2018-08-31","coupon_total_count":100000,"coupon_type":3,"item_id":575391771551,"max_commission_rate":"30.00"}}
         */

        private String tkl;
        private String request_id;
        private GaoYongResultBean gao_yong_result;

        public String getTkl() {
            return tkl;
        }

        public void setTkl(String tkl) {
            this.tkl = tkl;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public GaoYongResultBean getGao_yong_result() {
            return gao_yong_result;
        }

        public void setGao_yong_result(GaoYongResultBean gao_yong_result) {
            this.gao_yong_result = gao_yong_result;
        }

        public static class GaoYongResultBean {
            /**
             * data : {"category_id":1625,"coupon_click_url":"https://uland.taobao.com/coupon/edetail?e=ST2YD2A4l/UGQASttHIRqbieIflL4PaxXvY4FzDFTuXgyscmWOOQfk/NNXQre6ZReLbq8R+/QE+ve5tv7VhHxpQ5wfGz/u+NLoaTkhkC4nBdiwTmD3eVNiZ6Y/pkHtT5QS0Flu/fbSovkBQlP112cJ5ECHpSy25Ge6L+f9DtnlWRa28MCMEpFiMMIHEJMzAA&traceId=0b8834ed15357213236354198e","coupon_end_time":"2018-09-02","coupon_info":"满28元减15元","coupon_remain_count":43000,"coupon_start_time":"2018-08-31","coupon_total_count":100000,"coupon_type":3,"item_id":575391771551,"max_commission_rate":"30.00"}
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
                 * category_id : 1625
                 * coupon_click_url : https://uland.taobao.com/coupon/edetail?e=ST2YD2A4l/UGQASttHIRqbieIflL4PaxXvY4FzDFTuXgyscmWOOQfk/NNXQre6ZReLbq8R+/QE+ve5tv7VhHxpQ5wfGz/u+NLoaTkhkC4nBdiwTmD3eVNiZ6Y/pkHtT5QS0Flu/fbSovkBQlP112cJ5ECHpSy25Ge6L+f9DtnlWRa28MCMEpFiMMIHEJMzAA&traceId=0b8834ed15357213236354198e
                 * coupon_end_time : 2018-09-02
                 * coupon_info : 满28元减15元
                 * coupon_remain_count : 43000
                 * coupon_start_time : 2018-08-31
                 * coupon_total_count : 100000
                 * coupon_type : 3
                 * item_id : 575391771551
                 * max_commission_rate : 30.00
                 */

                private int category_id;
                private String coupon_click_url;
                private String coupon_end_time;
                private String coupon_info;
                private int coupon_remain_count;
                private String coupon_start_time;
                private int coupon_total_count;
                private int coupon_type;
                private long item_id;
                private String max_commission_rate;

                public int getCategory_id() {
                    return category_id;
                }

                public void setCategory_id(int category_id) {
                    this.category_id = category_id;
                }

                public String getCoupon_click_url() {
                    return coupon_click_url;
                }

                public void setCoupon_click_url(String coupon_click_url) {
                    this.coupon_click_url = coupon_click_url;
                }

                public String getCoupon_end_time() {
                    return coupon_end_time;
                }

                public void setCoupon_end_time(String coupon_end_time) {
                    this.coupon_end_time = coupon_end_time;
                }

                public String getCoupon_info() {
                    return coupon_info;
                }

                public void setCoupon_info(String coupon_info) {
                    this.coupon_info = coupon_info;
                }

                public int getCoupon_remain_count() {
                    return coupon_remain_count;
                }

                public void setCoupon_remain_count(int coupon_remain_count) {
                    this.coupon_remain_count = coupon_remain_count;
                }

                public String getCoupon_start_time() {
                    return coupon_start_time;
                }

                public void setCoupon_start_time(String coupon_start_time) {
                    this.coupon_start_time = coupon_start_time;
                }

                public int getCoupon_total_count() {
                    return coupon_total_count;
                }

                public void setCoupon_total_count(int coupon_total_count) {
                    this.coupon_total_count = coupon_total_count;
                }

                public int getCoupon_type() {
                    return coupon_type;
                }

                public void setCoupon_type(int coupon_type) {
                    this.coupon_type = coupon_type;
                }

                public long getItem_id() {
                    return item_id;
                }

                public void setItem_id(long item_id) {
                    this.item_id = item_id;
                }

                public String getMax_commission_rate() {
                    return max_commission_rate;
                }

                public void setMax_commission_rate(String max_commission_rate) {
                    this.max_commission_rate = max_commission_rate;
                }
            }
        }
    }
}
