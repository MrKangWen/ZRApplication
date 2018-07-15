package com.zhaorou.zrapplication.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseDialog;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.user.presenter.UserFragmentPresenter;
import com.zhaorou.zrapplication.utils.SPreferenceUtil;

public class SimpleEditTextDialog extends BaseDialog implements View.OnClickListener {

    private Context mContext;
    private EditText mEditText;
    private TextView mBtnOk;
    private UserFragmentPresenter mPresenter;

    public SimpleEditTextDialog(@NonNull Context context, UserFragmentPresenter presenter) {
        super(context);
        mContext = context;
        mPresenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_simple_edittext_dialog);

        mBtnOk = findViewById(R.id.bind_pid_dialog_btn_ok);
        mBtnOk.setOnClickListener(this);
        mBtnOk.setEnabled(false);

        mEditText = findViewById(R.id.bind_pid_dialog_content_et);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mBtnOk.setEnabled(false);
                } else {
                    mBtnOk.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        updatePid();
    }

    private void updatePid() {
        String pid = mEditText.getText().toString();
        String token = SPreferenceUtil.getString(mContext, ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
        mPresenter.bindPid(pid, token);
    }
}
