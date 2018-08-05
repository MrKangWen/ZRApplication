package com.zhaorou.zhuanquanapp.home.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zhaorou.zhuanquanapp.R;
import com.zhaorou.zhuanquanapp.base.BaseDialog;

public class LoadingDialog extends BaseDialog {


    public LoadingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.layout_loading_dialog);
    }
}
