package com.zhaorou.zrapplication.constants;

public class ZRDConstants {

    public static class HttpUrls {
        public static final String BASE_URL = "http://app.zhaoroudan.com/";
        public static final String GET_CLASS_LIST = "api/get_class_list";
        public static final String GET_DGOODS_LIST = "api/get_tkjd_goods_list";
        public static final String WX_LOGIN = "api/oauth2";
        public static final String GET_USER_INFO = "api/get_userinfo";
    }

    public static class AppIds {
        public static final String WX_APP_ID = "wx52070f1f1b0d384e";
    }

    public static class SharedPreferenceKey {
        public static final String SP_LOGIN_TOKEN = "login_token";
    }

    public static class EventCommand {
        public static final String COMMAND_LOGIN = "command_login";
    }
}
