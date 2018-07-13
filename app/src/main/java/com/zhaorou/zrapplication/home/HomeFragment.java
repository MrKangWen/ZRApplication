package com.zhaorou.zrapplication.home;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseFragment;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.DtkGoodsListModel;
import com.zhaorou.zrapplication.home.model.HomeTabModel;
import com.zhaorou.zrapplication.home.presenter.HomeFragmentPresenter;
import com.zhaorou.zrapplication.utils.ScreenInfoHelper;
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

    @BindView(R.id.fragment_home_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fragment_home_action_bar_search_et)
    TextView mSearchEt;
    @BindView(R.id.fragment_home_action_bar_right_btn_fl)
    FrameLayout mActionBarBtnRight;
    @BindView(R.id.fragment_home_action_bar_right_btn_cancel_tv)
    TextView mActionBarBtnCancel;
    @BindView(R.id.fragment_home_action_bar_right_btn_menu_iv)
    ImageView mActionBarBtnMenu;
    @BindView(R.id.fragment_home_tab_rv)
    CustomRecyclerView mTabRv;
    @BindView(R.id.fragment_home_tab_indicator_tv)
    TextView mIndicatorTv;
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

    private View mView;
    private Unbinder mUnbinder;
    private int mScreenWidth;
    private TabAdapter mTabAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mTabItemWidth;
    private float mLastOffset;
    private int mChildCount;
    private List<HomeTabModel> mTabsList = new ArrayList<>();
    private ClassListAdapter mClassListAdapter;
    private ViewPagerAdapter mPagerAdapter;
    private List<ClassListModel.DataBean.ListBean> mClassList = new ArrayList<>();
    private List<DtkGoodsListModel.DataBean.ListBean> mGoodsList = new ArrayList<>();
    private List<HomeVPItemFragment> mFragmentList = new ArrayList<>();
    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter();
    private Handler mHandler = new Handler();

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
            mUnbinder = ButterKnife.bind(this, mView);
            mSwipeRefreshLayout.setEnabled(false);
            initClassListRv();
            initTabs();
            initViewPager();
        }
        mPresenter.attachView(this);
        mPresenter.fetchClassList();
        mPresenter.fetchDtkGoodsList();
        return mView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mViewPager.removeAllViews();
        mPresenter.detachView();
    }

    private void initTabs() {
        for (int i = 0; i < mTabsTitleArray.length; i++) {
            String tabTitle = mTabsTitleArray[i];
            HomeTabModel homeTabModel = new HomeTabModel();
            homeTabModel.setTabTitle(tabTitle);
            if (i == 0) {
                homeTabModel.setSelected(true);
            } else {
                homeTabModel.setSelected(false);
            }
            mTabsList.add(homeTabModel);
        }
        mScreenWidth = ScreenInfoHelper.getScreenWidthPixels(getContext());
        mTabItemWidth = mScreenWidth / 4;
        mIndicatorTv.setWidth(mTabItemWidth);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mTabRv.setLayoutManager(mLayoutManager);
        mTabAdapter = new TabAdapter();
        mTabRv.setAdapter(mTabAdapter);
    }

    private void initClassListRv() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        mClassListRv.setLayoutManager(gridLayoutManager);
        mClassListAdapter = new ClassListAdapter();
        mClassListRv.setAdapter(mClassListAdapter);

    }

    private void initViewPager() {
        for (int i = 0; i < mTabsTitleArray.length; i++) {
            mFragmentList.add(new HomeVPItemFragment());
        }
        FragmentManager fm = getActivity().getSupportFragmentManager();
        mPagerAdapter = new ViewPagerAdapter(fm);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset != 0) {
            if (positionOffset - mLastOffset > 0) {
                if (position >= mChildCount - 1) {
                    int firstPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                    int offset = (int) ((positionOffset + (position - firstPosition)) * mTabItemWidth);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIndicatorTv.getLayoutParams();
                    params.leftMargin = offset;
                    mIndicatorTv.setLayoutParams(params);
                } else {
                    int offset = (int) ((positionOffset + position) * mTabItemWidth);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIndicatorTv.getLayoutParams();
                    params.leftMargin = offset;
                    mIndicatorTv.setLayoutParams(params);
                }
            } else {
                int firstPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstPosition == 0) {
                    int offset = (int) ((positionOffset + position) * mTabItemWidth);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIndicatorTv.getLayoutParams();
                    params.leftMargin = offset;
                    mIndicatorTv.setLayoutParams(params);
                } else {
                    int offset = (int) ((positionOffset + (position - firstPosition)) * mTabItemWidth);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIndicatorTv.getLayoutParams();
                    params.leftMargin = offset;
                    mIndicatorTv.setLayoutParams(params);
                }
            }
            mLastOffset = positionOffset;
        }

    }

    @Override
    public void onPageSelected(int position) {
        mChildCount = mTabRv.getChildCount();
        for (int i = 0; i < mTabsList.size(); i++) {
            HomeTabModel homeTabModel = mTabsList.get(i);
            if (i == position) {
                homeTabModel.setSelected(true);
            } else {
                homeTabModel.setSelected(false);
            }
        }
        mTabAdapter.notifyDataSetChanged();
        int lastPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
        int firstPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        if (position < firstPosition || position > lastPosition) {
            mTabRv.scrollToPosition(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onFetchedClassList(List<ClassListModel.DataBean.ListBean> list) {
        mClassList.clear();
        mClassList.addAll(list);
        mClassListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFetchDtkGoodsList(List<DtkGoodsListModel.DataBean.ListBean> list) {
        mGoodsList.clear();
        mGoodsList.addAll(list);
        int pos = mViewPager.getCurrentItem();
        mFragmentList.get(pos).notifyDataSetChanged(mGoodsList);
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    @OnClick({R.id.fragment_home_action_bar_right_btn_menu_iv, R.id.fragment_home_class_list_btn_close_tv,
            R.id.fragment_home_class_list_root_layout_ll})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_home_action_bar_right_btn_menu_iv:
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

    private void showClassList() {
        mClassListRootLayoutLl.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(mClassListContentLayoutLl, "translationY", -mClassListContentLayoutLl.getHeight(), mTabRv.getTop()).setDuration(250).start();
        ObjectAnimator.ofFloat(mClassListRootLayoutLl, "alpha", 0, 1).setDuration(200).start();
    }

    private void dismissClassList() {
        ObjectAnimator.ofFloat(mClassListContentLayoutLl, "translationY", mTabRv.getTop(), -mClassListContentLayoutLl.getHeight()).setDuration(250).start();
        ObjectAnimator.ofFloat(mClassListRootLayoutLl, "alpha", 1, 0).setDuration(200).start();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mClassListRootLayoutLl.setVisibility(View.GONE);
            }
        }, 250);
    }

    private class TabAdapter extends RecyclerView.Adapter<TabViewHolder> {

        @NonNull
        @Override
        public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_view_simple_text, parent, false);
            return new TabViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TabViewHolder holder, final int position) {
            HomeTabModel homeTabModel = mTabsList.get(position);
            String tabTitle = homeTabModel.getTabTitle();
            boolean selected = homeTabModel.isSelected();
            holder.mTabTitleTv.setText(tabTitle);
            if (selected) {
                holder.mTabTitleTv.setTextColor(getResources().getColor(R.color.colorRed_FF2200));
            } else {
                holder.mTabTitleTv.setTextColor(getResources().getColor(R.color.colorBlack_333333));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTabsTitleArray.length;
        }
    }

    private class TabViewHolder extends RecyclerView.ViewHolder {

        private TextView mTabTitleTv;

        public TabViewHolder(View itemView) {
            super(itemView);
            mTabTitleTv = itemView.findViewById(R.id.item_recycler_view_simple_text_tv);
            mTabTitleTv.setWidth(mTabItemWidth);
        }
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
            holder.mClassNameTv.setText(mClassList.get(position).getClassname());
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
