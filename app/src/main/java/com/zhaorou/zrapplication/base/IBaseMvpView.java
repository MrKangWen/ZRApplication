package com.zhaorou.zrapplication.base;

public interface IBaseMvpView {

    void onShowLoading();

    void onHideLoading();

    void onLoginTimeout();

    void onLoadFail(String str);
}
