package com.zhaorou.zrapplication.base;

public interface IBaseMvpView {

    void onShowLoading();

    void onHideLoading();

    void onLoadFail(String str);
}
