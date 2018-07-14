package com.zhaorou.zrapplication.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseActivity;
import com.zhaorou.zrapplication.base.GlideApp;
import com.zhaorou.zrapplication.home.HomeVPItemFragment;
import com.zhaorou.zrapplication.home.dialog.PerfectWXCircleDialog;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.search.presenter.SearchPresenter;
import com.zhaorou.zrapplication.utils.DisplayUtil;
import com.zhaorou.zrapplication.widget.recyclerview.CustomItemDecoration;
import com.zhaorou.zrapplication.widget.recyclerview.CustomRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements ISearchView {

    @BindView(R.id.activity_search_swip_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.activity_search_action_bar_right_btn_cancel_tv)
    TextView mActionBarRightTv;
    @BindView(R.id.activity_search_action_bar_right_btn_fl)
    FrameLayout mBtnActionBarRight;
    @BindView(R.id.activity_search_action_bar_search_et)
    EditText mSearchEt;
    @BindView(R.id.activity_search_goods_list_rv)
    CustomRecyclerView mRecyclerView;

    private int page = 1;
    private List<GoodsListModel.DataBean.ListBean> mSearchList = new ArrayList<>();
    private SearchPresenter mPresenter = new SearchPresenter();
    private LinearLayoutManager mLayoutManager;
    private SearchAdapter mSearchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        initSearchEditText();
        initSwipLayout();
        initRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onSearchResult(List<GoodsListModel.DataBean.ListBean> list) {
        if (page == 1) {
            mSearchList.clear();
        }
        mSearchList.addAll(list);
        mSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(boolean loadMore) {
        if (loadMore) {
            String keyword = mSearchEt.getText().toString();
            doLoadMore(keyword);
        } else {
            if (page > 1) {
                Toast.makeText(this, "没有更多了~", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @OnClick({R.id.activity_search_action_bar_right_btn_fl})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_search_action_bar_right_btn_fl:
                finish();
                break;
        }
    }

    private void initSearchEditText() {
        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mSearchList.clear();
                    mSearchAdapter.notifyDataSetChanged();
                } else {
                    String keyword = s.toString();
                    doSearch(keyword);

                }
            }
        });
    }

    private void initSwipLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String keyword = mSearchEt.getText().toString();
                if (!TextUtils.isEmpty(keyword)) {
                    doSearch(keyword);
                }
            }
        });
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        CustomItemDecoration itemDecoration = new CustomItemDecoration(DisplayUtil.dip2px(this, 10), getResources().getColor(R.color.colorGray_F5F5F5));
        mRecyclerView.addItemDecoration(itemDecoration);
        mSearchAdapter = new SearchAdapter();
        mRecyclerView.setAdapter(mSearchAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastCompleteVisiblePosition = getLastCompleteVisiblePosition();
                int dataCount = getDataCount();
                if (lastCompleteVisiblePosition == dataCount - 1) {
                    onLoadMore(true);
                }
            }
        });
    }

    private void doSearch(String keyword) {
        page = 1;
        Map<String, String> params = new HashMap<>();
        params.put("type", "so");
        params.put("keyword", keyword);
        params.put("page", page + "");
        mPresenter.doSearch(params);
    }

    private void doLoadMore(String keyword) {
        page++;
        Map<String, String> params = new HashMap<>();
        params.put("type", "so");
        params.put("keyword", keyword);
        params.put("page", page + "");
        mPresenter.doSearch(params);
    }

    public int getLastCompleteVisiblePosition() {
        return mLayoutManager.findLastCompletelyVisibleItemPosition();
    }

    public int getDataCount() {
        return mSearchList.size();
    }

    private class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

        private PerfectWXCircleDialog mPerfectWXCircleDialog;

        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_goods_list_home_fragment, parent, false);
            SearchViewHolder goodsViewHolder = new SearchViewHolder(view);
            return goodsViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            String title = mSearchList.get(position).getGoods_name();
            holder.mTitleTv.setText(title);

            String pic = mSearchList.get(position).getPic();
            GlideApp.with(SearchActivity.this).asBitmap().load(pic).into(holder.mGoodsImageIv);

            String jhs = mSearchList.get(position).getJhs();
            if (TextUtils.equals(jhs, "1")) {
                holder.mPlatformTv.setText("聚");
            }

            String price = mSearchList.get(position).getPrice();
            holder.mPriceTv.setText("￥" + price);

            String sales = mSearchList.get(position).getSales();
            holder.mPayNumberTv.setText(sales + "人付款");

            String price_coupons = mSearchList.get(position).getPrice_coupons();
            holder.mCouponTv.setText("优惠券" + price_coupons + "元");

            String rate = mSearchList.get(position).getRate();
            holder.mRateTv.setText("佣金：" + rate + "%");

            String quan_shengyu = mSearchList.get(position).getQuan_shengyu();
            holder.mRemainderTv.setText("剩余：" + quan_shengyu);

            holder.mRankingFl.setVisibility(View.GONE);

            holder.mBtnPerfectWXCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPerfectWXCircleDialog == null) {
                        mPerfectWXCircleDialog = new PerfectWXCircleDialog(SearchActivity.this);
                    }
                    mPerfectWXCircleDialog.show();
                }
            });
            holder.mBtnCopyWords.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        @Override
        public int getItemCount() {
            return mSearchList.size();
        }
    }

    private class SearchViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout mRankingFl;
        private ImageView mGoodsImageIv;
        private TextView mPlatformTv;
        private TextView mTitleTv;
        private TextView mCouponTv;
        private TextView mPriceTv;
        private TextView mPayNumberTv;
        private TextView mRateTv;
        private TextView mRemainderTv;
        private RelativeLayout mBtnShareWXRl;
        private TextView mBtnPerfectWXCircle;
        private TextView mBtnCopyWords;
        private TextView mBtnShareWXTv;

        public SearchViewHolder(View itemView) {
            super(itemView);
            mRankingFl = itemView.findViewById(R.id.item_goods_list_ranking_fl);
            mGoodsImageIv = itemView.findViewById(R.id.item_goods_list_goods_image_iv);
            mPlatformTv = itemView.findViewById(R.id.item_goods_list_platform_tv);
            mTitleTv = itemView.findViewById(R.id.item_goods_list_title_tv);
            mCouponTv = itemView.findViewById(R.id.item_goods_list_coupon_tv);
            mPriceTv = itemView.findViewById(R.id.item_goods_list_price_tv);
            mPayNumberTv = itemView.findViewById(R.id.item_goods_list_pay_number_tv);
            mRateTv = itemView.findViewById(R.id.item_goods_list_rate_tv);
            mRemainderTv = itemView.findViewById(R.id.item_goods_list_remainder_tv);
            mBtnShareWXRl = itemView.findViewById(R.id.item_goods_list_btn_share_wx_rl);
            mBtnPerfectWXCircle = itemView.findViewById(R.id.item_goods_list_btn_perfect_wx_circle);
            mBtnCopyWords = itemView.findViewById(R.id.item_goods_list_btn_copy_words);
            mBtnShareWXTv = itemView.findViewById(R.id.item_goods_list_btn_share_wx_tv);
        }
    }
}
