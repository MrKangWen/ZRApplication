package com.zhaorou.zrapplication.home.model;

import com.zhaorou.zrapplication.base.BaseModel;
import java.util.List;

/**
 * 精选
 * @author kang
 */
public class JxListModel  extends BaseModel{

    /**
     * data : {"list":[{"id":550,"user_id":105,"user_unionid":"oqR1C1o7XFP1iBrU40Bw5jAnhSNU","user_name":"Cvans","goods_id":"563316782698","goods_url":"https://item.taobao.com/item.htm?id=563316782698","type":1,"title":"","goods_name":"美国万吉香蕉长颈鹿牙胶婴儿磨牙棒","pic":"https://img.alicdn.com/i3/2779919263/TB23SxGwMmTBuNjy1XbXXaMrVXa_!!2779919263.jpg","market_pic":"uploads/1536216291531mm16868834_600x900.jpg","cid":4,"price":"64.9","price_after_coupons":"24.9","reject_reason":null,"sales":"2369","rate":"30.2","quan_guid_content":"美国万吉，线下同款！食品级软硅胶材质，柔软舒适不伤牙，让宝宝爱不释手，妈妈放心选择，送防掉链，3个月以上适用，可水煮消毒！ ","quan_id":"dd7a6550771b41fa80f70b113987eb39","price_coupons":"40","quan_shengyu":"98993","quan_lingzou":1007,"quan_condition":"0","quan_link":"https://uland.taobao.com/quan/detail?sellerId=2779919263&activityId=dd7a6550771b41fa80f70b113987eb39","coupon_start_time":"2018-09-06 00:00:00","coupon_end_time":"2018-09-13 23:59:59","yongjin_type":null,"yugao_pic":"img/default.jpg","yugao_introd":"","zhibo_pic1":"img/default.jpg","zhibo_pic2":"img/default.jpg","zhibo_pic3":"img/default.jpg","zhibo_introd1":"","zhibo_introd2":"","zhibo_introd3":"","zhibo_time":"0000-00-00 00:00:00","create_time":"2018-09-06 14:45:32","start_time":"2018-09-06 00:00:00","status":1,"coupon_status":1,"is_delete":0,"is_publish":1,"sort":0,"is_friendpop":1,"new_friendpop_num":0,"total_friendpop_num":1,"two_h_volume":0,"reminded":0}],"hasmore":true,"total":189}
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
         * list : [{"id":550,"user_id":105,"user_unionid":"oqR1C1o7XFP1iBrU40Bw5jAnhSNU","user_name":"Cvans","goods_id":"563316782698","goods_url":"https://item.taobao.com/item.htm?id=563316782698","type":1,"title":"","goods_name":"美国万吉香蕉长颈鹿牙胶婴儿磨牙棒","pic":"https://img.alicdn.com/i3/2779919263/TB23SxGwMmTBuNjy1XbXXaMrVXa_!!2779919263.jpg","market_pic":"uploads/1536216291531mm16868834_600x900.jpg","cid":4,"price":"64.9","price_after_coupons":"24.9","reject_reason":null,"sales":"2369","rate":"30.2","quan_guid_content":"美国万吉，线下同款！食品级软硅胶材质，柔软舒适不伤牙，让宝宝爱不释手，妈妈放心选择，送防掉链，3个月以上适用，可水煮消毒！ ","quan_id":"dd7a6550771b41fa80f70b113987eb39","price_coupons":"40","quan_shengyu":"98993","quan_lingzou":1007,"quan_condition":"0","quan_link":"https://uland.taobao.com/quan/detail?sellerId=2779919263&activityId=dd7a6550771b41fa80f70b113987eb39","coupon_start_time":"2018-09-06 00:00:00","coupon_end_time":"2018-09-13 23:59:59","yongjin_type":null,"yugao_pic":"img/default.jpg","yugao_introd":"","zhibo_pic1":"img/default.jpg","zhibo_pic2":"img/default.jpg","zhibo_pic3":"img/default.jpg","zhibo_introd1":"","zhibo_introd2":"","zhibo_introd3":"","zhibo_time":"0000-00-00 00:00:00","create_time":"2018-09-06 14:45:32","start_time":"2018-09-06 00:00:00","status":1,"coupon_status":1,"is_delete":0,"is_publish":1,"sort":0,"is_friendpop":1,"new_friendpop_num":0,"total_friendpop_num":1,"two_h_volume":0,"reminded":0}]
         * hasmore : true
         * total : 189
         */

        private boolean hasmore;
        private int total;
        private List<ListBean> list;

        public boolean isHasmore() {
            return hasmore;
        }

        public void setHasmore(boolean hasmore) {
            this.hasmore = hasmore;
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
             * id : 550
             * user_id : 105
             * user_unionid : oqR1C1o7XFP1iBrU40Bw5jAnhSNU
             * user_name : Cvans
             * goods_id : 563316782698
             * goods_url : https://item.taobao.com/item.htm?id=563316782698
             * type : 1
             * title :
             * goods_name : 美国万吉香蕉长颈鹿牙胶婴儿磨牙棒
             * pic : https://img.alicdn.com/i3/2779919263/TB23SxGwMmTBuNjy1XbXXaMrVXa_!!2779919263.jpg
             * market_pic : uploads/1536216291531mm16868834_600x900.jpg
             * cid : 4
             * price : 64.9
             * price_after_coupons : 24.9
             * reject_reason : null
             * sales : 2369
             * rate : 30.2
             * quan_guid_content : 美国万吉，线下同款！食品级软硅胶材质，柔软舒适不伤牙，让宝宝爱不释手，妈妈放心选择，送防掉链，3个月以上适用，可水煮消毒！
             * quan_id : dd7a6550771b41fa80f70b113987eb39
             * price_coupons : 40
             * quan_shengyu : 98993
             * quan_lingzou : 1007
             * quan_condition : 0
             * quan_link : https://uland.taobao.com/quan/detail?sellerId=2779919263&activityId=dd7a6550771b41fa80f70b113987eb39
             * coupon_start_time : 2018-09-06 00:00:00
             * coupon_end_time : 2018-09-13 23:59:59
             * yongjin_type : null
             * yugao_pic : img/default.jpg
             * yugao_introd :
             * zhibo_pic1 : img/default.jpg
             * zhibo_pic2 : img/default.jpg
             * zhibo_pic3 : img/default.jpg
             * zhibo_introd1 :
             * zhibo_introd2 :
             * zhibo_introd3 :
             * zhibo_time : 0000-00-00 00:00:00
             * create_time : 2018-09-06 14:45:32
             * start_time : 2018-09-06 00:00:00
             * status : 1
             * coupon_status : 1
             * is_delete : 0
             * is_publish : 1
             * sort : 0
             * is_friendpop : 1
             * new_friendpop_num : 0
             * total_friendpop_num : 1
             * two_h_volume : 0
             * reminded : 0
             */

            private int id;
            private int user_id;
            private String user_unionid;
            private String user_name;
            private String goods_id;
            private String goods_url;
            private int type;
            private String title;
            private String goods_name;
            private String pic;
            private String market_pic;
            private int cid;
            private String price;
            private String price_after_coupons;
            private Object reject_reason;
            private String sales;
            private String rate;
            private String quan_guid_content;
            private String quan_id;
            private String price_coupons;
            private String quan_shengyu;
            private int quan_lingzou;
            private String quan_condition;
            private String quan_link;
            private String coupon_start_time;
            private String coupon_end_time;
            private Object yongjin_type;
            private String yugao_pic;
            private String yugao_introd;
            private String zhibo_pic1;
            private String zhibo_pic2;
            private String zhibo_pic3;
            private String zhibo_introd1;
            private String zhibo_introd2;
            private String zhibo_introd3;
            private String zhibo_time;
            private String create_time;
            private String start_time;
            private int status;
            private int coupon_status;
            private int is_delete;
            private int is_publish;
            private int sort;
            private int is_friendpop;
            private int new_friendpop_num;
            private int total_friendpop_num;
            private int two_h_volume;
            private int reminded;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_unionid() {
                return user_unionid;
            }

            public void setUser_unionid(String user_unionid) {
                this.user_unionid = user_unionid;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_url() {
                return goods_url;
            }

            public void setGoods_url(String goods_url) {
                this.goods_url = goods_url;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getMarket_pic() {
                return market_pic;
            }

            public void setMarket_pic(String market_pic) {
                this.market_pic = market_pic;
            }

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPrice_after_coupons() {
                return price_after_coupons;
            }

            public void setPrice_after_coupons(String price_after_coupons) {
                this.price_after_coupons = price_after_coupons;
            }

            public Object getReject_reason() {
                return reject_reason;
            }

            public void setReject_reason(Object reject_reason) {
                this.reject_reason = reject_reason;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getQuan_guid_content() {
                return quan_guid_content;
            }

            public void setQuan_guid_content(String quan_guid_content) {
                this.quan_guid_content = quan_guid_content;
            }

            public String getQuan_id() {
                return quan_id;
            }

            public void setQuan_id(String quan_id) {
                this.quan_id = quan_id;
            }

            public String getPrice_coupons() {
                return price_coupons;
            }

            public void setPrice_coupons(String price_coupons) {
                this.price_coupons = price_coupons;
            }

            public String getQuan_shengyu() {
                return quan_shengyu;
            }

            public void setQuan_shengyu(String quan_shengyu) {
                this.quan_shengyu = quan_shengyu;
            }

            public int getQuan_lingzou() {
                return quan_lingzou;
            }

            public void setQuan_lingzou(int quan_lingzou) {
                this.quan_lingzou = quan_lingzou;
            }

            public String getQuan_condition() {
                return quan_condition;
            }

            public void setQuan_condition(String quan_condition) {
                this.quan_condition = quan_condition;
            }

            public String getQuan_link() {
                return quan_link;
            }

            public void setQuan_link(String quan_link) {
                this.quan_link = quan_link;
            }

            public String getCoupon_start_time() {
                return coupon_start_time;
            }

            public void setCoupon_start_time(String coupon_start_time) {
                this.coupon_start_time = coupon_start_time;
            }

            public String getCoupon_end_time() {
                return coupon_end_time;
            }

            public void setCoupon_end_time(String coupon_end_time) {
                this.coupon_end_time = coupon_end_time;
            }

            public Object getYongjin_type() {
                return yongjin_type;
            }

            public void setYongjin_type(Object yongjin_type) {
                this.yongjin_type = yongjin_type;
            }

            public String getYugao_pic() {
                return yugao_pic;
            }

            public void setYugao_pic(String yugao_pic) {
                this.yugao_pic = yugao_pic;
            }

            public String getYugao_introd() {
                return yugao_introd;
            }

            public void setYugao_introd(String yugao_introd) {
                this.yugao_introd = yugao_introd;
            }

            public String getZhibo_pic1() {
                return zhibo_pic1;
            }

            public void setZhibo_pic1(String zhibo_pic1) {
                this.zhibo_pic1 = zhibo_pic1;
            }

            public String getZhibo_pic2() {
                return zhibo_pic2;
            }

            public void setZhibo_pic2(String zhibo_pic2) {
                this.zhibo_pic2 = zhibo_pic2;
            }

            public String getZhibo_pic3() {
                return zhibo_pic3;
            }

            public void setZhibo_pic3(String zhibo_pic3) {
                this.zhibo_pic3 = zhibo_pic3;
            }

            public String getZhibo_introd1() {
                return zhibo_introd1;
            }

            public void setZhibo_introd1(String zhibo_introd1) {
                this.zhibo_introd1 = zhibo_introd1;
            }

            public String getZhibo_introd2() {
                return zhibo_introd2;
            }

            public void setZhibo_introd2(String zhibo_introd2) {
                this.zhibo_introd2 = zhibo_introd2;
            }

            public String getZhibo_introd3() {
                return zhibo_introd3;
            }

            public void setZhibo_introd3(String zhibo_introd3) {
                this.zhibo_introd3 = zhibo_introd3;
            }

            public String getZhibo_time() {
                return zhibo_time;
            }

            public void setZhibo_time(String zhibo_time) {
                this.zhibo_time = zhibo_time;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getCoupon_status() {
                return coupon_status;
            }

            public void setCoupon_status(int coupon_status) {
                this.coupon_status = coupon_status;
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

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getIs_friendpop() {
                return is_friendpop;
            }

            public void setIs_friendpop(int is_friendpop) {
                this.is_friendpop = is_friendpop;
            }

            public int getNew_friendpop_num() {
                return new_friendpop_num;
            }

            public void setNew_friendpop_num(int new_friendpop_num) {
                this.new_friendpop_num = new_friendpop_num;
            }

            public int getTotal_friendpop_num() {
                return total_friendpop_num;
            }

            public void setTotal_friendpop_num(int total_friendpop_num) {
                this.total_friendpop_num = total_friendpop_num;
            }

            public int getTwo_h_volume() {
                return two_h_volume;
            }

            public void setTwo_h_volume(int two_h_volume) {
                this.two_h_volume = two_h_volume;
            }

            public int getReminded() {
                return reminded;
            }

            public void setReminded(int reminded) {
                this.reminded = reminded;
            }
        }
    }
}
