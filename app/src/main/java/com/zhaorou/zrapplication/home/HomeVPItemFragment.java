package com.zhaorou.zrapplication.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseFragment;
import com.zhaorou.zrapplication.utils.DisplayUtil;
import com.zhaorou.zrapplication.widget.recyclerview.CustomItemDecoration;
import com.zhaorou.zrapplication.widget.recyclerview.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeVPItemFragment extends BaseFragment {

    @BindView(R.id.goods_list_fragment_home_vp_item_rv)
    CustomRecyclerView mRecyclerView;

    private View mView;
    private Unbinder mUnbinder;
    private GoodsAdapter mGoodsAdapter;
    private List mGoodsList = new ArrayList();

    public HomeVPItemFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home_vpitem, container, false);
            mUnbinder = ButterKnife.bind(this, mView);
        }
        initRecyclerView();
        return mView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        CustomItemDecoration itemDecoration = new CustomItemDecoration(DisplayUtil.dip2px(getContext(), 10), getResources().getColor(R.color.colorGray_F5F5F5));
        mRecyclerView.addItemDecoration(itemDecoration);
        mGoodsAdapter = new GoodsAdapter();
        mRecyclerView.setAdapter(mGoodsAdapter);
    }

    private class GoodsAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_goods_list_home_fragment, parent, false);
            GoodsViewHolder goodsViewHolder = new GoodsViewHolder(view);
            return goodsViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    private class GoodsViewHolder extends RecyclerView.ViewHolder {
        private TextView mRankingTv;
        private ImageView mGoodsImageIv;
        private TextView mPlatformTv;
        private TextView mTitleTv;
        private TextView mCouponTv;
        private TextView mPriceTv;
        private TextView mPayNumberTv;
        private TextView mCommissionTv;
        private TextView mRemainderTv;
        private RelativeLayout mBtnShareWXRl;
        private TextView mBtnPerfectWXCircle;
        private TextView mBtnCopyWords;
        private TextView mBtnShareWXTv;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            mRankingTv = itemView.findViewById(R.id.item_goods_list_ranking_tv);
            mGoodsImageIv = itemView.findViewById(R.id.item_goods_list_goods_image_iv);
            mPlatformTv = itemView.findViewById(R.id.item_goods_list_platform_tv);
            mTitleTv = itemView.findViewById(R.id.item_goods_list_title_tv);
            mCouponTv = itemView.findViewById(R.id.item_goods_list_coupon_tv);
            mPriceTv = itemView.findViewById(R.id.item_goods_list_price_tv);
            mPayNumberTv = itemView.findViewById(R.id.item_goods_list_pay_number_tv);
            mCommissionTv = itemView.findViewById(R.id.item_goods_list_commission_tv);
            mRemainderTv = itemView.findViewById(R.id.item_goods_list_remainder_tv);
            mBtnShareWXRl = itemView.findViewById(R.id.item_goods_list_btn_share_wx_rl);
            mBtnPerfectWXCircle = itemView.findViewById(R.id.item_goods_list_btn_perfect_wx_circle);
            mBtnCopyWords = itemView.findViewById(R.id.item_goods_list_btn_copy_words);
            mBtnShareWXTv = itemView.findViewById(R.id.item_goods_list_btn_share_wx_tv);
        }
    }
}
