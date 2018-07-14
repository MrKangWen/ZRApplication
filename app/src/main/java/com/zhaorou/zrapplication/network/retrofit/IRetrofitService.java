package com.zhaorou.zrapplication.network.retrofit;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface IRetrofitService {

    @GET
    Call<ResponseBody> executeGet(@Url String url);

    @GET
    Call<ResponseBody> executeGet(@Url String url, @QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> executePost(@Url String url, @FieldMap Map<String, String> params);

    @Multipart
    @POST()
    Call<ResponseBody> uploadFile(@Url String url, @Part("name") String name, @Part MultipartBody.Part file);

}
