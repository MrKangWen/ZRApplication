package com.zhaoroudan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhaoroudan.base.BaseActivity;
import com.zhaoroudan.home.HomeFragment;
import com.zhaoroudan.sheet.MeetSheetFragment;
import com.zhaoroudan.user.UserFragment;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.view_pager_main_activity)
    ViewPager mPagingVp;
    @BindView(R.id.tab_layout_main_activity)
    TabLayout mPagingTabsTl;

    @BindArray(R.array.tabs_name_main_activity)
    String[] mTabsNames;

    private int[] mTabIcons = new int[]{R.drawable.selector_tab_home_main_activity, R.drawable.selector_tab_sheet_main_activity, R.drawable.selector_tab_user_main_activity};
    private Fragment[] mFragments = new Fragment[]{new HomeFragment(), new MeetSheetFragment(), new UserFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPagingVp();
        initPagingTabs();
    }

    private void initPagingVp() {

        FragmentManager fm = getSupportFragmentManager();
        mPagingVp.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }
        });
    }

    private void initPagingTabs() {
        mPagingTabsTl.setupWithViewPager(mPagingVp, true);
        for (int i = 0; i < mTabsNames.length; i++) {
            TabLayout.Tab tab = mPagingTabsTl.getTabAt(i);
            View view = getLayoutInflater().inflate(R.layout.item_tab_layout, mPagingTabsTl, false);
            TextView tabNameTv = view.findViewById(R.id.tab_text_item_tab_layout);
            tabNameTv.setText(mTabsNames[i]);
            ImageView iconTopIv = view.findViewById(R.id.icon_top_item_tab_layout);
            iconTopIv.setBackground(getResources().getDrawable(mTabIcons[i]));
            tab.setCustomView(view);
        }
    }
}
