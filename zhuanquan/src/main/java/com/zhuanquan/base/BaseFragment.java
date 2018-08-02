package com.zhuanquan.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuanquan.R;
import com.zhuanquan.interfaces.IBaseView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment implements IBaseView {

    @BindView(R.id.swipe_refresh_layout_base_fragment)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private View mInflate;

    public BaseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initContentView(inflater, container);
        onViewCreated();
        return mInflate;
    }

    private void initContentView(LayoutInflater inflater, ViewGroup container) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_base, container, false);
            ButterKnife.bind(this, mInflate);
            int layoutId = setContentLayout();
            View view = getLayoutInflater().inflate(layoutId, null);
            mSwipeRefreshLayout.addView(view);
        }
    }

    @Override
    public int setContentLayout() {
        return 0;
    }

    @Override
    public void onViewCreated() {
    }
}
