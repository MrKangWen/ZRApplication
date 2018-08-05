package com.zhaorou.zhuanquanapp.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zhaorou.zhuanquanapp.R;
import com.zhaorou.zhuanquanapp.base.BaseApplication;
import com.zhaorou.zhuanquanapp.constants.ZRDConstants;
import com.zhaorou.zhuanquanapp.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        EventBus.getDefault().register(this);
        BaseApplication.getWXAPI().handleIntent(getIntent(), this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseApplication.getWXAPI().handleIntent(data, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        BaseApplication.getWXAPI().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                String code = resp.code;
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setCommand(ZRDConstants.EventCommand.COMMAND_LOGIN);
                messageEvent.setData(code);
                EventBus.getDefault().post(messageEvent);
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this, "用户取消授权", Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(this, "用户拒绝授权", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

    }
}
