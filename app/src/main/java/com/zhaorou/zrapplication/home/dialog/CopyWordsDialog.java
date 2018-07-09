package com.zhaorou.zrapplication.home.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseDialog;

public class CopyWordsDialog extends BaseDialog {

    public CopyWordsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_copy_words_dialog);
    }
}
