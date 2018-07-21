package com.zhaorou.zrapplication.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseActivity;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.login.LoginActivity;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.utils.SPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.activity_settings_btn_link_taoword)
    ImageView mBtnLinkTao;

    private String mLinkTao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        mLinkTao = SPreferenceUtil.getString(SettingsActivity.this, ZRDConstants.SPreferenceKey.SP_LINK_TAO, "1");
        if (TextUtils.equals(mLinkTao, "2")) {
            mBtnLinkTao.setImageResource(R.mipmap.icon_toggle_on);
        } else if (TextUtils.equals(mLinkTao, "1")) {
            mBtnLinkTao.setImageResource(R.mipmap.icon_toggle_off);
        }
    }

    @OnClick({R.id.activity_settings_layout_title_left_btn_rl, R.id.activity_settings_btn_link_taoword,
            R.id.activity_settings_version_info_ll, R.id.activity_settings_btn_logout})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_settings_layout_title_left_btn_rl:
                finish();
                break;
            case R.id.activity_settings_btn_link_taoword:
                String token = SPreferenceUtil.getString(SettingsActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                if (TextUtils.isEmpty(token)) {
                    Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    mLinkTao = SPreferenceUtil.getString(SettingsActivity.this, ZRDConstants.SPreferenceKey.SP_LINK_TAO, "1");
                    Map<String, String> params = new HashMap<>();
                    if (TextUtils.equals(mLinkTao, "1")) {
                        SPreferenceUtil.put(SettingsActivity.this, ZRDConstants.SPreferenceKey.SP_LINK_TAO, "2");
                        mBtnLinkTao.setImageResource(R.mipmap.icon_toggle_on);
                        params.put("tkl_type", "2");
                    } else if (TextUtils.equals(mLinkTao, "2")) {
                        SPreferenceUtil.put(SettingsActivity.this, ZRDConstants.SPreferenceKey.SP_LINK_TAO, "1");
                        mBtnLinkTao.setImageResource(R.mipmap.icon_toggle_off);
                        params.put("tkl_type", "1");
                    }
                    params.put("token", token);
                    Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executePost(ZRDConstants.HttpUrls.UPDATE_TKL_TYPE, params);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response != null && response.body() != null) {
                                try {
                                    String str = response.body().string();
                                    JSONObject jsonObj = new JSONObject(str);
                                    if (jsonObj != null && jsonObj.optInt("code") == 200) {
                                        String data = jsonObj.optString("data");
                                        Toast.makeText(SettingsActivity.this, data, Toast.LENGTH_SHORT).show();
                                    }
                                    if (jsonObj != null && jsonObj.optInt("code") == 401) {
                                        Toast.makeText(SettingsActivity.this, "登录已过期，请重新登录", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
                break;
            case R.id.activity_settings_version_info_ll:
                break;
            case R.id.activity_settings_btn_logout:
                AlertDialog alertDialog = new AlertDialog.Builder(SettingsActivity.this)
                        .setMessage("是否退出当前账号？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SPreferenceUtil.put(SettingsActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                                Toast.makeText(SettingsActivity.this, "已退出登录", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).create();
                alertDialog.show();

                break;
            default:
                break;
        }

    }

}
