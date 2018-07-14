package com.zhaorou.zrapplication.home.model;

public class FriendPopDetailModel {

    /**
     * data : {"entity":{"id":78,"content":"文案内容","market_image":"营销图片","image":"产品图片1#产品图片2#产品图片3"}}
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
         * entity : {"id":78,"content":"文案内容","market_image":"营销图片","image":"产品图片1#产品图片2#产品图片3"}
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
             * id : 78
             * content : 文案内容
             * market_image : 营销图片
             * image : 产品图片1#产品图片2#产品图片3
             */

            private int id;
            private String content;
            private String market_image;
            private String image;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getMarket_image() {
                return market_image;
            }

            public void setMarket_image(String market_image) {
                this.market_image = market_image;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
