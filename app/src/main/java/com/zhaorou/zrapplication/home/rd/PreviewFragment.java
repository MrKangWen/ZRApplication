package com.zhaorou.zrapplication.home.rd;



import android.support.v4.app.Fragment;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.home.api.HomeApi;
import com.zhaorou.zrapplication.home.model.JxListModel;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.widget.recyclerview.BaseListBindDataFragment;
import com.zhaorou.zrapplication.widget.recyclerview.CombinationViewHolder;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewFragment extends BaseListBindDataFragment<JxListModel, JxListModel.DataBean.ListBean> {

    public PreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public int getAdapterLayoutId() {
        return R.layout.item_goods_list_preview;
    }

    @Override
    public List<JxListModel.DataBean.ListBean> getAdapterList(JxListModel result) {
        return result.getData().getList();
    }

    @Override
    public void bindData(CombinationViewHolder holder, JxListModel.DataBean.ListBean t, int position) {

        String pic = t.getPic();

        if (!pic.startsWith("http")) {
            pic = ZRDConstants.HttpUrls.BASE_URL + pic;
        }
        holder.setImageView(getActivity(), R.id.preview_img, pic);
        holder.setText(R.id.preview_title, t.getGoods_name());
        holder.setText(R.id.preview_commission, "佣金：" + t.getRate() + "%");
        holder.setText(R.id.preview_live_time, "直播时间:" + t.getZhibo_time());
        holder.setText(R.id.preview_pay_price, "卷后价:"+t.getPrice_after_coupons() );

    }

    @Override
    public Call<JxListModel> getCall(Map<String, Object> params) {

        // {cid: "0", type: 1, flag: 1, page: 1, keyword: "", pageSize: 20}

        // 1精选商品 2 预告 3 常规
        params.put("cid", "0");
        params.put("type", 2);
        params.put("flag", 1);
        params.put("page", 1);
        params.put("pagesize", 15);
        return HttpRequestUtil.getRetrofitService(HomeApi.class).getJxList(params);
    }
}
