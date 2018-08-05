package com.zhaorou.zhuanquanapp.search.presenter;


import com.zhaorou.zhuanquanapp.base.BasePresenter;
import com.zhaorou.zhuanquanapp.constants.ZRDConstants;
import com.zhaorou.zhuanquanapp.home.model.FriendPopDetailModel;
import com.zhaorou.zhuanquanapp.home.model.GoodsListModel;
import com.zhaorou.zhuanquanapp.home.model.TaowordsModel;
import com.zhaorou.zhuanquanapp.network.HttpRequestUtil;
import com.zhaorou.zhuanquanapp.search.ISearchView;
import com.zhaorou.zhuanquanapp.utils.GsonHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter extends BasePresenter<ISearchView> {

    private static final String TAG = "SearchPresenter";

    public void doSearch(Map<String, String> params) {
        mView.onShowLoading();
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executePost(ZRDConstants.HttpUrls.GET_DGOODS_LIST, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response != null && response.body() != null) {
                        String responseStr = response.body().string();
                        JSONObject jsonObj = new JSONObject(responseStr);
                        if (jsonObj.optInt("code") == 200) {
                            GoodsListModel goodsListModel = GsonHelper.fromJson(responseStr, GoodsListModel.class);
                            GoodsListModel.DataBean data = goodsListModel.getData();
                            if (data != null) {
                                List<GoodsListModel.DataBean.ListBean> list = data.getList();
                                if (list != null && list.size() > 0) {
                                    mView.onSearchResult(list);
                                } else {
                                    mView.onLoadMore(false);
                                }
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

    public void getTaobaoTbkTpwd(Map<String, String> params) {
        mView.onShowLoading();
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executeGet(ZRDConstants.HttpUrls.GET_TAOBAO_TBK_TPWD, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.body() != null) {
                    try {
                        String responseStr = response.body().string();
                        JSONObject jsonObj = new JSONObject(responseStr);
                        if (jsonObj.optInt("code") == 200) {
                            TaowordsModel taowordsModel = GsonHelper.fromJson(responseStr, TaowordsModel.class);
                            TaowordsModel.DataBeanX data = taowordsModel.getData();
                            if (data != null) {
                                String tkl = data.getTkl();
                                mView.onGetTaowords(tkl);
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

    public void getFriendPopDetail(Map<String, String> params) {
        mView.onShowLoading();
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executeGet(ZRDConstants.HttpUrls.GET_FRIENDPOP_DETAIL, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.body() != null) {
                    try {
                        String responseStr = response.body().string();
                        JSONObject jsonObj = new JSONObject(responseStr);
                        if (jsonObj.optInt("code") == 200) {
                            FriendPopDetailModel friendPopDetailModel = GsonHelper.fromJson(responseStr, FriendPopDetailModel.class);
                            FriendPopDetailModel.DataBean data = friendPopDetailModel.getData();
                            if (data != null) {
                                FriendPopDetailModel.DataBean.EntityBean entity = data.getEntity();
                                mView.onGetFriendPopDetail(entity);
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
}
