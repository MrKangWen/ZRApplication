package com.zhaorou.zrapplication.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseActivity;
import com.zhaorou.zrapplication.base.BaseApplication;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.eventbus.MessageEvent;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.user.model.WXUserInfoModel;
import com.zhaorou.zrapplication.utils.GsonHelper;
import com.zhaorou.zrapplication.utils.SPreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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

public class LoginActivity extends BaseActivity {

    @BindView(R.id.icon_left_layout_title_iv)
    ImageView mTitleLeftIconIv;
    @BindView(R.id.text_right_layout_title_tv)
    TextView mTitleRightTextTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initTitleBar();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @OnClick({R.id.btn_left_layout_title_rl, R.id.activity_login_btn_wx})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left_layout_title_rl:
                finish();
                break;
            case R.id.activity_login_btn_wx:
                requestWechatAuth();
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        String action = event.getCommand();
        if (TextUtils.equals(action, ZRDConstants.EventCommand.COMMAND_LOGIN)) {
            String code = (String) event.getData();
            wxLogin(code);
        }
    }

    private void initTitleBar() {
        mTitleLeftIconIv.setImageResource(R.drawable.icon_x);
        mTitleLeftIconIv.setVisibility(View.VISIBLE);
        mTitleRightTextTv.setText("注册");
        mTitleRightTextTv.setVisibility(View.VISIBLE);
    }

    private void requestWechatAuth() {
        if (BaseApplication.getWXAPI().isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_zrd_login";
            BaseApplication.getWXAPI().sendReq(req);
        } else {
            Toast.makeText(this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
        }
    }

    private void wxLogin(String code) {

        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("state", "STATE");
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().executeGet(ZRDConstants.HttpUrls.WX_LOGIN, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.body() != null) {
                    try {
                        String responseStr = response.body().string();
                        JSONObject jsonObj = new JSONObject(responseStr);
                        int code = jsonObj.optInt("code");
                        if (code == 200) {
                            WXUserInfoModel wxUserInfoModel = GsonHelper.fromJson(responseStr, WXUserInfoModel.class);
                            if (wxUserInfoModel != null && wxUserInfoModel.getCode() == 200) {
                                String token = wxUserInfoModel.getData().getToken();
                                SPreferenceUtil.put(LoginActivity.this, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, token);
                                String pid = wxUserInfoModel.getData().getUser().getPid();
                                SPreferenceUtil.put(LoginActivity.this, ZRDConstants.SPreferenceKey.SP_PID, pid);
                                String tao_session = wxUserInfoModel.getData().getUser().getTao_session();
                                SPreferenceUtil.put(LoginActivity.this, ZRDConstants.SPreferenceKey.SP_TAO_SESSION, tao_session);
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else if (!jsonObj.isNull("message")) {
                            String message = jsonObj.optString("message");
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else if (!jsonObj.isNull("msg")) {
                            String message = jsonObj.optString("msg");
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "请求错误", Toast.LENGTH_SHORT).show();
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
}
