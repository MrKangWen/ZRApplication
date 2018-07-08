package com.zhaorou.zrapplication.network;

import com.zhaorou.zrapplication.constants.ZRDConstants;

import com.zhaorou.zrapplication.network.retrofit.IRetrofitService;
import com.zhaorou.zrapplication.network.retrofit.RetrofitClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequestUtil {

    private static Retrofit sRetrofit;

    public static void init() {
        if (sRetrofit == null) {
            sRetrofit = new RetrofitClient.Builder()
                    .baseUrl(ZRDConstants.HttpUrls.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

    }

    public static IRetrofitService getRetrofitService() {
        return sRetrofit.create(IRetrofitService.class);
    }

    public static <T> T getRetrofitService(Class<T> tClass) {
        return sRetrofit.create(tClass);
    }

}
