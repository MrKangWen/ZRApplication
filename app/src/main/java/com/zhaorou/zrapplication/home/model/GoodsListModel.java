package com.zhaorou.zrapplication.home.model;

import java.util.List;

public class GoodsListModel {


    /**
     * code : 200
     * data : {"list":[{"id":"6878285","goods_name":"俏十岁多效净透洁肤水清洁脸部眼唇卸妆水保湿爽肤300ml","goods_id":"559954083277","cate_id":"5","cate_name":"美妆","goods_url":"https://item.taobao.com/item.htm?id=559954083277","pic":"http://img.alicdn.com/imgextra/i1/2484618684/TB2mrzZkFooBKNjSZPhXXc2CXXa_!!2484618684.jpg","video_url":"","price":"138.00","price_after_coupons":"48.00","price_coupons":"90.00","sales":"17","rate":"30.00","commission_type":"3","commission_type_name":"鹊桥","quan_link":"https://uland.taobao.com/quan/detail?sellerId=1717237408&activityId=36c66a8687264888b639c8d9aca110cf","seller_id":"","quan_id":"","quan_shengyu":"0","quan_zhong":"20000","quan_ling":"0","quan_expired_time":"2018-07-15 23:59:59","quan_note":"138","quan_guid_content":"京东158元！新品上市，药食同源，国家发明专利证书，植物精萃很温和，卸妆、清洁、爽肤三效合一，小仙女秀出你容颜【赠运费险】","quan_qq_img":"http://images.jbkuan.com/20180714/e0d9497ef856c3b3c948cace54e97146.jpg_250x250.jpg","src":"1","jhs":"1","tqg":"0","gold":"0","freight":"0","abroad":"0","flagship":"1","total_friendpop_num":0,"new_friendpop_num":0,"is_friendpop":0},{"id":"6878178","goods_name":"FILA斐乐手表女男士新款防水时尚潮流官方正品情侣皮腕表dw778","goods_id":"521034825986","cate_id":"7","cate_name":"鞋包服饰","goods_url":"https://item.taobao.com/item.htm?id=521034825986","pic":"http://img.alicdn.com/imgextra/i3/2104519904/TB2zIvHcrYI8KJjy0FaXXbAiVXa_!!2104519904.jpg","video_url":"","price":"419.00","price_after_coupons":"339.00","price_coupons":"80.00","sales":"272","rate":"20.10","commission_type":"3","commission_type_name":"鹊桥","quan_link":"https://uland.taobao.com/quan/detail?sellerId=2104519904&activityId=1da3f889aa2a40e1be990fa8f6a65893","seller_id":"","quan_id":"","quan_shengyu":"0","quan_zhong":"10000","quan_ling":"0","quan_expired_time":"2018-07-17 23:59:59","quan_note":"400","quan_guid_content":"【斐乐】明星同款！情侣手表，触感光滑，防水，耐腐蚀，雕刻品牌LOGO标识，送给恋人的腕表，彰显品质~简单双针，品位慢时光！【赠运费险】","quan_qq_img":"http://images.jbkuan.com/20180714/7ed5a875211ccbf23c65edd1e51aa7cf.jpg_250x250.jpg","src":"1","jhs":"1","tqg":"0","gold":"0","freight":"0","abroad":"0","flagship":"1","total_friendpop_num":0,"new_friendpop_num":0,"is_friendpop":0}]}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> list;
        private String hasmore;

        public String getHasmore() {
            return hasmore;
        }

        public void setHasmore(String hasmore) {
            this.hasmore = hasmore;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 6878285
             * goods_name : 俏十岁多效净透洁肤水清洁脸部眼唇卸妆水保湿爽肤300ml
             * goods_id : 559954083277
             * cate_id : 5
             * cate_name : 美妆
             * goods_url : https://item.taobao.com/item.htm?id=559954083277
             * pic : http://img.alicdn.com/imgextra/i1/2484618684/TB2mrzZkFooBKNjSZPhXXc2CXXa_!!2484618684.jpg
             * video_url :
             * price : 138.00
             * price_after_coupons : 48.00
             * price_coupons : 90.00
             * sales : 17
             * rate : 30.00
             * commission_type : 3
             * commission_type_name : 鹊桥
             * quan_link : https://uland.taobao.com/quan/detail?sellerId=1717237408&activityId=36c66a8687264888b639c8d9aca110cf
             * seller_id :
             * quan_id :
             * quan_shengyu : 0
             * quan_zhong : 20000
             * quan_ling : 0
             * quan_expired_time : 2018-07-15 23:59:59
             * quan_note : 138
             * quan_guid_content : 京东158元！新品上市，药食同源，国家发明专利证书，植物精萃很温和，卸妆、清洁、爽肤三效合一，小仙女秀出你容颜【赠运费险】
             * quan_qq_img : http://images.jbkuan.com/20180714/e0d9497ef856c3b3c948cace54e97146.jpg_250x250.jpg
             * src : 1
             * jhs : 1
             * tqg : 0
             * gold : 0
             * freight : 0
             * abroad : 0
             * flagship : 1
             * total_friendpop_num : 0
             * new_friendpop_num : 0
             * is_friendpop : 0
             */

            private String id;
            private String goods_name;
            private String goods_id;
            private String cate_id;
            private String cate_name;
            private String goods_url;
            private String pic;
            private String video_url;
            private String price;
            private String price_after_coupons;
            private String price_coupons;
            private String sales;
            private String rate;
            private String commission_type;
            private String commission_type_name;
            private String quan_link;
            private String seller_id;
            private String quan_id;
            private String quan_shengyu;
            private String quan_zhong;
            private String quan_ling;
            private String quan_expired_time;
            private String quan_note;
            private String quan_guid_content;
            private String quan_qq_img;
            private String src;
            private String jhs;
            private String tqg;
            private String gold;
            private String freight;
            private String abroad;
            private String flagship;
            private int total_friendpop_num;
            private int new_friendpop_num;
            private int is_friendpop;
            private int keyid;
            private int ID;
            private int Cid;
            private String Title;
            private String IsTmall;
            private String Dsr;
            private String SellerID;
            private String Commission;
            private String Commission_queqiao;
            private String Jihua_link;
            private String Jihua_shenhe;
            private String Introduce;
            private String Que_siteid;
            private String Quan_time;
            private String Quan_receive;
            private String Quan_condition;
            private String Quan_link;
            private String Quan_m_link;
            private String Yongjin_type;
            private String ali_click;
            private String create_time;
            private String is_delete;
            private String type;
            private String sort;
            private String tao_pwd;
            private FriendPopDetailModel.DataBean.EntityBean friendPopDetailModel;

            public FriendPopDetailModel.DataBean.EntityBean getFriendPopDetailModel() {
                return friendPopDetailModel;
            }

            public void setFriendPopDetailModel(FriendPopDetailModel.DataBean.EntityBean friendPopDetailModel) {
                this.friendPopDetailModel = friendPopDetailModel;
            }

            public int getKeyid() {
                return keyid;
            }

            public void setKeyid(int keyid) {
                this.keyid = keyid;
            }

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String title) {
                Title = title;
            }

            public int getCid() {
                return Cid;
            }

            public void setCid(int cid) {
                Cid = cid;
            }

            public String getIsTmall() {
                return IsTmall;
            }

            public void setIsTmall(String isTmall) {
                IsTmall = isTmall;
            }

            public String getDsr() {
                return Dsr;
            }

            public void setDsr(String dsr) {
                Dsr = dsr;
            }

            public String getSellerID() {
                return SellerID;
            }

            public void setSellerID(String sellerID) {
                SellerID = sellerID;
            }

            public String getCommission() {
                return Commission;
            }

            public void setCommission(String commission) {
                Commission = commission;
            }

            public String getCommission_queqiao() {
                return Commission_queqiao;
            }

            public void setCommission_queqiao(String commission_queqiao) {
                Commission_queqiao = commission_queqiao;
            }

            public String getJihua_link() {
                return Jihua_link;
            }

            public void setJihua_link(String jihua_link) {
                Jihua_link = jihua_link;
            }

            public String getJihua_shenhe() {
                return Jihua_shenhe;
            }

            public void setJihua_shenhe(String jihua_shenhe) {
                Jihua_shenhe = jihua_shenhe;
            }

            public String getIntroduce() {
                return Introduce;
            }

            public void setIntroduce(String introduce) {
                Introduce = introduce;
            }

            public String getQue_siteid() {
                return Que_siteid;
            }

            public void setQue_siteid(String que_siteid) {
                Que_siteid = que_siteid;
            }

            public String getQuan_time() {
                return Quan_time;
            }

            public void setQuan_time(String quan_time) {
                Quan_time = quan_time;
            }

            public String getQuan_receive() {
                return Quan_receive;
            }

            public void setQuan_receive(String quan_receive) {
                Quan_receive = quan_receive;
            }

            public String getQuan_condition() {
                return Quan_condition;
            }

            public void setQuan_condition(String quan_condition) {
                Quan_condition = quan_condition;
            }

            public String getQuan_m_link() {
                return Quan_m_link;
            }

            public void setQuan_m_link(String quan_m_link) {
                Quan_m_link = quan_m_link;
            }

            public String getYongjin_type() {
                return Yongjin_type;
            }

            public void setYongjin_type(String yongjin_type) {
                Yongjin_type = yongjin_type;
            }

            public String getAli_click() {
                return ali_click;
            }

            public void setAli_click(String ali_click) {
                this.ali_click = ali_click;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(String is_delete) {
                this.is_delete = is_delete;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getTao_pwd() {
                return tao_pwd;
            }

            public void setTao_pwd(String tao_pwd) {
                this.tao_pwd = tao_pwd;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getCate_id() {
                return cate_id;
            }

            public void setCate_id(String cate_id) {
                this.cate_id = cate_id;
            }

            public String getCate_name() {
                return cate_name;
            }

            public void setCate_name(String cate_name) {
                this.cate_name = cate_name;
            }

            public String getGoods_url() {
                return goods_url;
            }

            public void setGoods_url(String goods_url) {
                this.goods_url = goods_url;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getVideo_url() {
                return video_url;
            }

            public void setVideo_url(String video_url) {
                this.video_url = video_url;
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

            public String getPrice_coupons() {
                return price_coupons;
            }

            public void setPrice_coupons(String price_coupons) {
                this.price_coupons = price_coupons;
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

            public String getCommission_type() {
                return commission_type;
            }

            public void setCommission_type(String commission_type) {
                this.commission_type = commission_type;
            }

            public String getCommission_type_name() {
                return commission_type_name;
            }

            public void setCommission_type_name(String commission_type_name) {
                this.commission_type_name = commission_type_name;
            }

            public String getQuan_link() {
                return quan_link;
            }

            public void setQuan_link(String quan_link) {
                this.quan_link = quan_link;
            }

            public String getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(String seller_id) {
                this.seller_id = seller_id;
            }

            public String getQuan_id() {
                return quan_id;
            }

            public void setQuan_id(String quan_id) {
                this.quan_id = quan_id;
            }

            public String getQuan_shengyu() {
                return quan_shengyu;
            }

            public void setQuan_shengyu(String quan_shengyu) {
                this.quan_shengyu = quan_shengyu;
            }

            public String getQuan_zhong() {
                return quan_zhong;
            }

            public void setQuan_zhong(String quan_zhong) {
                this.quan_zhong = quan_zhong;
            }

            public String getQuan_ling() {
                return quan_ling;
            }

            public void setQuan_ling(String quan_ling) {
                this.quan_ling = quan_ling;
            }

            public String getQuan_expired_time() {
                return quan_expired_time;
            }

            public void setQuan_expired_time(String quan_expired_time) {
                this.quan_expired_time = quan_expired_time;
            }

            public String getQuan_note() {
                return quan_note;
            }

            public void setQuan_note(String quan_note) {
                this.quan_note = quan_note;
            }

            public String getQuan_guid_content() {
                return quan_guid_content;
            }

            public void setQuan_guid_content(String quan_guid_content) {
                this.quan_guid_content = quan_guid_content;
            }

            public String getQuan_qq_img() {
                return quan_qq_img;
            }

            public void setQuan_qq_img(String quan_qq_img) {
                this.quan_qq_img = quan_qq_img;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getJhs() {
                return jhs;
            }

            public void setJhs(String jhs) {
                this.jhs = jhs;
            }

            public String getTqg() {
                return tqg;
            }

            public void setTqg(String tqg) {
                this.tqg = tqg;
            }

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getAbroad() {
                return abroad;
            }

            public void setAbroad(String abroad) {
                this.abroad = abroad;
            }

            public String getFlagship() {
                return flagship;
            }

            public void setFlagship(String flagship) {
                this.flagship = flagship;
            }

            public int getTotal_friendpop_num() {
                return total_friendpop_num;
            }

            public void setTotal_friendpop_num(int total_friendpop_num) {
                this.total_friendpop_num = total_friendpop_num;
            }

            public int getNew_friendpop_num() {
                return new_friendpop_num;
            }

            public void setNew_friendpop_num(int new_friendpop_num) {
                this.new_friendpop_num = new_friendpop_num;
            }

            public int getIs_friendpop() {
                return is_friendpop;
            }

            public void setIs_friendpop(int is_friendpop) {
                this.is_friendpop = is_friendpop;
            }
        }
    }
}
