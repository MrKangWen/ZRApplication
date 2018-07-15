package com.zhaorou.zrapplication.home;


import android.Manifest;
import android.content.Intent;
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
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.home.dialog.PerfectWXCircleDialog;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.FriendPopDetailModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.home.presenter.HomeFragmentPresenter;
import com.zhaorou.zrapplication.login.LoginActivity;
import com.zhaorou.zrapplication.utils.DisplayUtil;
import com.zhaorou.zrapplication.utils.SharedPreferenceHelper;
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
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeVPItemFragment extends BaseFragment implements IHomeFragmentView, EasyPermissions.PermissionCallbacks {

    private static final String TAG = "HomeVPItemFragment";
    @BindView(R.id.goods_list_fragment_home_vp_item_rv)
    CustomRecyclerView mRecyclerView;

    @BindArray(R.array.goods_list_type)
    String[] mGoodsTypeKeys;


    private View mView;

    private Unbinder mUnbinder;
    private LinearLayoutManager mLayoutManager;
    private GoodsAdapter mGoodsAdapter;
    private List<GoodsListModel.DataBean.ListBean> mGoodsList = new ArrayList();
    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter();
    private String mGoodsType;
    private int page = 1;
    private GoodsListModel.DataBean.ListBean mGoodsBean;

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
        Bundle arguments = getArguments();
        if (arguments != null) {
            mGoodsType = arguments.getString("goods_type");
        }
        initData();
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mPresenter.detachView();
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
                Toast.makeText(getContext(), "没有更多了~", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onGetFriendPopDetail(FriendPopDetailModel.DataBean.EntityBean entityBean) {
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
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        showDialog(mGoodsBean);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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
        Map<String, String> params = new HashMap<>();
        if (TextUtils.equals(mGoodsType, mGoodsTypeKeys[0])) {
            params.put("flag", mGoodsType);
        } else {
            params.put("type", mGoodsType);
        }
        params.put("page", page + "");
        mPresenter.fetchGoodsList(params);
    }

    private void doLoadMore() {
        page++;
        Map<String, String> params = new HashMap<>();
        if (TextUtils.equals(mGoodsType, mGoodsTypeKeys[0])) {
            params.put("flag", mGoodsType);
        } else {
            params.put("type", mGoodsType);
        }
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


        @NonNull
        @Override
        public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_goods_list_home_fragment, parent, false);
            GoodsViewHolder goodsViewHolder = new GoodsViewHolder(view);
            return goodsViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull GoodsViewHolder holder, final int position) {
            final GoodsListModel.DataBean.ListBean goodsBean = mGoodsList.get(position);
            mGoodsBean = goodsBean;
            String title = goodsBean.getGoods_name();
            holder.mTitleTv.setText(title);

            String pic = goodsBean.getPic();
            GlideApp.with(HomeVPItemFragment.this).asBitmap().load(pic).into(holder.mGoodsImageIv);

            String jhs = goodsBean.getJhs();
            if (TextUtils.equals(jhs, "1")) {
                holder.mPlatformTv.setText("聚");
            }

            String price = goodsBean.getPrice();
            holder.mPriceTv.setText("￥" + price);

            String sales = goodsBean.getSales();
            holder.mPayNumberTv.setText(sales + "人付款");

            String price_coupons = goodsBean.getPrice_coupons();
            holder.mCouponTv.setText("优惠券" + price_coupons + "元");

            String rate = goodsBean.getRate();
            holder.mRateTv.setText("佣金：" + rate + "%");

            String quan_shengyu = goodsBean.getQuan_shengyu();
            holder.mRemainderTv.setText("剩余：" + quan_shengyu);

            if (TextUtils.equals(mGoodsType, mGoodsTypeKeys[0])) {
                holder.mRankingFl.setVisibility(View.VISIBLE);
                String sort = goodsBean.getSort();
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
                    String token = SharedPreferenceHelper.getString(getContext(), ZRDConstants.SharedPreferenceKey.SP_LOGIN_TOKEN, "");
                    if (TextUtils.isEmpty(token)) {
                        Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    } else {
                        String[] permissons = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        boolean hasPermissions = EasyPermissions.hasPermissions(getContext(), permissons);
                        if (hasPermissions) {
                            showDialog(goodsBean);
                        } else {
                            EasyPermissions.requestPermissions(HomeVPItemFragment.this, "需要读取储存权限", 0, permissons);
                        }
                    }
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

    private void showDialog(GoodsListModel.DataBean.ListBean goodsBean) {
        PerfectWXCircleDialog perfectWXCircleDialog = new PerfectWXCircleDialog(getContext());
        perfectWXCircleDialog.show();
        perfectWXCircleDialog.setGoodsInfo(goodsBean);
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
