package com.zhaorou.zrapplication.home.presenter;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.zhaorou.zrapplication.base.BasePresenter;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.home.IHomeFragmentView;
import com.zhaorou.zrapplication.home.api.HomeApi;

import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.FriendPopDetailModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.home.model.JxListModel;
import com.zhaorou.zrapplication.home.model.TaowordsModel;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.network.retrofit.AbsZCallback;
import com.zhaorou.zrapplication.utils.ApplicationUtils;
import com.zhaorou.zrapplication.utils.GsonHelper;
import com.zhaorou.zrapplication.utils.SPreferenceUtil;

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
        if (mView != null) {
            mView.onShowLoading();
        }
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
                            if (mView != null) {
                                mView.onFetchedClassList(mClassList);
                            }
                        } else {
                            String data = jsonObj.optString("data");
                            if (mView != null) {
                                mView.onLoadFail(data);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mView != null) {
                    mView.onHideLoading();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (mView != null) {
                    mView.onLoadFail("网络请求失败");
                    mView.onHideLoading();
                }
            }

        });
    }

    public void fetchGoodsList(Map<String, String> params) {
//        mView.onShowLoading();
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executePost(ZRDConstants.HttpUrls.GET_DGOODS_LIST, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (mView != null) {
                    mView.onHideLoading();
                }
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
                                        if (mView != null) {
                                            mView.onFetchDtkGoodsList(list);
                                        }
                                    } else {
                                        if (mView != null) {
                                            mView.onLoadMore(false);
                                        }
                                    }
                                }
                            }
                        } else {
                            String data = jsonObj.optString("data");
                            if (mView != null) {
                                mView.onLoadFail(data);
                            }
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
                if (mView != null) {
                    mView.onLoadFail("网络请求失败");
                }
//                mView.onHideLoading();
            }
        });
    }


    public void getFriendPopDetail(Map<String, String> params) {
        if (mView != null) {
            mView.onShowLoading();
        }
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
                                    if (mView != null) {
                                        mView.onGetFriendPopDetail(entity);
                                    }
                                }
                            }
                        } else {
                            String data = jsonObj.optString("data");
                            if (mView != null) {
                                mView.onLoadFail(data);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (mView != null) {
                    mView.onHideLoading();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (mView != null) {
                    mView.onLoadFail("网络请求失败");
                    mView.onHideLoading();
                }
            }
        });
    }

    public void getTaobaoTbkTpwd(Map<String, String> params) {
        if (mView != null) {
            mView.onShowLoading();
        }
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

                                    if (mView != null) {
                                        String tklType = SPreferenceUtil.getString(ApplicationUtils.getApplicationContext(), ZRDConstants.SPreferenceKey.SP_LINK_TAO, "1");
                                        if (tklType.equals("1")) {
                                            String tkl = data.getTkl();
                                            mView.onGetTaowords(tkl);
                                        }else {
                                            String short_url = data.getShort_url();
                                            mView.onGetTaowords(short_url);
                                        }

                                    }
                                }
                            }
                        } else if (!jsonObj.isNull("data")) {
                            String message = jsonObj.optString("data");
                            if (mView != null) {
                                mView.onLoadFail(message);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (mView != null) {
                    mView.onHideLoading();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (mView != null) {
                    mView.onLoadFail("网络请求失败");
                    mView.onHideLoading();
                }
            }
        });
    }


    private MutableLiveData<List<JxListModel.DataBean.ListBean>> mJxListData;

    public MutableLiveData<List<JxListModel.DataBean.ListBean>> getJxListData() {
        if (mJxListData == null) {
            mJxListData = new MutableLiveData<>();
        }
        return mJxListData;
    }

    public void getJxListData(Map<String, Object> params) {

        if (mView != null) {
            mView.onShowLoading();
        }
        HttpRequestUtil.getRetrofitService(HomeApi.class).getJxList(params).enqueue(new AbsZCallback<JxListModel>() {
            @Override
            public void onSuccess(Call<JxListModel> call, Response<JxListModel> response) {
                if (mView != null) {
                    mView.onHideLoading();
                }

                JxListModel body = response.body();
//                Log.d("mytest", body.getData().getList().get(0).getGoods_id());
                if (mJxListData != null) {
                    mJxListData.setValue(body.getData().getList());
                }


            }

            @Override
            public void onFail(Call<JxListModel> call, Throwable t) {
                if (mView != null) {
                    mView.onHideLoading();
                }

            }
        });

    }


}
