package com.zhuanquan.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zhuanquan.R;
import com.zhuanquan.interfaces.IBaseView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity implements IBaseView {

    @BindView(R.id.swipe_refresh_layout_base_activity)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        initContentView();
        onViewCreated();
    }

    private void initContentView() {
        int layoutId = setContentLayout();
        View view = getLayoutInflater().inflate(layoutId, null);
        mSwipeRefreshLayout.addView(view);
    }

    @Override
    public int setContentLayout() {
        return 0;
    }

    @Override
    public void onViewCreated() {
    }
}
