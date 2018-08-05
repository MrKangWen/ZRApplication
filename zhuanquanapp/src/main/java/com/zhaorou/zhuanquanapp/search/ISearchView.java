package com.zhaorou.zhuanquanapp.search;

import com.zhaorou.zhuanquanapp.base.IBaseMvpView;
import com.zhaorou.zhuanquanapp.home.model.FriendPopDetailModel;
import com.zhaorou.zhuanquanapp.home.model.GoodsListModel;

import java.util.List;

public interface ISearchView extends IBaseMvpView {

    void onSearchResult(List<GoodsListModel.DataBean.ListBean> list);

    void onLoadMore(boolean loadMore);

    void onGetTaowords(String tkl);

    void onGetFriendPopDetail(FriendPopDetailModel.DataBean.EntityBean entityBean);
}
