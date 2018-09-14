package com.zhaorou.zrapplication.home.api;

import com.zhaorou.zrapplication.home.model.AppUpdateModel;
import com.zhaorou.zrapplication.home.model.JxListModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
}
