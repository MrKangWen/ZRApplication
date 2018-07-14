package com.zhaorou.zrapplication.search;

import com.zhaorou.zrapplication.base.IBaseMvpView;
import com.zhaorou.zrapplication.home.model.GoodsListModel;

import java.util.List;

public interface ISearchView extends IBaseMvpView {

    void onSearchResult(List<GoodsListModel.DataBean.ListBean> list);

    void onLoadMore(boolean loadMore);
}
