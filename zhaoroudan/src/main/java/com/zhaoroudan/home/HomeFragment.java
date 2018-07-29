package com.zhaoroudan.home;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhaoroudan.R;
import com.zhaoroudan.base.BaseFragment;
import com.zhaoroudan.widget.recyclerview.CustomRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.class_layout_home_fragment)
    LinearLayout mClassLayoutLl;
    @BindView(R.id.class_recycler_view_home_fragment)
    CustomRecyclerView mClassRecyclerView;
    @BindView(R.id.tabs_layout_home_fragment)
    TabLayout mTabsLayout;
    @BindView(R.id.view_pager_home_fragment)
    ViewPager mViewPager;

    private View mView;
    private Unbinder mBind;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
            mBind = ButterKnife.bind(this, mView);
        }
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
