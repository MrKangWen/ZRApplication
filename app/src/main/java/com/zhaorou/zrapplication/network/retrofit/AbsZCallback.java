package com.zhaorou.zrapplication.network.retrofit;

import com.zhaorou.zrapplication.base.BaseModel;
import com.zhaorou.zrapplication.network.imp.HttpDialogLoading;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author kang
 */
public abstract class AbsZCallback<T extends BaseModel> implements Callback<T> {

    public final static int HTTP_STATUS_SUCCESS = 200;
    public AbsZCallback() {

    }

    public AbsZCallback(HttpDialogLoading httpDialogLoading) {

    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response.body() == null) {
            Throwable throwable = new Throwable("发生异常:");
            onFail(call, throwable);
            return;
        }


      //  onSuccess(call, response);

       if (response.body().getCode() == HTTP_STATUS_SUCCESS) {
            onSuccess(call, response);
        } else {
            Throwable throwable = new Throwable("发生异常:" + response.body().getCode());
            onFail(call, throwable);
        }

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFail(call, t);
    }


    abstract public void onSuccess(Call<T> call, Response<T> response);

    abstract public void onFail(Call<T> call, Throwable t);

}
