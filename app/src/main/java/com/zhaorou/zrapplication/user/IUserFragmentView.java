package com.zhaorou.zrapplication.user;

import com.zhaorou.zrapplication.base.IBaseMvpView;
import com.zhaorou.zrapplication.user.model.UserInfoModel;

public interface IUserFragmentView extends IBaseMvpView {

    void onFetchedUserInfo(UserInfoModel.DataBean.UserBean userBean);
}
