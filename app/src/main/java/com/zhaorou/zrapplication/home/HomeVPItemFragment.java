package com.zhaorou.zrapplication.home;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
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
import com.zhaorou.zrapplication.home.dialog.LoadingDialog;
import com.zhaorou.zrapplication.home.dialog.PerfectWXCircleDialog;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.FriendPopDetailModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.home.presenter.HomeFragmentPresenter;
import com.zhaorou.zrapplication.login.LoginActivity;
import com.zhaorou.zrapplication.utils.AccessibilityUtils;
import com.zhaorou.zrapplication.utils.AssistantService;
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
    private String mShareType;
    private String mTaoword;
    private String mTkl;
    private LoadingDialog mLoadingDialog;
    private PerfectWXCircleDialog mPerfectWXCircleDialog;

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
        mLoadingDialog = new LoadingDialog(getContext());
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
        shareFriendPopToWx(entityBean);
    }

    @Override
    public void onLoadFail(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    private void shareFriendPopToWx(FriendPopDetailModel.DataBean.EntityBean entityBean) {
        if (entityBean == null || TextUtils.isEmpty(entityBean.getImage())) {
            Toast.makeText(getContext(), "请先完善朋友圈文案", Toast.LENGTH_SHORT).show();
            return;
        }
        String tklType = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LINK_TAO, "1");
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
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("taoword", mTaoword);
        cm.setPrimaryClip(clipData);
        Toast.makeText(getContext(), "已复制文案，正在启动微信，请稍后...", Toast.LENGTH_SHORT).show();

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
                    File file = FileUtils.saveImageToSdCard(getContext().getExternalCacheDir(), imgUrl);
                    if (file != null) {
                        fileList.add(file);
                    }
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
        mTkl = tkl;
        String tklType = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LINK_TAO, "1");
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
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("tkl", taoword);
        cm.setPrimaryClip(clipData);
        Toast.makeText(getContext(), "淘口令已复制", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowLoading() {
        HomeFragment.startRefresh();
        mLoadingDialog.show();
    }

    @Override
    public void onHideLoading() {
        HomeFragment.finishRefresh();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
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
            GoodsListModel.DataBean.ListBean goodsBean = mGoodsList.get(position);
            mGoodsBean = goodsBean;
            String title = goodsBean.getGoods_name();
            holder.mTitleTv.setText(title);

            String pic = goodsBean.getPic();
            GlideApp.with(HomeVPItemFragment.this).asBitmap().load(pic).into(holder.mGoodsImageIv);

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

            if (TextUtils.equals(mGoodsType, mGoodsTypeKeys[0])) {
                holder.mRankingFl.setVisibility(View.VISIBLE);
                String sort = goodsBean.getSort();
                if (!TextUtils.isEmpty(sort)) {
                    holder.mRankingTv.setText(position + 1 + "");
                }
            } else {
                holder.mRankingFl.setVisibility(View.GONE);
            }

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
                    String token = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                    String pid = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_PID, "");
                    String tao_session = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_TAO_SESSION, "");
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
                    try {
                        mShareType = "TKL";
                        GoodsListModel.DataBean.ListBean goodsBean = mGoodsList.get(position);
                        mGoodsBean = goodsBean;
                        String token = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                        String pid = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_PID, "");
                        String tao_session = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_TAO_SESSION, "");

                        if (TextUtils.isEmpty(token)) {
                            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        } else {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", goodsBean.getGoods_id());
                            params.put("token", token);
                            mPresenter.getTaobaoTbkTpwd(params);
                        }
                    } catch (Exception e) {
                    }

                }
            });
            holder.mBtnShareWXTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mShareType = "WX";
                        GoodsListModel.DataBean.ListBean goodsBean = mGoodsList.get(position);
                        mGoodsBean = goodsBean;
                        String token = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                        String pid = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_PID, "");
                        String tao_session = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_TAO_SESSION, "");

                        if (TextUtils.isEmpty(pid) || TextUtils.isEmpty(tao_session) || TextUtils.isEmpty(token)) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", goodsBean.getGoods_id());
                            params.put("token", token);
                            mPresenter.getTaobaoTbkTpwd(params);
                        }
                    } catch (Exception e) {
                    }
                }
            });
            holder.mBtnShareWXRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mShareType = "WX_CIRCLE";
                        GoodsListModel.DataBean.ListBean goodsBean = mGoodsList.get(position);
                        mGoodsBean = goodsBean;

                        String token = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                        String pid = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_PID, "");
                        String tao_session = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_TAO_SESSION, "");

                        if (TextUtils.isEmpty(token)) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", goodsBean.getGoods_id());
                            params.put("token", token);
                            mPresenter.getTaobaoTbkTpwd(params);
                        }
                    } catch (Exception e) {
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
        mPerfectWXCircleDialog = new PerfectWXCircleDialog(getContext(), this);
        mPerfectWXCircleDialog.show();
        mPerfectWXCircleDialog.setGoodsInfo(goodsBean);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
        private TextView mBtnPerfectWXCircle;
        private TextView mBtnCopyWords;
        private RelativeLayout mBtnShareWXRl;
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
