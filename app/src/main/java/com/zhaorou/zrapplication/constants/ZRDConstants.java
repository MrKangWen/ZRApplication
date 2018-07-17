package com.zhaorou.zrapplication.constants;

public class ZRDConstants {

    public static class HttpUrls {
        public static final String BASE_URL = "http://app.zhaoroudan.com/";
        public static final String GET_CLASS_LIST = "api/get_class_list";
        public static final String GET_DGOODS_LIST = "api/get_tkjd_goods_list";
        public static final String WX_LOGIN = "api/oauth2";
        public static final String GET_USER_INFO = "api/get_userinfo";
        public static final String UPDATE_PID = "api/update_pid";
        public static final String GET_FRIENDPOP_DETAIL = "api/get_friendpop_detail";
        public static final String UPLOAD_FILE = "api/uploadFile";
        public static final String ADD_FRIEND_POP = "api/add_friendpop";
        public static final String GET_TAOBAO_TBK_TPWD = "api/get_taobao_tbk_tpwd";
        public static final String UPDATE_TKL_TYPE = "api/update_tkl_type";
    }

    public static class AppIds {
        public static final String WX_APP_ID = "wx52070f1f1b0d384e";
    }

    public static class SPreferenceKey {
        public static final String SP_LOGIN_TOKEN = "login_token";
        public static final String SP_PID = "pid";
        public static final String SP_TAO_SESSION = "tao_session";
        public static final String SP_LINK_TAO = "link_tao";
    }

    public static class EventCommand {
        public static final String COMMAND_LOGIN = "command_login";
        public static final String COMMAND_SELECT_MARKET_IMAGE = "command_select_market_image";
        public static final String COMMAND_SELECT_IMAGES = "command_select_images";

    }
}
