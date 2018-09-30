package com.zhaorou.zrapplication.home.rd;


import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.goods.GoodsDetailActivity;
import com.zhaorou.zrapplication.home.HomeJxFragment;
import com.zhaorou.zrapplication.home.IHomeFragmentView;
import com.zhaorou.zrapplication.home.api.HomeApi;
import com.zhaorou.zrapplication.home.dialog.PerfectWXCircleDialog;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.FriendPopDetailModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.home.model.JxListModel;
import com.zhaorou.zrapplication.home.presenter.HomeFragmentPresenter;
import com.zhaorou.zrapplication.login.LoginActivity;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.utils.AssistantService;
import com.zhaorou.zrapplication.utils.FileUtils;
import com.zhaorou.zrapplication.utils.SPreferenceUtil;
import com.zhaorou.zrapplication.widget.recyclerview.BaseListBindDataFragment;
import com.zhaorou.zrapplication.widget.recyclerview.CombinationViewHolder;
import com.zhaorou.zrapplication.widget.view.ScrollTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonFragment extends BaseListBindDataFragment<JxListModel, JxListModel.DataBean.ListBean> implements IHomeFragmentView, EasyPermissions.PermissionCallbacks {
    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter();
    private JxListModel.DataBean.ListBean mGoodsBean;
    private String mShareType;
    private String mTaoword;
    private String mTkl;

    private PerfectWXCircleDialog mPerfectWXCircleDialog;

    public CommonFragment() {
        // Required empty public constructor
    }


    @Override
    public int getAdapterLayoutId() {
        return R.layout.item_goods_list_common;
    }

    @Override
    public List<JxListModel.DataBean.ListBean> getAdapterList(JxListModel result) {
        return result.getData().getList();
    }

    @Override
    public void bindData(CombinationViewHolder holder, final JxListModel.DataBean.ListBean t, int position) {

        String pic = t.getPic();

        if (!pic.startsWith("http")) {
            pic = ZRDConstants.HttpUrls.BASE_URL + pic;
        }
        holder.setImageView(getActivity(), R.id.preview_img, pic);

        holder.setText(R.id.common_shengyu, "剩余：" + t.getQuan_shengyu());
        holder.setText(R.id.common_sales, "销量:" + t.getSales());
        holder.setText(R.id.common_pay_price, "劵后价:" + t.getPrice_after_coupons());
        holder.setText(R.id.common_coupon, "优惠券:" + t.getPrice_coupons());

        ScrollTextView common_title=   holder.getView(R.id.common_title);
        common_title.setText(t.getGoods_name());
        common_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("ID", t.getId());
                startActivity(intent);
            }
        });
        holder.getView(R.id.commonDetailLl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("ID", t.getId());
                startActivity(intent);
            }
        });

        TextView common_perfect_wx_circle = holder.getView(R.id.common_perfect_wx_circle);
        int isFriendpop = t.getIs_friendpop();
        if (isFriendpop == 0) {
            common_perfect_wx_circle.setBackgroundResource(R.drawable.selector_rect_whitebg_blackbor1_cor4);
            common_perfect_wx_circle.setTextColor(getResources().getColor(R.color.colorBlack_333333));
            common_perfect_wx_circle.setText("完善朋友圈");
        } else if (isFriendpop == 1) {
            common_perfect_wx_circle.setBackgroundResource(R.drawable.selector_rect_whitebg_redbor1_cor4);
            common_perfect_wx_circle.setTextColor(getResources().getColor(R.color.colorRed_FF2200));
            common_perfect_wx_circle.setText("显示朋友圈");
        }

        //完善朋友圈 显示朋友圈
        common_perfect_wx_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JxListModel.DataBean.ListBean goodsBean = t;
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
                        EasyPermissions.requestPermissions(getActivity(), "需要读取储存权限", 0, permissons);
                    }
                }
            }
        });


        holder.getView(R.id.common_copy_tkl).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    mShareType = "TKL";
                    JxListModel.DataBean.ListBean goodsBean = t;
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
                    e.printStackTrace();
                }

            }
        });


        holder.getView(R.id.common_share_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mShareType = "WX";
                    JxListModel.DataBean.ListBean goodsBean = t;
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
                    e.printStackTrace();
                }
            }
        });


        //share to moments
        holder.getView(R.id.common_share_wx_moments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mShareType = "WX_CIRCLE";
                    JxListModel.DataBean.ListBean goodsBean = t;
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
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Call<JxListModel> getCall(Map<String, Object> params) {

        // {cid: "0", type: 1, flag: 1, page: 1, keyword: "", pageSize: 20}


        // 1精选商品 2 预告 3 常规
        params.put("cid", "0");
        params.put("type", 3);
        params.put("flag", 1);
        if (isLogin()) {
            params.put("token", getToken());
        }

        return HttpRequestUtil.getRetrofitService(HomeApi.class).getJxList(params);
    }

    private void showDialog(JxListModel.DataBean.ListBean goodsBean) {
        mPerfectWXCircleDialog = new PerfectWXCircleDialog(getContext(), this);
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
            Toast.makeText(getContext(), "请先完善朋友圈文案", Toast.LENGTH_SHORT).show();
            return;
        }
        String tklType = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LINK_TAO, "1");
        String goods_name = mGoodsBean.getGoods_name();
        String price = mGoodsBean.getPrice();
        String price_after_coupons = mGoodsBean.getPrice_after_coupons();
        String content = entityBean.getContent();



        if("WX_CIRCLE".equals(mShareType)){
            mTaoword = "\n" + goods_name + "\n" + "原价 " + price + "\n" + "券后 " +
                    price_after_coupons + "\n" +
                    "--------抢购方式--------" + "\n";
        }else {
            mTaoword = "\n" + goods_name + "\n" + content + "\n" + "原价 " + price + "\n" + "券后 " +
                    price_after_coupons + "\n" +
                    "--------抢购方式--------" + "\n";
        }

        if (TextUtils.equals(tklType, "1")) {
            mTaoword = mTaoword + "复制本信息" + mTkl + "打开淘宝即可获取";
        } else if (TextUtils.equals(tklType, "2")) {
            String str = mTkl;
            mTaoword = mTaoword + "打开链接\n" + str;
        }

        if (!isOpenService() && "WX_CIRCLE".equals(mShareType)) {
            mTaoword = content;
        }



        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("taoword", mTaoword);
        cm.setPrimaryClip(clipData);
        Toast.makeText(getContext(), "已复制文案，正在启动微信，请稍后...", Toast.LENGTH_SHORT).show();

        AssistantService.mMoments = content;

        final List<String> list = new ArrayList<>();
        if (entityBean != null) {

            if (entityBean.getMarket_image() != null) {
                if (startsWithHttp(entityBean.getMarket_image())) {
                    list.add(entityBean.getMarket_image());
                } else {
                    list.add(ZRDConstants.HttpUrls.BASE_URL + entityBean.getMarket_image());
                }
            }


            String imageStr = entityBean.getImage();
            if (!TextUtils.isEmpty(imageStr)) {
                if (imageStr.contains("#")) {
                    String[] imageArray = imageStr.split("#");
                    for (String img : imageArray) {

                        if (startsWithHttp(img)) {
                            list.add(img);
                        } else {
                            list.add(ZRDConstants.HttpUrls.BASE_URL + img);
                        }

                    }
                } else {
                    if (startsWithHttp(imageStr)) {
                        list.add(imageStr);
                    } else {
                        list.add(ZRDConstants.HttpUrls.BASE_URL + imageStr);
                    }

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
            return;
        }

        if (mGoodsBean.getIs_friendpop() == 0) {

            FriendPopDetailModel.DataBean.EntityBean bean = new FriendPopDetailModel.DataBean.EntityBean();
            bean.setContent(mGoodsBean.getQuan_guid_content());
            bean.setImage(mGoodsBean.getPic());
            shareFriendPopToWx(bean);
            return;
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
            String str = tkl;
            taoword = taoword + "打开链接\n" + str;
        }
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("tkl", taoword);
        cm.setPrimaryClip(clipData);
        Toast.makeText(getContext(), "淘口令已复制", Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter.attachView(this);
        return super.onCreateView(inflater, container, savedInstanceState);


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
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();

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
