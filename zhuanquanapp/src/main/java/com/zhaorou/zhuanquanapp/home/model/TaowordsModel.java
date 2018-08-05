package com.zhaorou.zhuanquanapp.home.model;

public class TaowordsModel {

    /**
     * data : {"ulandResult":"https://uland.taobao.com/coupon/edetail?e=znV0NGlHrH8GQASttHIRqRlHSXvamlh8Gfkoq5jZgbahI6XiP692UUG%2Bwu8MwGGQ5jwE%2FGPPvyczVw5Pw9jFfL9fwBwwUiqlT8HYLPNkHcvJnadaiBmiH27PVn13QcLN%2FfsIcQx%2BbXWexlMzA9F8yQ%3D%3D&traceId=0bfaef1515316642367251269e&from=tool&sight=czhk","ulandCode":0,"ulandData":{"category_id":50025705,"coupon_click_url":"https://uland.taobao.com/coupon/edetail?e=znV0NGlHrH8GQASttHIRqRlHSXvamlh8Gfkoq5jZgbahI6XiP692UUG%2Bwu8MwGGQ5jwE%2FGPPvyczVw5Pw9jFfL9fwBwwUiqlT8HYLPNkHcvJnadaiBmiH27PVn13QcLN%2FfsIcQx%2BbXWexlMzA9F8yQ%3D%3D&traceId=0bfaef1515316642367251269e","coupon_end_time":"2018-07-15","coupon_info":"满36元减30元","coupon_remain_count":25800,"coupon_start_time":"2018-07-15","coupon_total_count":30000,"coupon_type":3,"item_id":545609591550,"max_commission_rate":"50.00"},"maxRate":"50.00%","tkl":"￥87YSbZaoE9h￥","tklData":{"data":{"model":"￥87YSbZaoE9h￥"},"request_id":"1475l3oivi5ym"}}
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
         * ulandResult : https://uland.taobao.com/coupon/edetail?e=znV0NGlHrH8GQASttHIRqRlHSXvamlh8Gfkoq5jZgbahI6XiP692UUG%2Bwu8MwGGQ5jwE%2FGPPvyczVw5Pw9jFfL9fwBwwUiqlT8HYLPNkHcvJnadaiBmiH27PVn13QcLN%2FfsIcQx%2BbXWexlMzA9F8yQ%3D%3D&traceId=0bfaef1515316642367251269e&from=tool&sight=czhk
         * ulandCode : 0
         * ulandData : {"category_id":50025705,"coupon_click_url":"https://uland.taobao.com/coupon/edetail?e=znV0NGlHrH8GQASttHIRqRlHSXvamlh8Gfkoq5jZgbahI6XiP692UUG%2Bwu8MwGGQ5jwE%2FGPPvyczVw5Pw9jFfL9fwBwwUiqlT8HYLPNkHcvJnadaiBmiH27PVn13QcLN%2FfsIcQx%2BbXWexlMzA9F8yQ%3D%3D&traceId=0bfaef1515316642367251269e","coupon_end_time":"2018-07-15","coupon_info":"满36元减30元","coupon_remain_count":25800,"coupon_start_time":"2018-07-15","coupon_total_count":30000,"coupon_type":3,"item_id":545609591550,"max_commission_rate":"50.00"}
         * maxRate : 50.00%
         * tkl : ￥87YSbZaoE9h￥
         * tklData : {"data":{"model":"￥87YSbZaoE9h￥"},"request_id":"1475l3oivi5ym"}
         */

        private String ulandResult;
        private int ulandCode;
        private UlandDataBean ulandData;
        private String maxRate;
        private String tkl;
        private TklDataBean tklData;

        public String getUlandResult() {
            return ulandResult;
        }

        public void setUlandResult(String ulandResult) {
            this.ulandResult = ulandResult;
        }

        public int getUlandCode() {
            return ulandCode;
        }

        public void setUlandCode(int ulandCode) {
            this.ulandCode = ulandCode;
        }

        public UlandDataBean getUlandData() {
            return ulandData;
        }

        public void setUlandData(UlandDataBean ulandData) {
            this.ulandData = ulandData;
        }

        public String getMaxRate() {
            return maxRate;
        }

        public void setMaxRate(String maxRate) {
            this.maxRate = maxRate;
        }

        public String getTkl() {
            return tkl;
        }

        public void setTkl(String tkl) {
            this.tkl = tkl;
        }

        public TklDataBean getTklData() {
            return tklData;
        }

        public void setTklData(TklDataBean tklData) {
            this.tklData = tklData;
        }

        public static class UlandDataBean {
            /**
             * category_id : 50025705
             * coupon_click_url : https://uland.taobao.com/coupon/edetail?e=znV0NGlHrH8GQASttHIRqRlHSXvamlh8Gfkoq5jZgbahI6XiP692UUG%2Bwu8MwGGQ5jwE%2FGPPvyczVw5Pw9jFfL9fwBwwUiqlT8HYLPNkHcvJnadaiBmiH27PVn13QcLN%2FfsIcQx%2BbXWexlMzA9F8yQ%3D%3D&traceId=0bfaef1515316642367251269e
             * coupon_end_time : 2018-07-15
             * coupon_info : 满36元减30元
             * coupon_remain_count : 25800
             * coupon_start_time : 2018-07-15
             * coupon_total_count : 30000
             * coupon_type : 3
             * item_id : 545609591550
             * max_commission_rate : 50.00
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

        public static class TklDataBean {
            /**
             * data : {"model":"￥87YSbZaoE9h￥"}
             * request_id : 1475l3oivi5ym
             */

            private DataBean data;
            private String request_id;

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public String getRequest_id() {
                return request_id;
            }

            public void setRequest_id(String request_id) {
                this.request_id = request_id;
            }

            public static class DataBean {
                /**
                 * model : ￥87YSbZaoE9h￥
                 */

                private String model;

                public String getModel() {
                    return model;
                }

                public void setModel(String model) {
                    this.model = model;
                }
            }
        }
    }
}
