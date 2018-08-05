package com.zhaorou.zhuanquanapp.base;

public abstract class BasePresenter<T extends IBaseMvpView> {

    protected T mView;

    public void attachView(T view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }
}
