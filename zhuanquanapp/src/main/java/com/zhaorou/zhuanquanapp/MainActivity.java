package com.zhaorou.zhuanquanapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.zhaorou.zhuanquanapp.base.BaseActivity;
import com.zhaorou.zhuanquanapp.eventbus.MessageEvent;
import com.zhaorou.zhuanquanapp.home.HomeFragment;
import com.zhaorou.zhuanquanapp.user.UserFragment;
import com.zhaorou.zhuanquanapp.utils.AccessibilityUtils;
import com.zhaorou.zhuanquanapp.utils.AssistantService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
        if (!AccessibilityUtils.isAccessibilitySettingsOn(AssistantService.class.getName(), this)) {
            new AlertDialog.Builder(this).setMessage("打开【设置——>辅助功能/无障碍——>找肉单——>开启】开启分享朋友圈自动粘贴文字功能")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AccessibilityUtils.openAccessibility(AssistantService.class.getName(), MainActivity.this);
                        }
                    }).create().show();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MessageEvent<Object> message = new MessageEvent<>();
        message.setCommand(requestCode + "");
        message.setData(data);
        EventBus.getDefault().post(message);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

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
