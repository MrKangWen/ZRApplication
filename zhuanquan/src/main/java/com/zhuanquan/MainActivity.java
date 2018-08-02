package com.zhuanquan;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuanquan.base.BaseActivity;
import com.zhuanquan.home.HomeFragment;
import com.zhuanquan.user.UserFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>() {{
        add(new HomeFragment());
        add(new UserFragment());
    }};
    private ArrayList<String> mTabNames = new ArrayList<String>() {{
        add("首页");
        add("我的");
    }};
    private ArrayList<Integer> mTabsIcon = new ArrayList<Integer>() {{
        add(R.drawable.selector_home_tab);
        add(R.drawable.selector_user_tab);
    }};
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    public int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onViewCreated() {
        initViewPager();
        initTabLayout();
    }

    private void initTabLayout() {
        mTabLayout = findViewById(R.id.tab_layout_main_activity);
        mTabLayout.setupWithViewPager(mViewPager, true);
        for (int i = 0; i < mTabNames.size(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            View view = getLayoutInflater().inflate(R.layout.layout_main_activity_tab_item, mTabLayout, false);
            ImageView tabIconIv = view.findViewById(R.id.icon_main_activity_tab_item);
            tabIconIv.setBackgroundResource(mTabsIcon.get(i));
            TextView tabNameTv = view.findViewById(R.id.text_main_activity_tab_item);
            tabNameTv.setText(mTabNames.get(i));
            tab.setCustomView(view);
        }
    }

    private void initViewPager() {

        mViewPager = findViewById(R.id.view_pager_main_activity);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
    }

}
