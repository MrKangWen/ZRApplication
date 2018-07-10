package com.zhaorou.zrapplication.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    @BindView(R.id.fragment_user_avatar_iv)
    ImageView mAvatarIv;

    private View mView;
    private Unbinder mUnbinder;

    public UserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_user, container, false);
            mUnbinder = ButterKnife.bind(this, mView);
        }
        GlideApp.with(this).load(R.mipmap.ic_launcher).circleCrop().into(mAvatarIv);
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
