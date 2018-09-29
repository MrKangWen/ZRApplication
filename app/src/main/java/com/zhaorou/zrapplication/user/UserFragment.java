package com.zhaorou.zrapplication.user;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.GlideApp;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.home.dialog.LoadingDialog;
import com.zhaorou.zrapplication.login.LoginActivity;
import com.zhaorou.zrapplication.settings.SettingsActivity;
import com.zhaorou.zrapplication.user.model.UserInfoModel;
import com.zhaorou.zrapplication.user.model.UserMessageEvent;
import com.zhaorou.zrapplication.user.msg.MsgActivity;
import com.zhaorou.zrapplication.user.presenter.UserFragmentPresenter;
import com.zhaorou.zrapplication.utils.SPreferenceUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements IUserFragmentView {

    @BindView(R.id.fragment_user_avatar_iv)
    ImageView mAvatarIv;
    @BindView(R.id.fragment_user_user_info_ll)
    LinearLayout mUserInfoLl;
    @BindView(R.id.fragment_user_user_info_name)
    TextView mNameTv;

    @BindView(R.id.fragment_user_level_tv)
    TextView mLevelTv;
    @BindView(R.id.fragment_user_score_tv)
    TextView mScoreTv;
    @BindView(R.id.userTValidDate)
    TextView mUserTValidDate;
    @BindView(R.id.userMsgCountTv)
    TextView mUserMsgCountTv;

    private View mView;
    private Unbinder mUnbinder;
    private UserFragmentPresenter mPresenter = new UserFragmentPresenter();
    private BindPidDialog mBindPidDialog;
    private LoadingDialog mLoadingDialog;

    public UserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_user, container, false);
            mUnbinder = ButterKnife.bind(this, mView);
        }
        mPresenter.attachView(this);
        mLoadingDialog = new LoadingDialog(getContext());

        mView.findViewById(R.id.fragment_use_help_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webViewIntent = new Intent(getActivity(), WebViewActivity.class);
                webViewIntent.putExtra("URL", "https://www.kancloud.cn/zhaoroudanapp/help/722292");
                startActivity(webViewIntent);
            }
        });


        mView.findViewById(R.id.userIvMsg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                if (TextUtils.isEmpty(token)) {

                    toLogin();

                } else {
                    Intent intent = new Intent(getActivity(), MsgActivity.class);
                    startActivity(intent);
                }

            }
        });


        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mPresenter.detachView();

    }

    @OnClick({R.id.fragment_user_user_info_ll, R.id.fragment_user_bind_pid_ll, R.id.fragment_use_setting_ll,
            R.id.fragment_user_get_tao_session, R.id.fragment_user_bind_tao_session})
    protected void onClick(View v) {
        String token = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
        switch (v.getId()) {
            case R.id.fragment_user_user_info_ll:
                setUserInfoOrLogin();
                break;
            case R.id.fragment_user_bind_pid_ll:
                if (TextUtils.isEmpty(token)) {
                    toLogin();
                } else {
                    if (mBindPidDialog == null) {
                        mBindPidDialog = new BindPidDialog(getContext(), mPresenter);
                    }
                    mBindPidDialog.show();
                }
                break;
            case R.id.fragment_user_get_tao_session:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    toLogin();
                } else {
                    Intent webViewIntent = new Intent(getActivity(), WebViewActivity.class);
                    startActivityForResult(webViewIntent, 0);
                }
                break;
            case R.id.fragment_use_setting_ll:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void toLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    private void setUserInfoOrLogin() {
        String token = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            toLogin();
        } else {

        }
    }

    @Override
    public void onFetchedUserInfo(UserInfoModel.DataBean.UserBean userBean) {
        setUserInfo(userBean);
    }

    @Override
    public void onUpdatedPid(String pid) {
        SPreferenceUtil.put(getContext(), ZRDConstants.SPreferenceKey.SP_PID, pid);
        Toast.makeText(getContext(), "PID更新成功", Toast.LENGTH_SHORT).show();
        mBindPidDialog.dismiss();
    }

    @Override
    public void onUpdatedTaoSession(String taoSession) {
    }

    @Override
    public void onShowLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void onHideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }


    @Override
    public void onLoadFail(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    private void getUserInfo() {
        String token = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            mUserMsgCountTv.setVisibility(View.GONE);
            mScoreTv.setText("积分：0");
            GlideApp.with(this).load(R.mipmap.ic_launcher).circleCrop().into(mAvatarIv);
            mNameTv.setText("点击登录/注册");
            mUserTValidDate.setVisibility(View.GONE);

        } else {
            mPresenter.fetchUserInfo(token);
        }
    }

    private void setUserInfo(UserInfoModel.DataBean.UserBean userBean) {
        String nickname = userBean.getNickname();
        mNameTv.setText(nickname);

        mUserTValidDate.setVisibility(View.VISIBLE);
        mUserTValidDate.setText("过期时间：" + userBean.getTao_session_valid_time());
        String headimgurl = userBean.getHeadimgurl();
        GlideApp.with(this).load(headimgurl).circleCrop().into(mAvatarIv);

        int score = userBean.getScore();
        mScoreTv.setText("积分：" + score);

        mUserMsgCountTv.setText(userBean.getUnread_msg_count());
        EventBus.getDefault().post(new UserMessageEvent(userBean.getUnread_msg_count()));

    }
}
