package com.zhaorou.zrapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhaorou.zrapplication.base.BaseActivity;
import com.zhaorou.zrapplication.eventbus.MessageEvent;
import com.zhaorou.zrapplication.home.HomeFragment;
import com.zhaorou.zrapplication.home.rd.RdFragment;
import com.zhaorou.zrapplication.home.api.HomeApi;
import com.zhaorou.zrapplication.home.model.AppUpdateModel;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.network.retrofit.AbsZCallback;
import com.zhaorou.zrapplication.network.update.UpdateAppService;
import com.zhaorou.zrapplication.user.UserFragment;
import com.zhaorou.zrapplication.utils.AccessibilityUtils;
import com.zhaorou.zrapplication.utils.AssistantService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

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

    @BindView(R.id.activity_main_tab_rd_iv)
    ImageView mTabRdIv;
    @BindView(R.id.activity_main_tab_rd_tv)
    TextView mTabRdTv;


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
            add(mTabRdIv);
            add(mTabUserIv);
        }};
        mTabTextList = new ArrayList<TextView>() {{
            add(mTabHomeTv);
            add(mTabRdTv);
            add(mTabUserTv);
        }};
        mFragmentList = new ArrayList<Fragment>() {{
            add(new HomeFragment());
            add(new RdFragment());
            add(new UserFragment());
        }};
        setSelectedTab(mTabHomeIv, mTabHomeTv);
        initViewPager();
        EventBus.getDefault().register(this);
        appUpdate();

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

    @OnClick({R.id.activity_main_tab_home_iv, R.id.activity_main_tab_home_tv,
            R.id.activity_main_tab_rd_iv, R.id.activity_main_tab_rd_tv,
            R.id.activity_main_tab_user_iv, R.id.activity_main_tab_user_tv})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_tab_home_iv:
            case R.id.activity_main_tab_home_tv:
                setSelectedTab(mTabHomeIv, mTabHomeTv);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.activity_main_tab_rd_iv:
            case R.id.activity_main_tab_rd_tv:
                setSelectedTab(mTabRdIv, mTabRdTv);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.activity_main_tab_user_iv:
            case R.id.activity_main_tab_user_tv:
                setSelectedTab(mTabUserIv, mTabUserTv);
                mViewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MessageEvent<Intent> message = new MessageEvent<>();
        message.setCommand(requestCode + "");
        message.setData(data);
        EventBus.getDefault().post(message);
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


    public void appUpdate() {

        HttpRequestUtil.getRetrofitService(HomeApi.class).appUpdate().enqueue(new AbsZCallback<AppUpdateModel>() {
            @Override
            public void onSuccess(Call<AppUpdateModel> call, Response<AppUpdateModel> response) {

                if (response.body().getData() == null) {
                    return;
                }

                AppUpdateModel.DataBean.EntityBean data = response.body().getData().getEntity();

                if (data == null) {
                    return;
                }

                int code = Integer.valueOf(data.getCodeX());
                //int code = Integer.valueOf("3");
                if (BuildConfig.VERSION_CODE < code) {
                    showAppUpdateDialog(data.getUpdate_tip(), data.getDownload_url(), data.getMd5());
                }


            }

            @Override
            public void onFail(Call<AppUpdateModel> call, Throwable t) {

            }
        });

    }

    private void showAppUpdateDialog(String msg, final String apkUrl, final String apkMd5) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setTitle("更新")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), UpdateAppService.class);
                        //wifi下
                        intent.putExtra(UpdateAppService.IsNeedWifiDownloading, false);
                        //是否进行安装
                        intent.putExtra(UpdateAppService.TipsInstall, true);
                        intent.putExtra(UpdateAppService.APP_URL, apkUrl);
                        //md5
                        intent.putExtra(UpdateAppService.MD5_KEY, apkMd5.toUpperCase());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(intent);
                        } else {
                            startService(intent);
                        }

                    }
                }).create().show();
    }
}
