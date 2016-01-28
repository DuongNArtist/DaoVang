package com.dsa.dsadaovang.dialogs;

import com.dsa.dsadaovang.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

public abstract class BaseDialog extends Dialog {

    protected Context mContext;

    public BaseDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().getAttributes().windowAnimations = R.style.dialog;
        mContext = context;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(getViewId());
        bindView();
    }

    public abstract int getViewId();

    public abstract void bindView();
}
