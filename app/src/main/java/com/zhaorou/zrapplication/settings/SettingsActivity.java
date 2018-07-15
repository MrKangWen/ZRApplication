package com.zhaorou.zrapplication.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseActivity;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.utils.SPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.activity_settings_btn_link_taoword)
    ImageView mBtnLinkTao;
    private boolean mLinkTao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        mLinkTao = SPreferenceUtil.getBoolean(SettingsActivity.this, ZRDConstants.SPreferenceKey.SP_LINK_TAO, false);
        if (mLinkTao) {
            mBtnLinkTao.setImageResource(R.mipmap.icon_toggle_on);
        } else {
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
                mLinkTao = !mLinkTao;
                SPreferenceUtil.put(SettingsActivity.this, ZRDConstants.SPreferenceKey.SP_LINK_TAO, mLinkTao);
                if (mLinkTao) {
                    mBtnLinkTao.setImageResource(R.mipmap.icon_toggle_on);
                } else {
                    mBtnLinkTao.setImageResource(R.mipmap.icon_toggle_off);
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
                            }
                        }).create();
                alertDialog.show();

                break;
            default:
                break;
        }

    }

}
