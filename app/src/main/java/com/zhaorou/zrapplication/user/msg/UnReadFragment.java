package com.zhaorou.zrapplication.user.msg;


import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.home.api.HomeApi;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.user.model.UnReadMsgModel;
import com.zhaorou.zrapplication.widget.recyclerview.BaseListBindDataFragment;
import com.zhaorou.zrapplication.widget.recyclerview.CombinationViewHolder;

import java.util.List;
import java.util.Map;

import retrofit2.Call;


/**
 * A simple {@link Fragment} subclass.
 */
public class UnReadFragment extends BaseListBindDataFragment<UnReadMsgModel, UnReadMsgModel.DataBean.ListBean> {


    public UnReadFragment() {
        // Required empty public constructor
    }

/*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_un_read, container, false);
    }
*/


    @Override
    public int getAdapterLayoutId() {
        return R.layout.item_msg_un_read;
    }

    @Override
    public List<UnReadMsgModel.DataBean.ListBean> getAdapterList(UnReadMsgModel result) {
        return result.getData().getList();
    }

    @Override
    public void bindData(CombinationViewHolder holder, final UnReadMsgModel.DataBean.ListBean t, int position) {
        holder.setText(R.id.title, t.getContent());
        holder.setText(R.id.date, t.getCreate_time());

        holder.getView(R.id.msgUnReadRl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(t.getId());
            }
        });
    }


    private void showDialog(int msgId) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("是否标记为已读");
        builder.setPositiveButton("标记为已读", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public Call<UnReadMsgModel> getCall(Map<String, Object> map) {
        return HttpRequestUtil.getRetrofitService(HomeApi.class).getUnReadList(getToken());
    }
}
