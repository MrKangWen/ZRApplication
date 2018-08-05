package com.zhaorou.zhuanquanapp.home;


import com.zhaorou.zhuanquanapp.base.IBaseMvpView;
import com.zhaorou.zhuanquanapp.home.model.ClassListModel;
import com.zhaorou.zhuanquanapp.home.model.FriendPopDetailModel;
import com.zhaorou.zhuanquanapp.home.model.GoodsListModel;

import java.util.List;

public interface IHomeFragmentView extends IBaseMvpView {

    void onFetchedClassList(List<ClassListModel.DataBean.ListBean> list);

    void onFetchDtkGoodsList(List<GoodsListModel.DataBean.ListBean> list);

    void onLoadMore(boolean loadMore);

    void onGetFriendPopDetail(FriendPopDetailModel.DataBean.EntityBean entityBean);

    void onGetTaowords(String tkl);
}
