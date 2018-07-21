package com.zhaorou.zrapplication.home;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.home.dialog.PerfectWXCircleDialog;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.FriendPopDetailModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.home.model.TaowordsModel;
import com.zhaorou.zrapplication.home.presenter.HomeFragmentPresenter;
import com.zhaorou.zrapplication.login.LoginActivity;
import com.zhaorou.zrapplication.search.SearchActivity;
import com.zhaorou.zrapplication.utils.DisplayUtil;
import com.zhaorou.zrapplication.utils.FileUtils;
import com.zhaorou.zrapplication.utils.SPreferenceUtil;
import com.zhaorou.zrapplication.widget.recyclerview.CustomItemDecoration;
import com.zhaorou.zrapplication.widget.recyclerview.CustomRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

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

    private GoodsListModel.DataBean.ListBean mGoodsBean;
    private String mShareType;
    private String mTaoword;

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
        shareFriendPopToWx(entityBean);
    }

    private void shareFriendPopToWx(FriendPopDetailModel.DataBean.EntityBean entityBean) {
        final List<String> list = new ArrayList<>();
        if (entityBean != null) {
            String imageStr = entityBean.getImage();
            if (!TextUtils.isEmpty(imageStr)) {
                if (imageStr.contains("#")) {
                    String[] imageArray = imageStr.split("#");
                    for (String img : imageArray) {
                        list.add(ZRDConstants.HttpUrls.BASE_URL + img);
                    }
                } else {
                    list.add(ZRDConstants.HttpUrls.BASE_URL + imageStr);
                }
            } else {
                list.add(mGoodsBean.getPic());
            }
        } else {
            list.add(mGoodsBean.getPic());
        }

        final List<File> fileList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String imgUrl : list) {
                    File file = FileUtils.saveImageToSdCard(CategoryActivity.this, imgUrl);
                    fileList.add(file);
                }
                Intent intent = new Intent();
                ComponentName comp = null;
                if (TextUtils.equals(mShareType, "WX")) {
                    comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                }
                if (TextUtils.equals(mShareType, "WX_CIRCLE")) {
                    comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                    intent.putExtra("Kdescription", mTaoword);
                }
                intent.setComponent(comp);
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*");
                ArrayList<Uri> imgUriList = new ArrayList<>();
                for (File file : fileList) {
                    imgUriList.add(Uri.fromFile(file));
                }
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imgUriList);
                startActivity(intent);
            }
        }).start();
    }

    @Override
    public void onGetTaowords(String tkl) {
        String tklType = SPreferenceUtil.getString(CategoryActivity.this, ZRDConstants.SPreferenceKey.SP_LINK_TAO, "1");
        String title = mGoodsBean.getTitle();
        String goods_name = mGoodsBean.getGoods_name();
        String price = mGoodsBean.getPrice();
        String price_after_coupons = mGoodsBean.getPrice_after_coupons();
        String content = mGoodsBean.getQuan_guid_content();
        if (TextUtils.equals(mShareType, "TKL")) {
            shareTKL(tkl, tklType, title, goods_name, price, price_after_coupons);
        }
        if (TextUtils.equals(mShareType, "WX")) {
            shareWX(tkl, tklType, goods_name, price, price_after_coupons, content);
        }
        if (TextUtils.equals(mShareType, "WX_CIRCLE")) {
            shareWX(tkl, tklType, goods_name, price, price_after_coupons, content);
        }
    }

    private void shareWX(String tkl, String tklType, String goods_name, String price, String price_after_coupons, String content) {
        mTaoword = goods_name + "\n" + content + "\n" + "原价 " + price + "\n" + "券后 " +
                price_after_coupons + "\n" +
                "--------抢购方式--------" + "\n";
        if (TextUtils.equals(tklType, "1")) {
            mTaoword = mTaoword + "复制本信息" + tkl + "打开淘宝即可获取";
        } else if (TextUtils.equals(tklType, "2")) {
            String pic = mGoodsBean.getPic();
            String str = "https://wenan001.kuaizhan.com/?taowords=";
            mTaoword = mTaoword + "打开链接\n" + str + tkl.substring(1, tkl.length() - 1) + "&pic=" + pic;
        }
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("taoword", mTaoword);
        cm.setPrimaryClip(clipData);
        Toast.makeText(CategoryActivity.this, "已复制文案，正在启动微信，请稍后...", Toast.LENGTH_SHORT).show();

        Map<String, String> params = new HashMap<>();
        params.put("goods_id", mGoodsBean.getGoods_id());
        mPresenter.getFriendPopDetail(params);
    }

    private void shareTKL(String tkl, String tklType, String title, String goods_name, String price, String price_after_coupons) {

        String taoword = title + "\n" + goods_name + "\n" + "原价 " + price + "\n" + "券后 " +
                price_after_coupons + "\n" +
                "--------抢购方式--------" + "\n";
        if (TextUtils.equals(tklType, "1")) {
            taoword = taoword + "复制本信息" + tkl + "打开淘宝即可获取";
        } else if (TextUtils.equals(tklType, "2")) {
            String pic = mGoodsBean.getPic();
            String str = "https://wenan001.kuaizhan.com/?taowords=";
            taoword = taoword + "打开链接\n" + str + tkl.substring(1, tkl.length() - 1) + "&pic=" + pic;
        }
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("tkl", taoword);
        cm.setPrimaryClip(clipData);
        Toast.makeText(CategoryActivity.this, "淘口令已复制", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onLoginTimeout() {
        Toast.makeText(this, "登录已过期，请重新登录", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
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
        public void onBindViewHolder(@NonNull GoodsViewHolder holder, final int position) {
            GoodsListModel.DataBean.ListBean goodsBean = mGoodsList.get(position);
            mGoodsBean = goodsBean;
            String title = goodsBean.getGoods_name();
            holder.mTitleTv.setText(title);

            String pic = goodsBean.getPic();
            GlideApp.with(CategoryActivity.this).asBitmap().load(pic).into(holder.mGoodsImageIv);

            String isTmall = goodsBean.getIsTmall();
            if (TextUtils.equals(isTmall, "1")) {
                holder.mPlatformTv.setText("天猫");
                holder.mPlatformTv.setVisibility(View.VISIBLE);
            }

            String jhs = goodsBean.getJhs();
            if (TextUtils.equals(jhs, "1")) {
                holder.mPlatformTv.setText("聚");
                holder.mPlatformTv.setVisibility(View.VISIBLE);
            }

            String price = goodsBean.getPrice_after_coupons();
            holder.mPriceTv.setText("￥" + price);

            String sales = goodsBean.getSales();
            holder.mPayNumberTv.setText(sales + "人付款");

            String price_coupons = goodsBean.getPrice_coupons();
            holder.mCouponTv.setText("优惠券" + price_coupons + "元");

            String rate = goodsBean.getRate();
            holder.mRateTv.setText("佣金：" + rate + "%");

            String quan_shengyu = goodsBean.getQuan_shengyu();
            holder.mRemainderTv.setText("剩余：" + quan_shengyu);

            holder.mRankingFl.setVisibility(View.GONE);

            int isFriendpop = goodsBean.getIs_friendpop();
            if (isFriendpop == 0) {
                holder.mBtnPerfectWXCircle.setBackgroundResource(R.drawable.selector_rect_whitebg_blackbor1_cor4);
                holder.mBtnPerfectWXCircle.setTextColor(getResources().getColor(R.color.colorBlack_333333));
                holder.mBtnPerfectWXCircle.setText("完善朋友圈");
            } else if (isFriendpop == 1) {
                holder.mBtnPerfectWXCircle.setBackgroundResource(R.drawable.selector_rect_whitebg_redbor1_cor4);
                holder.mBtnPerfectWXCircle.setTextColor(getResources().getColor(R.color.colorRed_FF2200));
                holder.mBtnPerfectWXCircle.setText("显示朋友圈");
            }

            holder.mBtnPerfectWXCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsListModel.DataBean.ListBean goodsBean = mGoodsList.get(position);
                    mGoodsBean = goodsBean;
                    String token = SPreferenceUtil.getString(CategoryActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                    if (TextUtils.isEmpty(token)) {
                        Toast.makeText(CategoryActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CategoryActivity.this, LoginActivity.class));
                    } else {
                        String[] permissons = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        boolean hasPermissions = EasyPermissions.hasPermissions(CategoryActivity.this, permissons);
                        if (hasPermissions) {
                            showDialog(goodsBean);
                        } else {
                            EasyPermissions.requestPermissions(CategoryActivity.this, "需要读取储存权限", 0, permissons);
                        }
                    }
                }
            });
            holder.mBtnCopyWords.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mShareType = "TKL";
                    GoodsListModel.DataBean.ListBean goodsBean = mGoodsList.get(position);
                    mGoodsBean = goodsBean;
                    String token = SPreferenceUtil.getString(CategoryActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                    if (TextUtils.isEmpty(token)) {
                        Toast.makeText(CategoryActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CategoryActivity.this, LoginActivity.class));
                    } else {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", goodsBean.getGoods_id());
                        params.put("token", token);
                        mPresenter.getTaobaoTbkTpwd(params);
                    }

                }
            });
            holder.mBtnShareWXTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mShareType = "WX";
                    GoodsListModel.DataBean.ListBean goodsBean = mGoodsList.get(position);
                    mGoodsBean = goodsBean;
                    String token = SPreferenceUtil.getString(CategoryActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                    String pid = SPreferenceUtil.getString(CategoryActivity.this, ZRDConstants.SPreferenceKey.SP_PID, "");
                    String tao_session = SPreferenceUtil.getString(CategoryActivity.this, ZRDConstants.SPreferenceKey.SP_TAO_SESSION, "");
                    if (TextUtils.isEmpty(pid) || TextUtils.isEmpty(tao_session) || TextUtils.isEmpty(token)) {
                        Intent intent = new Intent(CategoryActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", goodsBean.getGoods_id());
                        params.put("token", token);
                        mPresenter.getTaobaoTbkTpwd(params);
                    }
                }
            });
            holder.mBtnShareWXRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mShareType = "WX_CIRCLE";
                    GoodsListModel.DataBean.ListBean goodsBean = mGoodsList.get(position);
                    String token = SPreferenceUtil.getString(CategoryActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                    if (TextUtils.isEmpty(token)) {
                        Intent intent = new Intent(CategoryActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", goodsBean.getGoods_id());
                        params.put("token", token);
                        mPresenter.getTaobaoTbkTpwd(params);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mGoodsList.size();
        }
    }

    private void showDialog(GoodsListModel.DataBean.ListBean goodsBean) {
        PerfectWXCircleDialog perfectWXCircleDialog = new PerfectWXCircleDialog(CategoryActivity.this);
        perfectWXCircleDialog.show();
        perfectWXCircleDialog.setGoodsInfo(goodsBean);
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
