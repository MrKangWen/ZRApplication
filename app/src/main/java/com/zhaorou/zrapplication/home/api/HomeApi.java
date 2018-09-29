package com.zhaorou.zrapplication.home.api;

import com.zhaorou.zrapplication.base.BaseDataModel;
import com.zhaorou.zrapplication.base.BaseModel;
import com.zhaorou.zrapplication.goods.GoodsDetailModel;
import com.zhaorou.zrapplication.home.model.AppUpdateModel;
import com.zhaorou.zrapplication.home.model.FriendPopDetailModel;
import com.zhaorou.zrapplication.home.model.JxListModel;
import com.zhaorou.zrapplication.user.model.HasReadMsgModel;
import com.zhaorou.zrapplication.user.model.SystemMsgModel;
import com.zhaorou.zrapplication.user.model.UnReadMsgModel;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @author kang
 */
public interface HomeApi {

    /**
     * 1精选商品 2 预告 3 常规
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
     * unread massage
     */
    @GET("api/get_unread_msg_list")
    Call<UnReadMsgModel> getUnReadList(@Query("token") String token);


    /**
     * has read massage
     */
    @GET("api/get_readed_msg_list")
    Call<HasReadMsgModel> getReadList(@Query("token") String token);

    /**
     * massage of system
     */
    @GET("api/get_system_notice_list")
    Call<SystemMsgModel> getSystemMsgList(@Query("token") String token);

    @GET
    Call<FriendPopDetailModel> getFriendPopDetail(@Url String url, @QueryMap Map<String, String> params);

    /**
     * 设置提醒
     * 【post】/api/add_push_record
     */
    @FormUrlEncoded
    @POST("api/add_push_record")
    Call<BaseModel> setPushRecord(@FieldMap Map<String, Object> params);

    /**
     * 消息更新为已读
     * {
     * field:"is_read"
     * id:2580
     * token:"d6e3627378f896f9e2a6e44e796c2e26"
     * val:1
     * }
     */
    @FormUrlEncoded
    @POST("api/upd_system_msg_status")
    Call<BaseDataModel> updateMsgStatus(@FieldMap Map<String, Object> params);

    /**
     * http://app.zhaoroudan.com/api/get_rou_goods_detail?id=949&token=6c6808e17379e57309f76f13be68866f
     * <p>
     * goods detail
     */
    @GET("api/get_rou_goods_detail")
    Call<GoodsDetailModel> getGoodsDetail(@Query("id") int goodsId);
}
