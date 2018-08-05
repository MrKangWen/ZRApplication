package com.zhaorou.zhuanquanapp.user.model;

import java.util.List;

public class UserInfoModel {

    /**
     * data : {"user":{"id":7,"openid":"onW_y1FMnFWG3xB3RiAPoEXiDuZU","subscribe":null,"nickname":"坤","sex":1,"city":"厦门","country":"中国","province":"福建","language":"zh_CN","headimgurl":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJDLicbdiao8d0jicFmY4o2UHiawmXZU54KUJf3a2nZyFoscjobkCNk0zDNnXIIPyqbOuGsq3Jc9gQLLQ/132","subscribe_time":null,"unionid":"oqR1C1iKMB0eks1DVTJcfIe6A-9Y","privilege":"","remark":null,"groupid":null,"tagid_list":null,"subscribe_scene":null,"qr_scene":null,"qr_scene_str":null,"create_time":"2018-07-12 20:11:23","pid":"mm_15924044_13620115_54418631","adopt_friendpop_num":0,"total_friendpop_num":0,"add_goods_num":0,"password":null,"telephone":null,"score":0,"tao_session":"70000100e089719266f8149ef40decf8d332df80372d27a61729f031d6136b0ad449359387898871","tkl_type":"1","is_delete":{"type":"Buffer","data":[0]}}}
     * code : 200
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
        /**
         * user : {"id":7,"openid":"onW_y1FMnFWG3xB3RiAPoEXiDuZU","subscribe":null,"nickname":"坤","sex":1,"city":"厦门","country":"中国","province":"福建","language":"zh_CN","headimgurl":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJDLicbdiao8d0jicFmY4o2UHiawmXZU54KUJf3a2nZyFoscjobkCNk0zDNnXIIPyqbOuGsq3Jc9gQLLQ/132","subscribe_time":null,"unionid":"oqR1C1iKMB0eks1DVTJcfIe6A-9Y","privilege":"","remark":null,"groupid":null,"tagid_list":null,"subscribe_scene":null,"qr_scene":null,"qr_scene_str":null,"create_time":"2018-07-12 20:11:23","pid":"mm_15924044_13620115_54418631","adopt_friendpop_num":0,"total_friendpop_num":0,"add_goods_num":0,"password":null,"telephone":null,"score":0,"tao_session":"70000100e089719266f8149ef40decf8d332df80372d27a61729f031d6136b0ad449359387898871","tkl_type":"1","is_delete":{"type":"Buffer","data":[0]}}
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 7
             * openid : onW_y1FMnFWG3xB3RiAPoEXiDuZU
             * subscribe : null
             * nickname : 坤
             * sex : 1
             * city : 厦门
             * country : 中国
             * province : 福建
             * language : zh_CN
             * headimgurl : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJDLicbdiao8d0jicFmY4o2UHiawmXZU54KUJf3a2nZyFoscjobkCNk0zDNnXIIPyqbOuGsq3Jc9gQLLQ/132
             * subscribe_time : null
             * unionid : oqR1C1iKMB0eks1DVTJcfIe6A-9Y
             * privilege :
             * remark : null
             * groupid : null
             * tagid_list : null
             * subscribe_scene : null
             * qr_scene : null
             * qr_scene_str : null
             * create_time : 2018-07-12 20:11:23
             * pid : mm_15924044_13620115_54418631
             * adopt_friendpop_num : 0
             * total_friendpop_num : 0
             * add_goods_num : 0
             * password : null
             * telephone : null
             * score : 0
             * tao_session : 70000100e089719266f8149ef40decf8d332df80372d27a61729f031d6136b0ad449359387898871
             * tkl_type : 1
             * is_delete : {"type":"Buffer","data":[0]}
             */

            private int id;
            private String openid;
            private Object subscribe;
            private String nickname;
            private int sex;
            private String city;
            private String country;
            private String province;
            private String language;
            private String headimgurl;
            private Object subscribe_time;
            private String unionid;
            private String privilege;
            private Object remark;
            private Object groupid;
            private Object tagid_list;
            private Object subscribe_scene;
            private Object qr_scene;
            private Object qr_scene_str;
            private String create_time;
            private String pid;
            private int adopt_friendpop_num;
            private int total_friendpop_num;
            private int add_goods_num;
            private Object password;
            private Object telephone;
            private int score;
            private String tao_session;
            private String tkl_type;
            private IsDeleteBean is_delete;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public Object getSubscribe() {
                return subscribe;
            }

            public void setSubscribe(Object subscribe) {
                this.subscribe = subscribe;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getHeadimgurl() {
                return headimgurl;
            }

            public void setHeadimgurl(String headimgurl) {
                this.headimgurl = headimgurl;
            }

            public Object getSubscribe_time() {
                return subscribe_time;
            }

            public void setSubscribe_time(Object subscribe_time) {
                this.subscribe_time = subscribe_time;
            }

            public String getUnionid() {
                return unionid;
            }

            public void setUnionid(String unionid) {
                this.unionid = unionid;
            }

            public String getPrivilege() {
                return privilege;
            }

            public void setPrivilege(String privilege) {
                this.privilege = privilege;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public Object getGroupid() {
                return groupid;
            }

            public void setGroupid(Object groupid) {
                this.groupid = groupid;
            }

            public Object getTagid_list() {
                return tagid_list;
            }

            public void setTagid_list(Object tagid_list) {
                this.tagid_list = tagid_list;
            }

            public Object getSubscribe_scene() {
                return subscribe_scene;
            }

            public void setSubscribe_scene(Object subscribe_scene) {
                this.subscribe_scene = subscribe_scene;
            }

            public Object getQr_scene() {
                return qr_scene;
            }

            public void setQr_scene(Object qr_scene) {
                this.qr_scene = qr_scene;
            }

            public Object getQr_scene_str() {
                return qr_scene_str;
            }

            public void setQr_scene_str(Object qr_scene_str) {
                this.qr_scene_str = qr_scene_str;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public int getAdopt_friendpop_num() {
                return adopt_friendpop_num;
            }

            public void setAdopt_friendpop_num(int adopt_friendpop_num) {
                this.adopt_friendpop_num = adopt_friendpop_num;
            }

            public int getTotal_friendpop_num() {
                return total_friendpop_num;
            }

            public void setTotal_friendpop_num(int total_friendpop_num) {
                this.total_friendpop_num = total_friendpop_num;
            }

            public int getAdd_goods_num() {
                return add_goods_num;
            }

            public void setAdd_goods_num(int add_goods_num) {
                this.add_goods_num = add_goods_num;
            }

            public Object getPassword() {
                return password;
            }

            public void setPassword(Object password) {
                this.password = password;
            }

            public Object getTelephone() {
                return telephone;
            }

            public void setTelephone(Object telephone) {
                this.telephone = telephone;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getTao_session() {
                return tao_session;
            }

            public void setTao_session(String tao_session) {
                this.tao_session = tao_session;
            }

            public String getTkl_type() {
                return tkl_type;
            }

            public void setTkl_type(String tkl_type) {
                this.tkl_type = tkl_type;
            }

            public IsDeleteBean getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(IsDeleteBean is_delete) {
                this.is_delete = is_delete;
            }

            public static class IsDeleteBean {
                /**
                 * type : Buffer
                 * data : [0]
                 */

                private String type;
                private List<Integer> data;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public List<Integer> getData() {
                    return data;
                }

                public void setData(List<Integer> data) {
                    this.data = data;
                }
            }
        }
    }
}
