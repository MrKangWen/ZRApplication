package com.zhaorou.zrapplication.widget.recyclerview;

import java.util.List;
import java.util.Map;
import retrofit2.Call;

public interface ListBindDataInterface<T,D> {

    int getAdapterLayoutId();//获取布局ID
    List<D> getAdapterList(T result);//获取数据
    void bindData(CombinationViewHolder holder, D t, int position);//绑定数据
    Call<T> getCall(Map<String, Object> map);//获取请求的call
}
