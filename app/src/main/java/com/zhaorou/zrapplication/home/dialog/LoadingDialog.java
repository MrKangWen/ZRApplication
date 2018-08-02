package com.zhaorou.zrapplication.home.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseDialog;

public class LoadingDialog extends BaseDialog {


    public LoadingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.layout_loading_dialog);
    }
}
