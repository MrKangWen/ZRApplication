package com.zhaorou.zrapplication.home;

import com.zhaorou.zrapplication.base.IBaseMvpView;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;

import java.util.List;

public interface IHomeFragmentView extends IBaseMvpView {

    void onFetchedClassList(List<ClassListModel.DataBean.ListBean> list);

    void onFetchDtkGoodsList(List<GoodsListModel.DataBean.ListBean> list);

    void onLoadMore(boolean hasMore);

}
