package com.zhaorou.zrapplication.home.presenter;

import android.util.Log;

import com.zhaorou.zrapplication.base.BasePresenter;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.home.IHomeFragmentView;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.DtkGoodsListModel;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.utils.GsonHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentPresenter extends BasePresenter<IHomeFragmentView> {

    private static final String TAG = "HomeFragmentPresenter";

    public void fetchClassList() {
        final List<ClassListModel.DataBean.ListBean> mClassList = new ArrayList<>();
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executeGet(ZRDConstants.HttpUrls.GET_CLASS_LIST);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseStr = response.body().string();
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void fetchDtkGoodsList() {
        final List<DtkGoodsListModel.DataBean.ListBean> dtkGoodsList = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("type", "paoliang");
        params.put("page", "1");
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executeGet(ZRDConstants.HttpUrls.GET_DTK_GOODS_LIST, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseStr = response.body().string();
                    DtkGoodsListModel dtkGoodsListModel = GsonHelper.fromJson(responseStr, DtkGoodsListModel.class);
                    if (dtkGoodsListModel != null && dtkGoodsListModel.getCode() == 200) {
                        DtkGoodsListModel.DataBean dataBean = dtkGoodsListModel.getData();
                        if (dataBean != null) {
                            List<DtkGoodsListModel.DataBean.ListBean> listBeans = dataBean.getList();
                            if (listBeans != null) {
                                for (DtkGoodsListModel.DataBean.ListBean listBean : listBeans) {
                                    dtkGoodsList.add(listBean);
                                }
                            }
                        }
                    }
                    mView.onFetchDtkGoodsList(dtkGoodsList);
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
