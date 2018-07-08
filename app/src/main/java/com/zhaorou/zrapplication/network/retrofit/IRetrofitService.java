package com.zhaorou.zrapplication.network.retrofit;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface IRetrofitService {

    @GET("{url}")
    Call<ResponseBody> executeGet(@Path("url") String url);

    @GET("{url}")
    Call<ResponseBody> executeGet(@Path("url") String url, @QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("{url}")
    Call<ResponseBody> executePost(@Path("url") String url, @FieldMap Map<String, String> params);

}
