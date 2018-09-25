package com.zhaorou.zrapplication.user.msg;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseActivity;
import com.zhaorou.zrapplication.widget.recyclerview.TabLayoutPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 6、获取未读/已读/系统公告列表
 *
 * @author kang
 */
public class MsgActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        initView();
    }

    private void initView() {
        setTitle("消息中心");
        List<Fragment> listFragment = new ArrayList<>();
        listFragment.add(new UnReadFragment());
        listFragment.add(new HasReadFragment());
        listFragment.add(new SystemMsgFragment());
        List<String> listTitle = new ArrayList<>();
        listTitle.add("未读");
        listTitle.add("已读");
        listTitle.add("系统公告");
        TabLayoutPagerAdapter tabLayoutPagerAdapter = new TabLayoutPagerAdapter(getSupportFragmentManager(), listFragment, listTitle);

        ViewPager viewPager = findViewById(R.id.msgViewPager);
        viewPager.setAdapter(tabLayoutPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.msgTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }
}
