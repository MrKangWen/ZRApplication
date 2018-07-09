package com.zhaorou.zrapplication.home.model;

import java.util.List;

public class DtkGoodsListModel {

    /**
     * data : {"list":[{"keyid":727901,"ID":12935871,"goods_id":"572237466961","Title":"水果茶纯果干新鲜手工花果茶果粒茶组合袋装网红水果片果干花茶包","goods_name":"新鲜手工水果茶果干8包*盒60克","pic":"https://img.alicdn.com/imgextra/i4/3012913363/TB2qjrHu98YBeNkSnb4XXaevFXa_!!3012913363.jpg","Cid":6,"price":49.9,"price_after_coupons":9.9,"IsTmall":null,"sales":12960,"Dsr":null,"SellerID":null,"Commission":null,"rate":30.01,"Commission_queqiao":null,"Jihua_link":null,"Jihua_shenhe":null,"Introduce":null,"Que_siteid":null,"quan_id":"d8bc63862b24423e9b036b1e245a7f51","price_coupons":40,"Quan_time":null,"quan_shengyu":61000,"Quan_receive":0,"Quan_condition":null,"Quan_link":null,"Quan_m_link":null,"Yongjin_type":null,"ali_click":null,"create_time":"2018-07-09 23:50:10","is_delete":0,"type":null,"sort":0,"tao_pwd":null,"is_friendpop":0,"new_friendpop_num":0,"total_friendpop_num":0},{"keyid":727902,"ID":12977752,"goods_id":"567620520836","Title":"【8支装】牛牛牙刷 儿童3-6-12岁细软毛护龈小刷头 可爱卡通手柄","goods_name":"【8支装】可爱卡通手柄细软毛护龈小刷头","pic":"https://img.alicdn.com/imgextra/i1/2271681157/TB2lBs8CFmWBuNjSspdXXbugXXa_!!2271681157.jpg","Cid":4,"price":29.9,"price_after_coupons":19.9,"IsTmall":null,"sales":3509,"Dsr":null,"SellerID":null,"Commission":null,"rate":20,"Commission_queqiao":null,"Jihua_link":null,"Jihua_shenhe":null,"Introduce":null,"Que_siteid":null,"quan_id":"dc3d78268915405eb8b9e290afb768c4","price_coupons":10,"Quan_time":null,"quan_shengyu":30000,"Quan_receive":0,"Quan_condition":null,"Quan_link":null,"Quan_m_link":null,"Yongjin_type":null,"ali_click":null,"create_time":"2018-07-09 23:50:10","is_delete":0,"type":null,"sort":1,"tao_pwd":null,"is_friendpop":0,"new_friendpop_num":0,"total_friendpop_num":0}]}
     * code : 10
     */

    private DataBean data;
    private int code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * keyid : 727901
             * ID : 12935871
             * goods_id : 572237466961
             * Title : 水果茶纯果干新鲜手工花果茶果粒茶组合袋装网红水果片果干花茶包
             * goods_name : 新鲜手工水果茶果干8包*盒60克
             * pic : https://img.alicdn.com/imgextra/i4/3012913363/TB2qjrHu98YBeNkSnb4XXaevFXa_!!3012913363.jpg
             * Cid : 6
             * price : 49.9
             * price_after_coupons : 9.9
             * IsTmall : null
             * sales : 12960
             * Dsr : null
             * SellerID : null
             * Commission : null
             * rate : 30.01
             * Commission_queqiao : null
             * Jihua_link : null
             * Jihua_shenhe : null
             * Introduce : null
             * Que_siteid : null
             * quan_id : d8bc63862b24423e9b036b1e245a7f51
             * price_coupons : 40
             * Quan_time : null
             * quan_shengyu : 61000
             * Quan_receive : 0
             * Quan_condition : null
             * Quan_link : null
             * Quan_m_link : null
             * Yongjin_type : null
             * ali_click : null
             * create_time : 2018-07-09 23:50:10
             * is_delete : 0
             * type : null
             * sort : 0
             * tao_pwd : null
             * is_friendpop : 0
             * new_friendpop_num : 0
             * total_friendpop_num : 0
             */

            private int keyid;
            private int ID;
            private String goods_id;
            private String Title;
            private String goods_name;
            private String pic;
            private int Cid;
            private double price;
            private double price_after_coupons;
            private Object IsTmall;
            private int sales;
            private Object Dsr;
            private Object SellerID;
            private Object Commission;
            private double rate;
            private Object Commission_queqiao;
            private Object Jihua_link;
            private Object Jihua_shenhe;
            private Object Introduce;
            private Object Que_siteid;
            private String quan_id;
            private int price_coupons;
            private Object Quan_time;
            private int quan_shengyu;
            private int Quan_receive;
            private Object Quan_condition;
            private Object Quan_link;
            private Object Quan_m_link;
            private Object Yongjin_type;
            private Object ali_click;
            private String create_time;
            private int is_delete;
            private Object type;
            private int sort;
            private Object tao_pwd;
            private int is_friendpop;
            private int new_friendpop_num;
            private int total_friendpop_num;

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

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
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

            public int getCid() {
                return Cid;
            }

            public void setCid(int Cid) {
                this.Cid = Cid;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getPrice_after_coupons() {
                return price_after_coupons;
            }

            public void setPrice_after_coupons(double price_after_coupons) {
                this.price_after_coupons = price_after_coupons;
            }

            public Object getIsTmall() {
                return IsTmall;
            }

            public void setIsTmall(Object IsTmall) {
                this.IsTmall = IsTmall;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public Object getDsr() {
                return Dsr;
            }

            public void setDsr(Object Dsr) {
                this.Dsr = Dsr;
            }

            public Object getSellerID() {
                return SellerID;
            }

            public void setSellerID(Object SellerID) {
                this.SellerID = SellerID;
            }

            public Object getCommission() {
                return Commission;
            }

            public void setCommission(Object Commission) {
                this.Commission = Commission;
            }

            public double getRate() {
                return rate;
            }

            public void setRate(double rate) {
                this.rate = rate;
            }

            public Object getCommission_queqiao() {
                return Commission_queqiao;
            }

            public void setCommission_queqiao(Object Commission_queqiao) {
                this.Commission_queqiao = Commission_queqiao;
            }

            public Object getJihua_link() {
                return Jihua_link;
            }

            public void setJihua_link(Object Jihua_link) {
                this.Jihua_link = Jihua_link;
            }

            public Object getJihua_shenhe() {
                return Jihua_shenhe;
            }

            public void setJihua_shenhe(Object Jihua_shenhe) {
                this.Jihua_shenhe = Jihua_shenhe;
            }

            public Object getIntroduce() {
                return Introduce;
            }

            public void setIntroduce(Object Introduce) {
                this.Introduce = Introduce;
            }

            public Object getQue_siteid() {
                return Que_siteid;
            }

            public void setQue_siteid(Object Que_siteid) {
                this.Que_siteid = Que_siteid;
            }

            public String getQuan_id() {
                return quan_id;
            }

            public void setQuan_id(String quan_id) {
                this.quan_id = quan_id;
            }

            public int getPrice_coupons() {
                return price_coupons;
            }

            public void setPrice_coupons(int price_coupons) {
                this.price_coupons = price_coupons;
            }

            public Object getQuan_time() {
                return Quan_time;
            }

            public void setQuan_time(Object Quan_time) {
                this.Quan_time = Quan_time;
            }

            public int getQuan_shengyu() {
                return quan_shengyu;
            }

            public void setQuan_shengyu(int quan_shengyu) {
                this.quan_shengyu = quan_shengyu;
            }

            public int getQuan_receive() {
                return Quan_receive;
            }

            public void setQuan_receive(int Quan_receive) {
                this.Quan_receive = Quan_receive;
            }

            public Object getQuan_condition() {
                return Quan_condition;
            }

            public void setQuan_condition(Object Quan_condition) {
                this.Quan_condition = Quan_condition;
            }

            public Object getQuan_link() {
                return Quan_link;
            }

            public void setQuan_link(Object Quan_link) {
                this.Quan_link = Quan_link;
            }

            public Object getQuan_m_link() {
                return Quan_m_link;
            }

            public void setQuan_m_link(Object Quan_m_link) {
                this.Quan_m_link = Quan_m_link;
            }

            public Object getYongjin_type() {
                return Yongjin_type;
            }

            public void setYongjin_type(Object Yongjin_type) {
                this.Yongjin_type = Yongjin_type;
            }

            public Object getAli_click() {
                return ali_click;
            }

            public void setAli_click(Object ali_click) {
                this.ali_click = ali_click;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public Object getTao_pwd() {
                return tao_pwd;
            }

            public void setTao_pwd(Object tao_pwd) {
                this.tao_pwd = tao_pwd;
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
        }
    }
}
