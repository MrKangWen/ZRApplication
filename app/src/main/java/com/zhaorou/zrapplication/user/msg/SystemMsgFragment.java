package com.zhaorou.zrapplication.user.msg;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseFragment;
import com.zhaorou.zrapplication.home.api.HomeApi;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.user.model.SystemMsgModel;
import com.zhaorou.zrapplication.widget.recyclerview.BaseListBindDataFragment;
import com.zhaorou.zrapplication.widget.recyclerview.CombinationViewHolder;
import com.zhaorou.zrapplication.widget.recyclerview.ListBindDataHelper;
import com.zhaorou.zrapplication.widget.recyclerview.ListBindDataInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class SystemMsgFragment extends BaseListBindDataFragment<SystemMsgModel, SystemMsgModel.DataBean.ListBean> {


    public SystemMsgFragment() {
        // Required empty public constructor
    }

    @Override
    public void initData() {
        super.initData();
        getHelper().setNeedLoadMore(false);
    }


    @Override
    public int getAdapterLayoutId() {
        return R.layout.item_msg_system;
    }

    @Override
    public List<SystemMsgModel.DataBean.ListBean> getAdapterList(SystemMsgModel result) {
        return result.getData().getList();
    }

    @Override
    public void bindData(CombinationViewHolder holder, SystemMsgModel.DataBean.ListBean t, int position) {

        holder.setText(R.id.title,t.getTitle());
        holder.setText(R.id.date,t.getPublish_time());
    }

    @Override
    public Call<SystemMsgModel> getCall(Map<String, Object> map) {
        return HttpRequestUtil.getRetrofitService(HomeApi.class).getSystemMsgList(getToken());
    }
}
