package com.zhaorou.zrapplication.widget.recyclerview;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseFragment;
import com.zhaorou.zrapplication.base.BaseModel;

/**
 * @author kang
 */
public abstract class BaseListBindDataFragment<T extends BaseModel, D> extends BaseFragment implements ListBindDataInterface<T, D>, SwipeRefreshLayout.OnRefreshListener {

    protected View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(getLayoutId(), container, false);
        initData();
        return mView;
    }

    private ListBindDataHelper<T, D> helper;

    @Override
    public void initData() {


        final SwipeRefreshLayout swipeRefreshLayout = mView.findViewById(R.id.baseSwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView mRecyclerView = getRecyclerViewId(mView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        helper = new ListBindDataHelper<>(getActivity(), mRecyclerView, getAdapterLayoutId(), this);
        startGetData();
        helper.setListRefreshListener(new ListRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        //  helper.setNeedLoadMore(false);
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void startGetData() {
        if (helper != null) {
            helper.startPostData(getCall(helper.getMap()));
        }
    }

    public ListBindDataHelper<T, D> getHelper() {

        return helper;
    }

    protected int getLayoutId() {

        return R.layout.base_list_view;
    }

    public RecyclerView getRecyclerViewId(View view) {

        FrameLayout frameLayout = view.findViewById(R.id.baseListFlView);
        //动态添加
        RecyclerView recyclerView = new RecyclerView(this.getActivity());
        recyclerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
        frameLayout.addView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        return recyclerView;

    }

    @Override
    public void onRefresh() {

        getHelper().onRefreshData();
    }
}
