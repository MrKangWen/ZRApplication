package com.zhaorou.zrapplication.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.GlideApp;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.login.LoginActivity;
import com.zhaorou.zrapplication.user.model.UserInfoModel;
import com.zhaorou.zrapplication.user.presenter.UserFragmentPresenter;
import com.zhaorou.zrapplication.utils.SharedPreferenceHelper;
import com.zhaorou.zrapplication.widget.SimpleEditTextDialog;

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
    @BindView(R.id.fragment_user_tip_text_tv)
    TextView mTipTv;
    @BindView(R.id.fragment_user_level_tv)
    TextView mLevelTv;
    @BindView(R.id.fragment_user_score_tv)
    TextView mScoreTv;

    private View mView;
    private Unbinder mUnbinder;
    private UserFragmentPresenter mPresenter = new UserFragmentPresenter();
    private SimpleEditTextDialog mBindPidDialog;

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

    @OnClick({R.id.fragment_user_user_info_ll, R.id.fragment_user_bind_pid_ll})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_user_user_info_ll:
                setUserInfoOrLogin();
                break;
            case R.id.fragment_user_bind_pid_ll:
                String token = SharedPreferenceHelper.getString(getContext(), ZRDConstants.SharedPreferenceKey.SP_LOGIN_TOKEN, "");
                if (TextUtils.isEmpty(token)) {
                    toLogin();
                } else {
                    if (mBindPidDialog == null) {
                        mBindPidDialog = new SimpleEditTextDialog(getContext(), mPresenter);
                    }
                    mBindPidDialog.show();
                }
                break;
            default:
                break;
        }
    }

    private void toLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    private void setUserInfoOrLogin() {
        String token = SharedPreferenceHelper.getString(getContext(), ZRDConstants.SharedPreferenceKey.SP_LOGIN_TOKEN, "");
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
    public void onUpdatedPid() {
        Toast.makeText(getContext(), "PID更新成功", Toast.LENGTH_SHORT).show();
        mBindPidDialog.dismiss();
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    private void getUserInfo() {
        String token = SharedPreferenceHelper.getString(getContext(), ZRDConstants.SharedPreferenceKey.SP_LOGIN_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            GlideApp.with(this).load(R.mipmap.ic_launcher).circleCrop().into(mAvatarIv);
            mNameTv.setText("点击登录/注册");
            mTipTv.setVisibility(View.GONE);
        } else {
            mPresenter.fetchUserInfo(token);
        }
    }

    private void setUserInfo(UserInfoModel.DataBean.UserBean userBean) {
        String nickname = userBean.getNickname();
        mNameTv.setText(nickname);
        mTipTv.setVisibility(View.VISIBLE);

        String headimgurl = userBean.getHeadimgurl();
        GlideApp.with(this).load(headimgurl).circleCrop().into(mAvatarIv);

        int score = userBean.getScore();
        mScoreTv.setText("积分：" + score);
    }
}
