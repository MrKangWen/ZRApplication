package com.zhaorou.zrapplication.home.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseDialog;

public class PerfectWXCircleDialog extends BaseDialog implements View.OnClickListener {

    private static final String TAG = "PerfectWXCircleDialog";

    private TextView mUrlTv;
    private TextView mTitleTv;
    private EditText mContentEt;
    private ImageView mBtnAddMainImage;
    private ImageView mBtnAddImages;
    private TextView mBtnCancel;
    private TextView mBtnSubmit;



    public PerfectWXCircleDialog(@NonNull Context context) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_perfect_wx_circle_dialog);

        mUrlTv = findViewById(R.id.perfect_wx_circle_dialog_url_tv);
        mTitleTv = findViewById(R.id.perfect_wx_circle_dialog_title_tv);
        mContentEt = findViewById(R.id.perfect_wx_circle_dialog_content_et);
        mBtnAddMainImage = findViewById(R.id.perfect_wx_circle_dialog_btn_add_main_image);
        mBtnAddImages = findViewById(R.id.perfect_wx_circle_dialog_btn_add_images);
        mBtnCancel = findViewById(R.id.perfect_wx_circle_dialog_btn_cancel);
        mBtnSubmit = findViewById(R.id.perfect_wx_circle_dialog_btn_submit);

        mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.perfect_wx_circle_dialog_btn_add_main_image:
                break;
            case R.id.perfect_wx_circle_dialog_btn_add_images:
                break;
            case R.id.perfect_wx_circle_dialog_btn_cancel:
                dismiss();
                break;
            case R.id.perfect_wx_circle_dialog_btn_submit:
                break;
            default:
                break;
        }
    }
}
