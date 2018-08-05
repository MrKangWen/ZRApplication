package com.zhaorou.zhuanquanapp.base;

public interface IBaseMvpView {

    void onShowLoading();

    void onHideLoading();

    void onLoadFail(String str);
}
