package com.zhaorou.zrapplication.home.rd;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseFragment;
import com.zhaorou.zrapplication.user.msg.HasReadFragment;
import com.zhaorou.zrapplication.user.msg.SystemMsgFragment;
import com.zhaorou.zrapplication.widget.recyclerview.TabLayoutPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RdFragment extends BaseFragment {


    public RdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rd, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initData() {

    }

    private void initView(View view) {
        List<Fragment> listFragment = new ArrayList<>();
        listFragment.add(new PreviewFragment());
        listFragment.add(new CommonFragment());
        List<String> listTitle = new ArrayList<>();
        listTitle.add("预告版");
        listTitle.add("常规版");
        TabLayoutPagerAdapter tabLayoutPagerAdapter = new TabLayoutPagerAdapter(getChildFragmentManager(), listFragment, listTitle);

        ViewPager viewPager = view.findViewById(R.id.rdViewPager);
        viewPager.setAdapter(tabLayoutPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.rdTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }
}
