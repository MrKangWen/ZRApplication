package com.zhaorou.zrapplication.user.presenter;

import android.util.Log;

import com.zhaorou.zrapplication.base.BasePresenter;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.user.IUserFragmentView;
import com.zhaorou.zrapplication.user.model.PidModel;
import com.zhaorou.zrapplication.user.model.UserInfoModel;
import com.zhaorou.zrapplication.utils.GsonHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragmentPresenter extends BasePresenter<IUserFragmentView> {

    private static final String TAG = "UserFragmentPresenter";

    public void fetchUserInfo(String token) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executeGet(ZRDConstants.HttpUrls.GET_USER_INFO, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.body() != null) {
                    try {
                        String responseStr = response.body().string();
                        Log.e(TAG, "onResponse: responseStr: " + responseStr);
                        UserInfoModel userInfoModel = GsonHelper.fromJson(responseStr, UserInfoModel.class);
                        if (userInfoModel != null && userInfoModel.getCode() == 200) {
                            UserInfoModel.DataBean data = userInfoModel.getData();
                            if (data != null && data.getUser() != null) {
                                UserInfoModel.DataBean.UserBean user = data.getUser();
                                mView.onFetchedUserInfo(user);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void bindPid(final String pid, String token) {
        Map<String, String> params = new HashMap<>();
        params.put("pid", pid);
        params.put("token", token);
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executePost(ZRDConstants.HttpUrls.UPDATE_PID, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response != null && response.body() != null) {
                        String responseStr = response.body().string();
                        PidModel pidModel = GsonHelper.fromJson(responseStr, PidModel.class);
                        if (pidModel != null && pidModel.getCode() == 200) {
                            mView.onUpdatedPid();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}
