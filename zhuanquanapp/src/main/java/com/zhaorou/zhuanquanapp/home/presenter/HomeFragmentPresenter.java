package com.zhaorou.zhuanquanapp.home.presenter;


import com.zhaorou.zhuanquanapp.base.BasePresenter;
import com.zhaorou.zhuanquanapp.constants.ZRDConstants;
import com.zhaorou.zhuanquanapp.home.IHomeFragmentView;
import com.zhaorou.zhuanquanapp.home.model.ClassListModel;
import com.zhaorou.zhuanquanapp.home.model.FriendPopDetailModel;
import com.zhaorou.zhuanquanapp.home.model.GoodsListModel;
import com.zhaorou.zhuanquanapp.home.model.TaowordsModel;
import com.zhaorou.zhuanquanapp.network.HttpRequestUtil;
import com.zhaorou.zhuanquanapp.utils.GsonHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentPresenter extends BasePresenter<IHomeFragmentView> {

    private static final String TAG = "HomeFragmentPresenter";

    public void fetchClassList() {
        mView.onShowLoading();
        final List<ClassListModel.DataBean.ListBean> mClassList = new ArrayList<>();
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executeGet(ZRDConstants.HttpUrls.GET_CLASS_LIST);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response != null && response.body() != null) {
                        String responseStr = response.body().string();
                        JSONObject jsonObj = new JSONObject(responseStr);
                        if (jsonObj.optInt("code") == 200) {
                            ClassListModel classListModel = GsonHelper.fromJson(responseStr, ClassListModel.class);
                            if (classListModel != null && classListModel.getCode() == 200) {
                                ClassListModel.DataBean dataBean = classListModel.getData();
                                if (dataBean != null) {
                                    List<ClassListModel.DataBean.ListBean> listBeans = dataBean.getList();
                                    if (listBeans != null) {
                                        for (ClassListModel.DataBean.ListBean listBean : listBeans) {
                                            mClassList.add(listBean);
                                        }
                                    }
                                }
                            }
                            mView.onFetchedClassList(mClassList);
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

    public void fetchGoodsList(Map<String, String> params) {
//        mView.onShowLoading();
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executePost(ZRDConstants.HttpUrls.GET_DGOODS_LIST, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mView.onHideLoading();
                try {
                    if (response != null && response.body() != null) {
                        String responseStr = response.body().string();
                        JSONObject jsonObj = new JSONObject(responseStr);
                        if (jsonObj.optInt("code") == 200) {
                            GoodsListModel goodsListModel = GsonHelper.fromJson(responseStr, GoodsListModel.class);
                            if (goodsListModel != null && goodsListModel.getCode() == 200) {
                                GoodsListModel.DataBean data = goodsListModel.getData();
                                if (data != null) {
                                    List<GoodsListModel.DataBean.ListBean> list = data.getList();
                                    if (list != null && list.size() > 0) {
                                        mView.onFetchDtkGoodsList(list);
                                    } else {
                                        mView.onLoadMore(false);
                                    }
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
//                mView.onHideLoading();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mView.onLoadFail("网络请求失败");
//                mView.onHideLoading();
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
                            if (friendPopDetailModel != null && friendPopDetailModel.getCode() == 200) {
                                FriendPopDetailModel.DataBean data = friendPopDetailModel.getData();
                                if (data != null) {
                                    FriendPopDetailModel.DataBean.EntityBean entity = data.getEntity();
                                    mView.onGetFriendPopDetail(entity);
                                }
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
                            if (taowordsModel != null && taowordsModel.getCode() == 200) {
                                TaowordsModel.DataBeanX data = taowordsModel.getData();
                                if (data != null) {
                                    String tkl = data.getTkl();
                                    mView.onGetTaowords(tkl);
                                }
                            }
                        } else if (!jsonObj.isNull("data")) {
                            String message = jsonObj.optString("data");
                            mView.onLoadFail(message);
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
