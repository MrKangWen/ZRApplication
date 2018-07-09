package com.zhaorou.zrapplication.home;

import com.zhaorou.zrapplication.base.IBaseMvpView;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.DtkGoodsListModel;

import java.util.List;

public interface IHomeFragmentView extends IBaseMvpView {

    void onFetchedClassList(List<ClassListModel.DataBean.ListBean> list);

    void onFetchDtkGoodsList(List<DtkGoodsListModel.DataBean.ListBean> list);
}
