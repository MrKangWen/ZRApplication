package com.zhaorou.zrapplication.user.msg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseFragment;
import com.zhaorou.zrapplication.home.api.HomeApi;
import com.zhaorou.zrapplication.home.model.JxListModel;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.network.retrofit.AbsZCallback;
import com.zhaorou.zrapplication.user.model.SystemMsgModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * * 【GET】/api/get_unread_msg_list?token=a2669eabee524208170b86cb678a1d05
 * 【GET】/api/get_readed_msg_list?token=a2669eabee524208170b86cb678a1d05
 * 【GET】/api/get_system_notice_list?token=a2669eabee524208170b86cb678a1d05
 * <p>
 * A simple {@link Fragment} subclass.
 */
public class MsgFragment extends BaseFragment {


    public MsgFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        getData();
        return view;
    }


    @Override
    public void initData() {

    }


    private void getData() {


        HttpRequestUtil.getRetrofitService(HomeApi.class).getSystemMsgList(getToken()).enqueue(new AbsZCallback<SystemMsgModel>() {
            @Override
            public void onSuccess(Call<SystemMsgModel> call, Response<SystemMsgModel> response) {

            }

            @Override
            public void onFail(Call<SystemMsgModel> call, Throwable t) {

            }
        });


    }
}
