package com.zhaorou.zrapplication.widget.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zhaorou.zrapplication.base.BaseFragment;
import com.zhaorou.zrapplication.base.BaseModel;

/**
 * @author kang
 */
public abstract class BaseListBindDataFragment<T extends BaseModel, D> extends BaseFragment implements ListBindDataInterface<T, D> {

    protected View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(getLayoutId(), container, false);
        initData();
        return mView;
    }

    ListBindDataHelper<T, D> helper;

    @Override
    public void initData() {

        RecyclerView recyclerView = mView.findViewById(getRecyclerViewId());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        helper = new ListBindDataHelper<>(getActivity(), recyclerView, getAdapterLayoutId(), this);
        helper.startPostData(getCall(helper.getMap()));
        //  helper.setNeedLoadMore(false);
    }

    public ListBindDataHelper<T, D> getHelper() {

        return helper;
    }

    protected abstract int getLayoutId();

    protected abstract int getRecyclerViewId();

}
