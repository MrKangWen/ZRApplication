package com.zhaorou.zrapplication.base;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.network.imp.HttpDialogLoading;
import com.zhaorou.zrapplication.utils.SPreferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements HttpDialogLoading {


    public BaseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    protected Map<String, Object> getTokenMap() {

        String token = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
        Map<String, Object> map = new HashMap<>(2);
        map.put("token", token);
        return map;
    }

    protected String getToken() {

        String token = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");

        return token;
    }

    protected boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }

    abstract public void initData();

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void goToLogin() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTipsDialog(String msg) {

    }

    public boolean startsWithHttp(String url) {
        return url.startsWith("http");
    }

/*    private void shareTKL(String tkl, String tklType, String goods_name, String price, String price_after_coupons) {

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
    }*/

}
