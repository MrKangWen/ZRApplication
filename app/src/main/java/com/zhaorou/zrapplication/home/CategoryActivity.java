package com.zhaorou.zrapplication.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseActivity;
import com.zhaorou.zrapplication.base.GlideApp;
import com.zhaorou.zrapplication.home.dialog.PerfectWXCircleDialog;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.FriendPopDetailModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.home.presenter.HomeFragmentPresenter;
import com.zhaorou.zrapplication.search.SearchActivity;
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

public class CategoryActivity extends BaseActivity implements IHomeFragmentView {

    @BindView(R.id.activity_category_swip_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.activity_category_layout_title_center_tv)
    TextView mLayoutTitleTv;
    @BindView(R.id.activity_category_goods_list)
    CustomRecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private String mCategoryName;
    private String mCategoryId;
    private int page = 1;
    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter();
    private List<GoodsListModel.DataBean.ListBean> mGoodsList = new ArrayList<>();
    private GoodsAdapter mGoodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        initIntent();
        initLayoutTitle();
        initSwipLayout();
        initRecyclerView();
        initData();
    }

    @Override
    public void onFetchedClassList(List<ClassListModel.DataBean.ListBean> list) {

    }

    @Override
    public void onFetchDtkGoodsList(List<GoodsListModel.DataBean.ListBean> list) {
        if (page == 1) {
            mGoodsList.clear();
        }
        mGoodsList.addAll(list);
        mGoodsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(boolean loadMore) {
        if (loadMore) {
            doLoadMore();
        } else {
            if (page > 1) {
                Toast.makeText(this, "没有更多了~", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onGetFriendPopDetail(FriendPopDetailModel.DataBean.EntityBean entityBean) {

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

    @OnClick({R.id.activity_category_layout_title_left_btn_rl})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_category_layout_title_left_btn_rl:
                finish();
                break;
            default:
                break;
        }
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mCategoryName = intent.getStringExtra("category_name");
            mCategoryId = intent.getStringExtra("category_id");
        }
    }

    private void initLayoutTitle() {
        mLayoutTitleTv.setText(mCategoryName);
    }

    private void initSwipLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        CustomItemDecoration itemDecoration = new CustomItemDecoration(DisplayUtil.dip2px(this, 10), getResources().getColor(R.color.colorGray_F5F5F5));
        mRecyclerView.addItemDecoration(itemDecoration);
        mGoodsAdapter = new GoodsAdapter();
        mRecyclerView.setAdapter(mGoodsAdapter);
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

    private void initData() {
        page = 1;
        Map<String, String> params = new HashMap<>();
        params.put("type", "classify");
        params.put("cid", mCategoryId);
        params.put("page", page + "");
        mPresenter.fetchGoodsList(params);
    }

    private void doLoadMore() {
        page++;
        Map<String, String> params = new HashMap<>();
        params.put("type", "classify");
        params.put("cid", mCategoryId);
        params.put("page", page + "");
        mPresenter.fetchGoodsList(params);
    }

    public int getLastCompleteVisiblePosition() {
        return mLayoutManager.findLastCompletelyVisibleItemPosition();
    }

    public int getDataCount() {
        return mGoodsList.size();
    }

    private class GoodsAdapter extends RecyclerView.Adapter<GoodsViewHolder> {

        private PerfectWXCircleDialog mPerfectWXCircleDialog;

        @NonNull
        @Override
        public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_goods_list_home_fragment, parent, false);
            GoodsViewHolder goodsViewHolder = new GoodsViewHolder(view);
            return goodsViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull GoodsViewHolder holder, int position) {
            String title = mGoodsList.get(position).getGoods_name();
            holder.mTitleTv.setText(title);

            String pic = mGoodsList.get(position).getPic();
            GlideApp.with(CategoryActivity.this).asBitmap().load(pic).into(holder.mGoodsImageIv);

            String jhs = mGoodsList.get(position).getJhs();
            if (TextUtils.equals(jhs, "1")) {
                holder.mPlatformTv.setText("聚");
            }

            String price = mGoodsList.get(position).getPrice();
            holder.mPriceTv.setText("￥" + price);

            String sales = mGoodsList.get(position).getSales();
            holder.mPayNumberTv.setText(sales + "人付款");

            String price_coupons = mGoodsList.get(position).getPrice_coupons();
            holder.mCouponTv.setText("优惠券" + price_coupons + "元");

            String rate = mGoodsList.get(position).getRate();
            holder.mRateTv.setText("佣金：" + rate + "%");

            String quan_shengyu = mGoodsList.get(position).getQuan_shengyu();
            holder.mRemainderTv.setText("剩余：" + quan_shengyu);

            holder.mRankingFl.setVisibility(View.GONE);

            holder.mBtnPerfectWXCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPerfectWXCircleDialog == null) {
                        mPerfectWXCircleDialog = new PerfectWXCircleDialog(CategoryActivity.this);
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
            return mGoodsList.size();
        }
    }

    private class GoodsViewHolder extends RecyclerView.ViewHolder {
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

        public GoodsViewHolder(View itemView) {
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
