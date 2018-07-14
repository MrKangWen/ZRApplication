package com.zhaorou.zrapplication.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.zhaorou.zrapplication.base.BaseFragment;
import com.zhaorou.zrapplication.base.GlideApp;
import com.zhaorou.zrapplication.home.dialog.PerfectWXCircleDialog;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.home.presenter.HomeFragmentPresenter;
import com.zhaorou.zrapplication.utils.DisplayUtil;
import com.zhaorou.zrapplication.widget.recyclerview.CustomItemDecoration;
import com.zhaorou.zrapplication.widget.recyclerview.CustomRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeVPItemFragment extends BaseFragment implements IHomeFragmentView {

    private static final String TAG = "HomeVPItemFragment";
    @BindView(R.id.goods_list_fragment_home_vp_item_rv)
    CustomRecyclerView mRecyclerView;

    @BindArray(R.array.goods_list_type)
    String[] mGoodsTypeKeys;

    private View mView;
    private Unbinder mUnbinder;
    private GoodsAdapter mGoodsAdapter;
    private List<GoodsListModel.DataBean.ListBean> mGoodsList = new ArrayList();
    private LinearLayoutManager mLayoutManager;
    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter();
    private String mGoodsType;
    private int page = 1;

    public HomeVPItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home_vpitem, container, false);
            mUnbinder = ButterKnife.bind(this, mView);
            initRecyclerView();
        }
        mPresenter.attachView(this);
        initData();
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mPresenter.detachView();
        ViewGroup parent = (ViewGroup) mView.getParent();
        parent.removeAllViews();
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
    public void onShowLoading() {
        HomeFragment.startRefresh();
    }

    @Override
    public void onHideLoading() {
        HomeFragment.finishRefresh();
    }

    @Override
    public void onLoadMore(boolean hasMore) {
        if (hasMore) {
            page++;
            Map<String, String> params = new HashMap<>();
            if (TextUtils.equals(mGoodsType, mGoodsTypeKeys[0])) {
                params.put("flag", mGoodsType);
            } else {
                params.put("type", mGoodsType);
            }
            params.put("page", page + "");
            mPresenter.fetchGoodsList(params);
        } else {
            Toast.makeText(getContext(), "没有更多了~", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        CustomItemDecoration itemDecoration = new CustomItemDecoration(DisplayUtil.dip2px(getContext(), 10), getResources().getColor(R.color.colorGray_F5F5F5));
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

    public void initData() {
        page = 1;
        Bundle arguments = getArguments();
        if (arguments != null) {
            mGoodsType = arguments.getString("goods_type");
            Map<String, String> params = new HashMap<>();
            if (TextUtils.equals(mGoodsType, mGoodsTypeKeys[0])) {
                params.put("flag", mGoodsType);
            } else {
                params.put("type", mGoodsType);
            }
            params.put("page", page + "");
            mPresenter.fetchGoodsList(params);
        }
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
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_goods_list_home_fragment, parent, false);
            GoodsViewHolder goodsViewHolder = new GoodsViewHolder(view);
            return goodsViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull GoodsViewHolder holder, int position) {
            String title = mGoodsList.get(position).getGoods_name();
            holder.mTitleTv.setText(title);

            String pic = mGoodsList.get(position).getPic();
            GlideApp.with(HomeVPItemFragment.this).asBitmap().load(pic).into(holder.mGoodsImageIv);

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

            if (TextUtils.equals(mGoodsType, mGoodsTypeKeys[0])) {
                holder.mRankingFl.setVisibility(View.VISIBLE);
                String sort = mGoodsList.get(position).getSort();
                if (!TextUtils.isEmpty(sort)) {
                    int sortInt = Integer.valueOf(sort) + 1;
                    holder.mRankingTv.setText(sortInt + "");
                }
            } else {
                holder.mRankingFl.setVisibility(View.GONE);
            }

            holder.mBtnPerfectWXCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPerfectWXCircleDialog == null) {
                        mPerfectWXCircleDialog = new PerfectWXCircleDialog(getContext());
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
        private TextView mRankingTv;
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
            mRankingTv = itemView.findViewById(R.id.item_goods_list_ranking_tv);
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
