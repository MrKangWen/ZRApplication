package com.zhaorou.zrapplication.home;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseFragment;
import com.zhaorou.zrapplication.home.dialog.LoadingDialog;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.FriendPopDetailModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.home.presenter.HomeFragmentPresenter;
import com.zhaorou.zrapplication.search.SearchActivity;
import com.zhaorou.zrapplication.widget.recyclerview.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, IHomeFragmentView {

    private static final String TAG = "HomeFragment";

    private static SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.fragment_home_layout_title_search_et)
    TextView mSearchEt;
    @BindView(R.id.fragment_home_layout_title_right_btn_fl)
    FrameLayout mActionBarBtnRight;
    @BindView(R.id.fragment_home_layout_title_right_tv)
    TextView mActionBarBtnCancel;
    @BindView(R.id.fragment_home_layout_title_right_iv)
    ImageView mActionBarBtnMenu;
    @BindView(R.id.fragment_home_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.fragment_home_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.fragment_home_class_list_root_layout_ll)
    RelativeLayout mClassListRootLayoutLl;
    @BindView(R.id.fragment_home_class_list_content_layout_ll)
    LinearLayout mClassListContentLayoutLl;
    @BindView(R.id.fragment_home_class_list_rv)
    CustomRecyclerView mClassListRv;

    @BindArray(R.array.fragment_home_tab)
    String[] mTabsTitleArray;
    @BindArray(R.array.goods_list_type)
    String[] mGoodsTypeKeys;

    private View mView;
    private Unbinder mUnbinder;
    private ClassListAdapter mClassListAdapter;
    private ViewPagerAdapter mPagerAdapter;
    private List<HomeVPItemFragment> mFragmentList = new ArrayList<>();
    private List<ClassListModel.DataBean.ListBean> mClassList = new ArrayList<>();
    private Handler mHandler = new Handler();
    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter();
    private LoadingDialog mLoadingDialog;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
            mUnbinder = ButterKnife.bind(this, mView);
            mLoadingDialog = new LoadingDialog(getContext());
            initSwipLayout();
            initClassListRv();
            initViewPager();
            initTabs();
        }
        mPresenter.attachView(this);
        mPresenter.fetchClassList();
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mPresenter.detachView();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onLoadFail(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFetchedClassList(List<ClassListModel.DataBean.ListBean> list) {
        mClassList.clear();
        mClassList.addAll(list);
        mClassListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFetchDtkGoodsList(List<GoodsListModel.DataBean.ListBean> list) {

    }

    @Override
    public void onShowLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void onHideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }


    @Override
    public void onLoadMore(boolean hasMore) {

    }

    @Override
    public void onGetFriendPopDetail(FriendPopDetailModel.DataBean.EntityBean entityBean) {

    }

    @Override
    public void onGetTaowords(String tkl) {

    }

    @OnClick({R.id.fragment_home_layout_title_search_et, R.id.fragment_home_layout_title_right_iv, R.id.fragment_home_class_list_btn_close_tv,
            R.id.fragment_home_class_list_root_layout_ll})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_home_layout_title_search_et:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.fragment_home_layout_title_right_iv:
                if (mClassListRootLayoutLl.getVisibility() != View.VISIBLE) {
                    showClassList();
                } else {
                    dismissClassList();
                }
                break;
            case R.id.fragment_home_class_list_root_layout_ll:
            case R.id.fragment_home_class_list_btn_close_tv:
                if (mClassListRootLayoutLl.getVisibility() == View.VISIBLE) {
                    dismissClassList();
                }
                break;
            default:
                break;
        }
    }

    private void initSwipLayout() {
        mSwipeRefreshLayout = mView.findViewById(R.id.fragment_home_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFragmentList.get(mViewPager.getCurrentItem()).initData();
            }
        });
    }

    private void initTabs() {

        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabsTitleArray.length; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            TextView tabTextTv = (TextView) getLayoutInflater().inflate(R.layout.item_home_tab, mTabLayout, false);
            tabTextTv.setText(mTabsTitleArray[i]);
            tab.setCustomView(tabTextTv);
        }
    }

    private void initClassListRv() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        mClassListRv.setLayoutManager(gridLayoutManager);
        mClassListAdapter = new ClassListAdapter();
        mClassListRv.setAdapter(mClassListAdapter);

    }

    private void initViewPager() {
        for (int i = 0; i < mTabsTitleArray.length; i++) {
            HomeVPItemFragment homeVPItemFragment = new HomeVPItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("goods_type", mGoodsTypeKeys[i]);
            homeVPItemFragment.setArguments(bundle);
            mFragmentList.add(homeVPItemFragment);
        }
        FragmentManager fm = getActivity().getSupportFragmentManager();
        mPagerAdapter = new ViewPagerAdapter(fm);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
    }

    public static void startRefresh() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    public static void finishRefresh() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void showClassList() {
        mClassListRootLayoutLl.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(mClassListContentLayoutLl, "translationY", -mClassListContentLayoutLl.getHeight(), mTabLayout.getTop()).setDuration(250).start();
        ObjectAnimator.ofFloat(mClassListRootLayoutLl, "alpha", 0, 1).setDuration(200).start();
    }

    private void dismissClassList() {
        ObjectAnimator.ofFloat(mClassListContentLayoutLl, "translationY", mTabLayout.getTop(), -mClassListContentLayoutLl.getHeight()).setDuration(250).start();
        ObjectAnimator.ofFloat(mClassListRootLayoutLl, "alpha", 1, 0).setDuration(200).start();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mClassListRootLayoutLl.setVisibility(View.GONE);
            }
        }, 250);
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
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

    private class ClassListAdapter extends RecyclerView.Adapter<ClassListHolder> {

        @NonNull
        @Override
        public ClassListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_class_list_fragment_home, parent, false);
            ClassListHolder classListHolder = new ClassListHolder(view);
            return classListHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ClassListHolder holder, int position) {
            final String classname = mClassList.get(position).getClassname();
            final String id = mClassList.get(position).getId();
            holder.mClassNameTv.setText(classname);
            holder.mClassNameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CategoryActivity.class);
                    intent.putExtra("category_name", classname);
                    intent.putExtra("category_id", id);
                    startActivity(intent);
                    dismissClassList();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mClassList.size();
        }
    }

    private class ClassListHolder extends RecyclerView.ViewHolder {

        public TextView mClassNameTv;

        public ClassListHolder(View itemView) {
            super(itemView);
            mClassNameTv = itemView.findViewById(R.id.item_class_list_fragment_home_cname_tv);
        }
    }
}
