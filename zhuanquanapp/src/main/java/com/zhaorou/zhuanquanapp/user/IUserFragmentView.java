package com.zhaorou.zhuanquanapp.user;


import com.zhaorou.zhuanquanapp.base.IBaseMvpView;
import com.zhaorou.zhuanquanapp.user.model.UserInfoModel;

public interface IUserFragmentView extends IBaseMvpView {

    void onFetchedUserInfo(UserInfoModel.DataBean.UserBean userBean);

    void onUpdatedPid(String pid);

    void onUpdatedTaoSession(String taoSession);
}
