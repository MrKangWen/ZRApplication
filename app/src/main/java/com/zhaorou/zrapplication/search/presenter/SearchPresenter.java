package com.zhaorou.zrapplication.search.presenter;

import com.zhaorou.zrapplication.base.BasePresenter;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.home.model.FriendPopDetailModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.home.model.TaowordsModel;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.search.ISearchView;
import com.zhaorou.zrapplication.utils.GsonHelper;

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
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executePost(ZRDConstants.HttpUrls.GET_DGOODS_LIST, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mView.onShowLoading();
                mView.onHideLoading();
                try {
                    if (response != null && response.body() != null) {
                        String responseStr = response.body().string();
                        GoodsListModel goodsListModel = GsonHelper.fromJson(responseStr, GoodsListModel.class);
                        if (goodsListModel != null && goodsListModel.getCode() == 200) {
                            GoodsListModel.DataBean data = goodsListModel.getData();
                            if (data != null) {
                                List<GoodsListModel.DataBean.ListBean> list = data.getList();
                                if (list != null && list.size() > 0) {
                                    mView.onSearchResult(list);
                                } else {
                                    mView.onLoadMore(false);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mView.onHideLoading();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mView.onHideLoading();
            }
        });
    }

    public void getTaobaoTbkTpwd(Map<String, String> params) {
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executeGet(ZRDConstants.HttpUrls.GET_TAOBAO_TBK_TPWD, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.body() != null) {
                    try {
                        String responseStr = response.body().string();
                        TaowordsModel taowordsModel = GsonHelper.fromJson(responseStr, TaowordsModel.class);
                        if (taowordsModel != null && taowordsModel.getCode() == 200) {
                            TaowordsModel.DataBeanX data = taowordsModel.getData();
                            if (data != null) {
                                String tkl = data.getTkl();
                                mView.onGetTaowords(tkl);
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

    public void getFriendPopDetail(Map<String, String> params) {
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executeGet(ZRDConstants.HttpUrls.GET_FRIENDPOP_DETAIL, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.body() != null) {
                    try {
                        String responseStr = response.body().string();
                        FriendPopDetailModel friendPopDetailModel = GsonHelper.fromJson(responseStr, FriendPopDetailModel.class);
                        if (friendPopDetailModel != null && friendPopDetailModel.getCode() == 200) {
                            FriendPopDetailModel.DataBean data = friendPopDetailModel.getData();
                            if (data != null) {
                                FriendPopDetailModel.DataBean.EntityBean entity = data.getEntity();
                                mView.onGetFriendPopDetail(entity);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mView.onHideLoading();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mView.onHideLoading();
            }
        });
    }
}
