package com.zhaorou.zrapplication.network.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class RetrofitClient {
    private static final String TAG = "RetrofitClient";

    public static class Builder {
        private volatile OkHttpClient.Builder okHttpBuilder;
        private volatile Retrofit.Builder retrofitBuilder;
        private static Retrofit mRetrofit;

        public Builder() {
            if (okHttpBuilder == null) {
                synchronized (Builder.class) {
                    if (okHttpBuilder == null) {
                        okHttpBuilder = new OkHttpClient.Builder();
                    }
                }
            }

            if (retrofitBuilder == null) {
                synchronized (Builder.class) {
                    if (retrofitBuilder == null) {
                        retrofitBuilder = new Retrofit.Builder();
                    }
                }
            }
        }

        /*-----------------------OkHttpClient Builder---------------------------------------*/
        public Builder connectTimeout(long timeout, TimeUnit timeUnit) {
            okHttpBuilder.connectTimeout(timeout, timeUnit);
            return this;
        }

        public Builder readTimeout(long timeout, TimeUnit timeUnit) {
            okHttpBuilder.readTimeout(timeout, timeUnit);
            return this;
        }

        public Builder writeTimeout(long timeout, TimeUnit timeUnit) {
            okHttpBuilder.writeTimeout(timeout, timeUnit);
            return this;
        }

        public Builder cache(Cache cache) {
            okHttpBuilder.cache(cache);
            return this;
        }


        public Builder addInterceptor(Interceptor interceptor) {
            okHttpBuilder.addInterceptor(interceptor);
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            okHttpBuilder.addNetworkInterceptor(interceptor);
            return this;
        }

        public Builder cookieJar(CookieJar cookieJar) {
            okHttpBuilder.cookieJar(cookieJar);
            return this;
        }

        public Builder authenticator(Authenticator authenticator) {
            okHttpBuilder.authenticator(authenticator);
            return this;
        }

        /*------------------------Retrofit Builder----------------------------------------*/
        public Builder baseUrl(String baseUrl) {
            retrofitBuilder.baseUrl(baseUrl);
            return this;
        }

        public Builder addConverterFactory(Converter.Factory factory) {
            retrofitBuilder.addConverterFactory(factory);
            return this;
        }

        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            retrofitBuilder.addCallAdapterFactory(factory);
            return this;
        }

        public Retrofit build() {
            if (mRetrofit == null) {
                mRetrofit = retrofitBuilder.client(okHttpBuilder.build()).build();
            }
            return mRetrofit;
        }
    }
}
