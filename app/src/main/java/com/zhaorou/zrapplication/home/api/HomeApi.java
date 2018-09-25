package com.zhaorou.zrapplication.home.api;

import com.zhaorou.zrapplication.home.model.AppUpdateModel;
import com.zhaorou.zrapplication.home.model.JxListModel;
import com.zhaorou.zrapplication.user.model.HasReadMsgModel;
import com.zhaorou.zrapplication.user.model.SystemMsgModel;
import com.zhaorou.zrapplication.user.model.UnReadMsgModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author kang
 */
public interface HomeApi {

    /**
     * 精选商品
     */
    @FormUrlEncoded
    @POST("api/get_rou_goods_list")
    Call<JxListModel> getJxList(@FieldMap Map<String, Object> params);

    /**
     * 升级
     */
    @GET("api/get_latest_version?type=1")
    Call<AppUpdateModel> appUpdate();


    /**
     *  unread massage
     */
    @GET("api/get_unread_msg_list")
    Call<UnReadMsgModel> getUnReadList(@Query("token") String token);


    /**
     * has read massage
     */
    @GET("api/get_readed_msg_list")
    Call<HasReadMsgModel> getReadList(@Query("token") String token);

    /**
     *  massage of system
     */
    @GET("api/get_system_notice_list")
    Call<SystemMsgModel> getSystemMsgList(@Query("token") String token);

}
