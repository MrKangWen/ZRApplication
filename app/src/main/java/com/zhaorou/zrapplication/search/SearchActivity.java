package com.zhaorou.zrapplication.search;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
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
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.home.dialog.LoadingDialog;
import com.zhaorou.zrapplication.home.dialog.PerfectWXCircleDialog;
import com.zhaorou.zrapplication.home.model.FriendPopDetailModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.login.LoginActivity;
import com.zhaorou.zrapplication.search.presenter.SearchPresenter;
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

    private GoodsListModel.DataBean.ListBean mGoodsBean;
    private String mShareType;
    private String mTaoword;
    private String mTkl;
    private LoadingDialog mLoadingDialog;
    private PerfectWXCircleDialog mPerfectWXCircleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        mLoadingDialog = new LoadingDialog(this);
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
    public void onLoadFail(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetTaowords(String tkl) {
        mTkl = tkl;
        String tklType = SPreferenceUtil.getString(this, ZRDConstants.SPreferenceKey.SP_LINK_TAO, "1");
        String goods_name = mGoodsBean.getGoods_name();
        String price = mGoodsBean.getPrice();
        String price_after_coupons = mGoodsBean.getPrice_after_coupons();

        if (TextUtils.equals(mShareType, "TKL")) {
            shareTKL(tkl, tklType, goods_name, price, price_after_coupons);
        }
        if (TextUtils.equals(mShareType, "WX")) {
            getFriendPop();
        }
        if (TextUtils.equals(mShareType, "WX_CIRCLE")) {
            getFriendPop();
        }
    }

    private void getFriendPop() {
        Map<String, String> params = new HashMap<>();
        params.put("goods_id", mGoodsBean.getGoods_id());
        mPresenter.getFriendPopDetail(params);
    }


    private void shareTKL(String tkl, String tklType, String goods_name, String price, String price_after_coupons) {

        String taoword = goods_name + "\n" + "原价 " + price + "\n" + "券后 " +
                price_after_coupons + "\n" +
                "--------抢购方式--------" + "\n";
        if (TextUtils.equals(tklType, "1")) {
            taoword = taoword + "复制本信息" + tkl + "打开淘宝即可获取";
        } else if (TextUtils.equals(tklType, "2")) {
            String pic = mGoodsBean.getPic();
            String str = "https://wenan001.kuaizhan.com/?taowords=";
            taoword = taoword + "打开链接\n" + str + tkl.substring(1, tkl.length() - 1) + "&pic=" + Base64.encodeToString(pic.getBytes(), Base64.DEFAULT);
        }
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("tkl", taoword);
        cm.setPrimaryClip(clipData);
        Toast.makeText(this, "淘口令已复制", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetFriendPopDetail(FriendPopDetailModel.DataBean.EntityBean entityBean) {
        shareFriendPopToWx(entityBean);
    }

    private void shareFriendPopToWx(FriendPopDetailModel.DataBean.EntityBean entityBean) {
        if (entityBean == null || TextUtils.isEmpty(entityBean.getImage())) {
            Toast.makeText(this, "请先完善朋友圈文案", Toast.LENGTH_SHORT).show();
            return;
        }
        String tklType = SPreferenceUtil.getString(this, ZRDConstants.SPreferenceKey.SP_LINK_TAO, "1");
        String goods_name = mGoodsBean.getGoods_name();
        String price = mGoodsBean.getPrice();
        String price_after_coupons = mGoodsBean.getPrice_after_coupons();
        String content = entityBean.getContent();

        if (TextUtils.equals(mShareType, "WX")) {
            mTaoword = goods_name + "\n" + content + "\n" + "原价 " + price + "\n" + "券后 " +
                    price_after_coupons + "\n" +
                    "--------抢购方式--------" + "\n";
            if (TextUtils.equals(tklType, "1")) {
                mTaoword = mTaoword + "复制本信息" + mTkl + "打开淘宝即可获取";
            } else if (TextUtils.equals(tklType, "2")) {
                String pic = mGoodsBean.getPic();
                String str = "https://wenan001.kuaizhan.com/?taowords=";
                mTaoword = mTaoword + "打开链接\n" + str + mTkl.substring(1, mTkl.length() - 1) + "&pic=" + Base64.encodeToString(pic.getBytes(), Base64.DEFAULT);
            }
        } else {
            mTaoword = content;
        }
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("taoword", mTaoword);
        cm.setPrimaryClip(clipData);
        Toast.makeText(this, "已复制文案，正在启动微信，请稍后...", Toast.LENGTH_SHORT).show();

        final List<String> list = new ArrayList<>();
        if (entityBean != null) {
            list.add(ZRDConstants.HttpUrls.BASE_URL + entityBean.getMarket_image());
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
            }
        } else {
            list.add(mGoodsBean.getPic());
        }

        final List<File> fileList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String imgUrl : list) {
                    File file = FileUtils.saveImageToSdCard(getExternalCacheDir(), imgUrl);
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
    public void onShowLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void onHideLoading() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
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


        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_goods_list_home_fragment, parent, false);
            SearchViewHolder goodsViewHolder = new SearchViewHolder(view);
            return goodsViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, final int position) {
            GoodsListModel.DataBean.ListBean goodsBean = mSearchList.get(position);
            mGoodsBean = goodsBean;
            String title = goodsBean.getGoods_name();
            holder.mTitleTv.setText(title);

            String pic = goodsBean.getPic();
            GlideApp.with(SearchActivity.this).asBitmap().load(pic).into(holder.mGoodsImageIv);

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
                    GoodsListModel.DataBean.ListBean goodsBean = mSearchList.get(position);
                    mGoodsBean = goodsBean;
                    String token = SPreferenceUtil.getString(SearchActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                    if (TextUtils.isEmpty(token)) {
                        Toast.makeText(SearchActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SearchActivity.this, LoginActivity.class));
                    } else {
                        String[] permissons = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        boolean hasPermissions = EasyPermissions.hasPermissions(SearchActivity.this, permissons);
                        if (hasPermissions) {
                            showDialog(goodsBean);
                        } else {
                            EasyPermissions.requestPermissions(SearchActivity.this, "需要读取储存权限", 0, permissons);
                        }
                    }
                }
            });
            holder.mBtnCopyWords.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mShareType = "TKL";
                    GoodsListModel.DataBean.ListBean goodsBean = mSearchList.get(position);
                    mGoodsBean = goodsBean;
                    String token = SPreferenceUtil.getString(SearchActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                    if (TextUtils.isEmpty(token)) {
                        Toast.makeText(SearchActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SearchActivity.this, LoginActivity.class));
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
                    GoodsListModel.DataBean.ListBean goodsBean = mSearchList.get(position);
                    mGoodsBean = goodsBean;
                    String token = SPreferenceUtil.getString(SearchActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                    String pid = SPreferenceUtil.getString(SearchActivity.this, ZRDConstants.SPreferenceKey.SP_PID, "");
                    String tao_session = SPreferenceUtil.getString(SearchActivity.this, ZRDConstants.SPreferenceKey.SP_TAO_SESSION, "");
                    if (TextUtils.isEmpty(pid) || TextUtils.isEmpty(tao_session) || TextUtils.isEmpty(token)) {
                        Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
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
                    GoodsListModel.DataBean.ListBean goodsBean = mSearchList.get(position);
                    String token = SPreferenceUtil.getString(SearchActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                    if (TextUtils.isEmpty(token)) {
                        Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
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
            return mSearchList.size();
        }
    }

    private void showDialog(GoodsListModel.DataBean.ListBean goodsBean) {
        mPerfectWXCircleDialog = new PerfectWXCircleDialog(this);
        mPerfectWXCircleDialog.show();
        mPerfectWXCircleDialog.setGoodsInfo(goodsBean);
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
