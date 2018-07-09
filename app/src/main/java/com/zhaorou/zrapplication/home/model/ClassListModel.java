package com.zhaorou.zrapplication.home.model;

import java.util.List;

public class ClassListModel {

    /**
     * data : {"list":[{"id":"1","classname":"女装"},{"id":"2","classname":"男装"},{"id":"3","classname":"内衣"},{"id":"4","classname":"母婴"},{"id":"5","classname":"美妆"},{"id":"6","classname":"居家"},{"id":"7","classname":"鞋包配饰"},{"id":"8","classname":"美食"},{"id":"9","classname":"文体车品"},{"id":"10","classname":"数码家电"},{"id":"11","classname":"运动户外"},{"id":"12","classname":"其他"}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * classname : 女装
             */

            private String id;
            private String classname;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getClassname() {
                return classname;
            }

            public void setClassname(String classname) {
                this.classname = classname;
            }
        }
    }
}
