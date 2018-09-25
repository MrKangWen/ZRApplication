package com.zhaorou.zrapplication.user.msg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.home.api.HomeApi;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.user.model.HasReadMsgModel;
import com.zhaorou.zrapplication.user.model.SystemMsgModel;
import com.zhaorou.zrapplication.widget.recyclerview.BaseListBindDataFragment;
import com.zhaorou.zrapplication.widget.recyclerview.CombinationViewHolder;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class HasReadFragment extends BaseListBindDataFragment<HasReadMsgModel, HasReadMsgModel.DataBean.ListBean> {


    public HasReadFragment() {
        // Required empty public constructor
    }


    @Override
    public void initData() {
        super.initData();
        getHelper().setNeedLoadMore(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_has_read;
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.msgHasReadRecyclerView;
    }

    @Override
    public int getAdapterLayoutId() {
        return R.layout.item_msg_has_read;
    }

    @Override
    public List<HasReadMsgModel.DataBean.ListBean> getAdapterList(HasReadMsgModel result) {
        return result.getData().getList();
    }

    @Override
    public void bindData(CombinationViewHolder holder, HasReadMsgModel.DataBean.ListBean t, int position) {

        holder.setText(R.id.title, t.getContent());
        holder.setText(R.id.date, t.getCreate_time());
    }

    @Override
    public Call<HasReadMsgModel> getCall(Map<String, Object> map) {
        return HttpRequestUtil.getRetrofitService(HomeApi.class).getReadList(getToken());
    }
}
