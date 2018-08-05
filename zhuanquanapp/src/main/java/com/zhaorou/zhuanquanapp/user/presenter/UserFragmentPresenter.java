package com.zhaorou.zhuanquanapp.user.presenter;


import com.zhaorou.zhuanquanapp.base.BasePresenter;
import com.zhaorou.zhuanquanapp.constants.ZRDConstants;
import com.zhaorou.zhuanquanapp.network.HttpRequestUtil;
import com.zhaorou.zhuanquanapp.user.IUserFragmentView;
import com.zhaorou.zhuanquanapp.user.model.PidModel;
import com.zhaorou.zhuanquanapp.user.model.UserInfoModel;
import com.zhaorou.zhuanquanapp.utils.GsonHelper;

import org.json.JSONException;
import org.json.JSONObject;

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
        mView.onShowLoading();
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executeGet(ZRDConstants.HttpUrls.GET_USER_INFO, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.body() != null) {
                    try {
                        String responseStr = response.body().string();
                        JSONObject jsonObj = new JSONObject(responseStr);
                        if (jsonObj.optInt("code") == 200) {
                            UserInfoModel userInfoModel = GsonHelper.fromJson(responseStr, UserInfoModel.class);
                            UserInfoModel.DataBean data = userInfoModel.getData();
                            if (data != null && data.getUser() != null) {
                                UserInfoModel.DataBean.UserBean user = data.getUser();
                                mView.onFetchedUserInfo(user);
                            }
                        } else {
                            String data = jsonObj.optString("data");
                            mView.onLoadFail(data);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mView.onHideLoading();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mView.onLoadFail("网络请求失败");
                mView.onHideLoading();
            }
        });
    }

    public void bindPid(final String pid, String token) {
        mView.onShowLoading();
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
                        JSONObject jsonObj = new JSONObject(responseStr);
                        if (jsonObj.optInt("code") == 200) {
                            mView.onUpdatedPid(pid);
                        } else {
                            String data = jsonObj.optString("data");
                            mView.onLoadFail(data);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mView.onHideLoading();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mView.onLoadFail("网络请求失败");
                mView.onHideLoading();
            }
        });
    }

    public void bindTaoSession(final String taoSession, String token) {
        mView.onShowLoading();
        Map<String, String> params = new HashMap<>();
        params.put("tao_session", taoSession);
        params.put("token", token);
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executePost(ZRDConstants.HttpUrls.BIND_TAO_SESSION, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response != null && response.body() != null) {
                        String responseStr = response.body().string();
                        JSONObject jsonObj = new JSONObject(responseStr);
                        if (jsonObj.optInt("code") == 200) {
                            PidModel pidModel = GsonHelper.fromJson(responseStr, PidModel.class);
                            if (pidModel != null && pidModel.getCode() == 200) {
                                mView.onUpdatedTaoSession(taoSession);
                            }
                        } else {
                            String data = jsonObj.optString("data");
                            mView.onLoadFail(data);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mView.onHideLoading();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mView.onLoadFail("网络请求失败");
                mView.onHideLoading();
            }
        });
    }
}
