package com.zhaorou.zrapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhaorou.zrapplication.base.BaseActivity;
import com.zhaorou.zrapplication.home.HomeFragment;
import com.zhaorou.zrapplication.user.UserFragment;
import com.zhaorou.zrapplication.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.activity_main_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.activity_main_tab_home_iv)
    ImageView mTabHomeIv;
    @BindView(R.id.activity_main_tab_home_tv)
    TextView mTabHomeTv;
    @BindView(R.id.activity_main_tab_user_iv)
    ImageView mTabUserIv;
    @BindView(R.id.activity_main_tab_user_tv)
    TextView mTabUserTv;

    private VPAdapter mVPAdapter;

    private List<ImageView> mTabIconList;
    private List<TextView> mTabTextList;
    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTabIconList = new ArrayList<ImageView>() {{
            add(mTabHomeIv);
            add(mTabUserIv);
        }};
        mTabTextList = new ArrayList<TextView>() {{
            add(mTabHomeTv);
            add(mTabUserTv);
        }};
        mFragmentList = new ArrayList<Fragment>() {{
            add(new HomeFragment());
            add(new UserFragment());
        }};
        setSelectedTab(mTabHomeIv, mTabHomeTv);
        initViewPager();
    }

    @OnClick({R.id.activity_main_tab_home_iv, R.id.activity_main_tab_home_tv,
            R.id.activity_main_tab_user_iv, R.id.activity_main_tab_user_tv})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_tab_home_iv:
            case R.id.activity_main_tab_home_tv:
                setSelectedTab(mTabHomeIv, mTabHomeTv);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.activity_main_tab_user_iv:
            case R.id.activity_main_tab_user_tv:
                setSelectedTab(mTabUserIv, mTabUserTv);
                mViewPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }


    private void setSelectedTab(ImageView imageView, TextView textView) {
        for (ImageView view : mTabIconList) {
            if (view == imageView) {
                view.setSelected(true);
            } else {
                view.setSelected(false);
            }
        }
        for (TextView view : mTabTextList) {
            if (view == textView) {
                view.setSelected(true);
            } else {
                view.setSelected(false);
            }
        }
    }

    private void initViewPager() {
        FragmentManager fm = getSupportFragmentManager();
        mVPAdapter = new VPAdapter(fm);
        mViewPager.setAdapter(mVPAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setSelectedTab(mTabIconList.get(position), mTabTextList.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class VPAdapter extends FragmentPagerAdapter {

        public VPAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
