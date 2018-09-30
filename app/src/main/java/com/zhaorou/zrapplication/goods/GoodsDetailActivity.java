package com.zhaorou.zrapplication.goods;


import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseActivity;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.home.IHomeFragmentView;
import com.zhaorou.zrapplication.home.api.HomeApi;
import com.zhaorou.zrapplication.home.dialog.PerfectWXCircleDialog;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.FriendPopDetailModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.home.presenter.HomeFragmentPresenter;
import com.zhaorou.zrapplication.login.LoginActivity;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.network.retrofit.AbsZCallback;
import com.zhaorou.zrapplication.utils.AssistantService;
import com.zhaorou.zrapplication.utils.FileUtils;
import com.zhaorou.zrapplication.utils.SPreferenceUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Response;

public class GoodsDetailActivity extends BaseActivity implements IHomeFragmentView, EasyPermissions.PermissionCallbacks {

    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter();
    private GoodsDetailModel.DataBean.EntityBean mGoodsBean;
    private String mShareType;
    private String mTaoword;
    private String mTkl;
    private PerfectWXCircleDialog mPerfectWXCircleDialog;

    private int mZhiBoIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        mPresenter.attachView(this);
        setTitle("肉单详情");
        getData();
    }


    private void initData(final GoodsDetailModel.DataBean.EntityBean detailModel) {

        String baseUrl = ZRDConstants.HttpUrls.BASE_URL;

        if (!TextUtils.isEmpty(detailModel.getZhibo_introd1())) {
            ImageView imageView1 = findViewById(R.id.detailIv1);
            Glide.with(getApplicationContext()).
                    load(baseUrl + detailModel.getZhibo_pic1()).into(imageView1);
            TextView textView1 = findViewById(R.id.detailTv1);
            textView1.setMovementMethod(ScrollingMovementMethod.getInstance());
            textView1.setText(detailModel.getZhibo_introd1());

            findViewById(R.id.detailShareTv1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isLogin()) {
                        Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GoodsDetailActivity.this, LoginActivity.class));
                    } else {
                        mZhiBoIndex = 1;
                        Map<String, String> params = new HashMap<>();
                        params.put("id", detailModel.getGoods_id());
                        params.put("token", getToken());
                        mPresenter.getTaobaoTbkTpwd(params);
                    }
                }
            });

        } else {
            findViewById(R.id.detailLiveLl1).setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(detailModel.getZhibo_introd2())) {
            ImageView imageView2 = findViewById(R.id.detailIv2);
            Glide.with(getApplicationContext()).
                    load(baseUrl + detailModel.getZhibo_pic2()).into(imageView2);
            TextView textView2 = findViewById(R.id.detailTv2);
            textView2.setText(detailModel.getZhibo_introd2());
            textView2.setMovementMethod(ScrollingMovementMethod.getInstance());

            findViewById(R.id.detailShareTv2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isLogin()) {
                        Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GoodsDetailActivity.this, LoginActivity.class));
                    } else {
                        mZhiBoIndex = 2;
                        Map<String, String> params = new HashMap<>();
                        params.put("id", detailModel.getGoods_id());
                        params.put("token", getToken());
                        mPresenter.getTaobaoTbkTpwd(params);
                    }
                }
            });

        } else {
            findViewById(R.id.detailLiveLl2).setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(detailModel.getZhibo_introd3())) {
            ImageView imageView3 = findViewById(R.id.detailIv3);
            Glide.with(getApplicationContext()).
                    load(baseUrl + detailModel.getZhibo_pic3()).into(imageView3);
            TextView textView3 = findViewById(R.id.detailTv3);
            textView3.setText(detailModel.getZhibo_introd3());
            textView3.setMovementMethod(ScrollingMovementMethod.getInstance());
            findViewById(R.id.detailShareTv3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isLogin()) {
                        Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GoodsDetailActivity.this, LoginActivity.class));
                    } else {
                        mZhiBoIndex = 3;
                        Map<String, String> params = new HashMap<>();
                        params.put("id", detailModel.getGoods_id());
                        params.put("token", getToken());
                        mPresenter.getTaobaoTbkTpwd(params);
                    }
                }
            });
        } else {
            findViewById(R.id.detailLiveLl3).setVisibility(View.GONE);
        }


        ImageView detailIv4 = findViewById(R.id.detailIv4);

        if (detailModel.getPic().startsWith("http")) {
            Glide.with(getApplicationContext()).
                    load(detailModel.getPic()).into(detailIv4);
        } else {
            Glide.with(getApplicationContext()).
                    load(baseUrl + detailModel.getPic()).into(detailIv4);
        }


        TextView title = findViewById(R.id.detailGoodsTitleTv);
        title.setText(detailModel.getGoods_name());


        TextView detailCouponTv = findViewById(R.id.detailCouponTv);
        detailCouponTv.setText("优惠券" + detailModel.getPrice_coupons() + "元");

        TextView detailSurplusTv = findViewById(R.id.detailSurplusTv);
        detailSurplusTv.setText("剩余：" + detailModel.getQuan_shengyu());


        TextView detailCouponAfterPriceTv = findViewById(R.id.detailCouponAfterPriceTv);
        detailCouponAfterPriceTv.setText("劵后价：" + detailModel.getPrice_after_coupons());

        TextView detailSalesTv = findViewById(R.id.detailSalesTv);
        detailSalesTv.setText("销量：" + detailModel.getSales());

        TextView detailExtractTv = findViewById(R.id.detailExtractTv);
        detailExtractTv.setText("佣金：" + detailModel.getRate() + "%");


        TextView detail_perfect_wx_circle = findViewById(R.id.detail_perfect_wx_circle);
        int isFriendpop = detailModel.getIs_friendpop();
        if (isFriendpop == 0) {
            detail_perfect_wx_circle.setBackgroundResource(R.drawable.selector_rect_whitebg_blackbor1_cor4);
            detail_perfect_wx_circle.setTextColor(getResources().getColor(R.color.colorBlack_333333));
            detail_perfect_wx_circle.setText("完善朋友圈");
        } else if (isFriendpop == 1) {
            detail_perfect_wx_circle.setBackgroundResource(R.drawable.selector_rect_whitebg_redbor1_cor4);
            detail_perfect_wx_circle.setTextColor(getResources().getColor(R.color.colorRed_FF2200));
            detail_perfect_wx_circle.setText("显示朋友圈");
        }
        mGoodsBean = detailModel;
        //完善朋友圈 显示朋友圈
        detail_perfect_wx_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String token = SPreferenceUtil.getString(getApplicationContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                String pid = SPreferenceUtil.getString(getApplicationContext(), ZRDConstants.SPreferenceKey.SP_PID, "");
                String tao_session = SPreferenceUtil.getString(getApplicationContext(), ZRDConstants.SPreferenceKey.SP_TAO_SESSION, "");
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(GoodsDetailActivity.this, LoginActivity.class));
                } else {
                    String[] permissons = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    boolean hasPermissions = EasyPermissions.hasPermissions(GoodsDetailActivity.this, permissons);
                    if (hasPermissions) {
                        showDialog(mGoodsBean);
                    } else {
                        EasyPermissions.requestPermissions(GoodsDetailActivity.this, "需要读取储存权限", 0, permissons);
                    }
                }
            }
        });


        findViewById(R.id.detail_copy_tkl).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    mShareType = "TKL";


                    String token = SPreferenceUtil.getString(GoodsDetailActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                    //  String pid = SPreferenceUtil.getString(this, ZRDConstants.SPreferenceKey.SP_PID, "");
                    //   String tao_session = SPreferenceUtil.getString(this, ZRDConstants.SPreferenceKey.SP_TAO_SESSION, "");

                    if (TextUtils.isEmpty(token)) {
                        Toast.makeText(GoodsDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GoodsDetailActivity.this, LoginActivity.class));
                    } else {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", detailModel.getGoods_id());
                        params.put("token", token);
                        mPresenter.getTaobaoTbkTpwd(params);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        findViewById(R.id.detail_share_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mShareType = "WX";


                    String token = SPreferenceUtil.getString(GoodsDetailActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                    String pid = SPreferenceUtil.getString(GoodsDetailActivity.this, ZRDConstants.SPreferenceKey.SP_PID, "");
                    String tao_session = SPreferenceUtil.getString(GoodsDetailActivity.this, ZRDConstants.SPreferenceKey.SP_TAO_SESSION, "");

                    if (TextUtils.isEmpty(pid) || TextUtils.isEmpty(tao_session) || TextUtils.isEmpty(token)) {
                        Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", detailModel.getGoods_id());
                        params.put("token", token);
                        mPresenter.getTaobaoTbkTpwd(params);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //share to moments
        findViewById(R.id.detail_share_wx_moments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mShareType = "WX_CIRCLE";

                    mGoodsBean = detailModel;

                    String token = SPreferenceUtil.getString(getApplicationContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                    String pid = SPreferenceUtil.getString(getApplicationContext(), ZRDConstants.SPreferenceKey.SP_PID, "");
                    String tao_session = SPreferenceUtil.getString(getApplicationContext(), ZRDConstants.SPreferenceKey.SP_TAO_SESSION, "");

                    if (TextUtils.isEmpty(token)) {
                        Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", detailModel.getGoods_id());
                        params.put("token", token);
                        mPresenter.getTaobaoTbkTpwd(params);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void getData() {


        int id = getIntent().getIntExtra("ID", 0);

        HttpRequestUtil.getRetrofitService(HomeApi.class).getGoodsDetail(id).enqueue(new AbsZCallback<GoodsDetailModel>() {
            @Override
            public void onSuccess(Call<GoodsDetailModel> call, Response<GoodsDetailModel> response) {

                if (response.body().getCode() == AbsZCallback.HTTP_STATUS_SUCCESS) {
                    initData(response.body().getData().getEntity());

                    findViewById(R.id.detailFl).setVisibility(View.GONE);

                }

            }

            @Override
            public void onFail(Call<GoodsDetailModel> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "获取数据异常", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void showDialog(GoodsDetailModel.DataBean.EntityBean goodsBean) {
        mPerfectWXCircleDialog = new PerfectWXCircleDialog(this);
        mPerfectWXCircleDialog.show();
        mPerfectWXCircleDialog.setGoodsInfo(goodsBean.getGoods_id(), goodsBean.getQuan_guid_content(),
                goodsBean.getIs_friendpop(), goodsBean.getGoods_name(), goodsBean.getPic());
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


        //直播分享 和 分享都微信群都不需要文案
        if (mZhiBoIndex != 0) {
            mTaoword = content;
        } else {


            if ("WX".equals(mShareType)) {
                mTaoword = goods_name + "\n" + "原价 " + price + "\n" + "券后 " +
                        price_after_coupons + "\n" +
                        "--------抢购方式--------" + "\n";
            } else {
                //微信朋友圈
                mTaoword = "\n" + goods_name + "\n" + "原价 " + price + "\n" + "券后 " +
                        price_after_coupons + "\n" +
                        "--------抢购方式--------" + "\n";
            }




            if (TextUtils.equals(tklType, "1")) {
                mTaoword = mTaoword + "复制本信息" + mTkl + "打开淘宝即可获取";
            } else if (TextUtils.equals(tklType, "2"))

            {
                String str = mTkl;
                mTaoword = mTaoword + "打开链接\n" + str;
            }

        }


        ClipboardManager cm = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("taoword", mTaoword);
        cm.setPrimaryClip(clipData);
        Toast.makeText(this, "已复制文案，正在启动微信，请稍后...", Toast.LENGTH_SHORT).show();

        mZhiBoIndex = 0;

        startShareWx(entityBean, content);

    }

    private void startShareWx(FriendPopDetailModel.DataBean.EntityBean entityBean, String content) {


        AssistantService.mMoments = content;

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
                    File file = FileUtils.saveImageToSdCard(getApplicationContext().getExternalCacheDir(), imgUrl);
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
        String tklType = SPreferenceUtil.getString(getApplicationContext(), ZRDConstants.SPreferenceKey.SP_LINK_TAO, "1");
        String goods_name = mGoodsBean.getGoods_name();
        String price = mGoodsBean.getPrice();
        String price_after_coupons = mGoodsBean.getPrice_after_coupons();

        if (TextUtils.equals(mShareType, "TKL")) {
            shareTKL(tkl, tklType, goods_name, price, price_after_coupons);
            return;
        }


        FriendPopDetailModel.DataBean.EntityBean bean = new FriendPopDetailModel.DataBean.EntityBean();

        if (mZhiBoIndex != 0) {
            //分享直播内容
            switch (mZhiBoIndex) {
                case 1:
                    bean.setContent(mGoodsBean.getZhibo_introd1());
                    bean.setImage(mGoodsBean.getZhibo_pic1());
                    break;
                case 2:
                    bean.setContent(mGoodsBean.getZhibo_introd2());
                    bean.setImage(mGoodsBean.getZhibo_pic2());
                    break;
                case 3:
                    bean.setContent(mGoodsBean.getZhibo_introd3());
                    bean.setImage(mGoodsBean.getZhibo_pic3());
                    break;
                default:

                    break;
            }

            shareFriendPopToWx(bean);
            return;
        }

        if (mGoodsBean.getIs_friendpop() == 0) {
            bean.setContent(mGoodsBean.getQuan_guid_content());
            bean.setImage(mGoodsBean.getMarket_pic());
            shareFriendPopToWx(bean);
            return;
        }


        if (TextUtils.equals(mShareType, "WX")) {

            bean.setContent(mGoodsBean.getQuan_guid_content());
            bean.setImage(mGoodsBean.getMarket_pic());
            shareFriendPopToWx(bean);
            return;
            //  getFriendPop();
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
            String str = tkl;
            taoword = taoword + "打开链接\n" + str;
        }
        ClipboardManager cm = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("tkl", taoword);
        cm.setPrimaryClip(clipData);
        Toast.makeText(this, "淘口令已复制", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onLoadMore(boolean loadMore) {

    }


    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void onLoadFail(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFetchedClassList(List<ClassListModel.DataBean.ListBean> list) {

    }

    @Override
    public void onFetchDtkGoodsList(List<GoodsListModel.DataBean.ListBean> list) {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        showDialog(mGoodsBean);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
