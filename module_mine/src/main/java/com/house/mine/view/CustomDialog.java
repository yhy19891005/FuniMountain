package com.house.mine.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.house.mine.R;
import com.house.mine.util.DensityUtil;

public class CustomDialog {

    private Activity mActivity;
    private Handler handler;
    private Runnable runnable;
    private Dialog dlg;
    private View layout;

    private TextView dialogMessage_tv;
    private TextView dialog_negative_btn, dialog_positive_btn, dialog_ok_btn;
    private TextView titleView, skip_tv;

    private String message, negativeBtn, positiveBtn, okBtn, title;
    private LinearLayout mTwoBtn;
    private RelativeLayout title_layout;
    private boolean canceledOnTouchOutside = false;

    public CustomDialog(Activity mActivity) {
        this.mActivity = mActivity;
        initView();
    }

    public void show() {
        show(0);
    }

    public void show(final long showTime) {
        if (handler != null) handler.removeCallbacks(runnable);
        if (mActivity.isFinishing()) return;

        dlg.setContentView(layout);
        dlg.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dlg.show();

        Window window = dlg.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.width = (int) (DensityUtil.getWindowWidth(mActivity) * 0.8);
        window.setAttributes(params);
        initData();

        if (showTime > 0) {
            if (handler == null) handler = new Handler();
            if (runnable == null) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                };
            }
            handler.postDelayed(runnable, showTime);
        }

    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        dlg.setOnDismissListener(listener);
    }

    public void dismiss() {
        dlg.dismiss();
    }

    public void setCancelAble(boolean cancelAble) {
        dlg.setCancelable(cancelAble);
    }

    public void setCanceledOnTouchOutside(boolean cancelAble) {
        canceledOnTouchOutside = cancelAble;
    }

    public boolean isShowing() {
        return dlg.isShowing();
    }

    public void setTitle(String title) {
        this.title = title;
        titleView.setVisibility(View.VISIBLE);
        titleView.setText(title);
    }

    public void setMessage(String s) {
        setMessage(s, 0, false, 0);
    }

    public void setMessage(String s, int maxHeight, int paddingTop, int paddingBottom) {
        dialogMessage_tv.setPadding(dialogMessage_tv.getPaddingLeft(), paddingTop, dialogMessage_tv.getPaddingRight(), paddingBottom);
        dialogMessage_tv.setMaxHeight(maxHeight);
        setMessage(s, 0, false, 0);
    }

    public void setMessage(String s, int maxHeight, int paddingTop, int paddingBottom ,boolean isHtml) {
        dialogMessage_tv.setPadding(dialogMessage_tv.getPaddingLeft(), paddingTop, dialogMessage_tv.getPaddingRight(), paddingBottom);
        dialogMessage_tv.setMaxHeight(maxHeight);
        setMessage(s, 0, isHtml, 0);
    }

    public void setMessage(String s, boolean isHtml) {
        setMessage(s, 0, isHtml, 0);
    }

    public void setMessage(String s, int gravity) {
        setMessage(s, 0, false, gravity);
    }

    public void setMessage(String s, float textSize) {
        setMessage(s, textSize, false, 0);
    }

    public void setMessage(String s, float textSize, boolean isHtml, int gravity) {
        this.message = s;
        if (textSize != 0) dialogMessage_tv.setTextSize(textSize);
        if (isHtml) dialogMessage_tv.setText(Html.fromHtml(message));
        else dialogMessage_tv.setText(message);
        if (gravity != 0) dialogMessage_tv.setGravity(gravity);
    }

    public void setPositiveBtn(String s, View.OnClickListener click) {
        setPositiveBtn(s, 0, 0, click);
    }

    public void setPositiveBtn(String s, int textColorId, int backgroundColorId, View.OnClickListener click) {
        positiveBtn = s;
        dialog_positive_btn.setText(positiveBtn);
        dialog_positive_btn.setOnClickListener(click);

        if (textColorId != 0)
            dialog_positive_btn.setTextColor(textColorId);
        if (backgroundColorId != 0)
            dialog_positive_btn.setBackgroundColor(backgroundColorId);
    }

    public void setNegativeBtn(String s, View.OnClickListener click) {
        setNegativeBtn(s, 0, 0, click);
    }

    public void setNegativeBtn(String s, int textColorId, int backgroundColorId, View.OnClickListener click) {
        negativeBtn = s;
        dialog_negative_btn.setText(negativeBtn);
        dialog_negative_btn.setOnClickListener(click);

        if (textColorId != 0)
            dialog_negative_btn.setTextColor(textColorId);
        if (backgroundColorId != 0)
            dialog_negative_btn.setBackgroundColor(backgroundColorId);
    }

    public void setOkBtn(String s, View.OnClickListener click) {
        this.okBtn = s;
        dialog_ok_btn.setText(okBtn);
        dialog_ok_btn.setOnClickListener(click);
    }

    private void initView() {
        layout = mActivity.getLayoutInflater().inflate(R.layout.view_dialog, null);
        mTwoBtn = (LinearLayout) layout.findViewById(R.id.twoBtn);
        title_layout = (RelativeLayout) layout.findViewById(R.id.title_layout);
        titleView = (TextView) layout.findViewById(R.id.tv_title);
        skip_tv = (TextView) layout.findViewById(R.id.skip_tv);
        dialogMessage_tv = (TextView) layout.findViewById(R.id.dialogMessage_tv);
        dialogMessage_tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        dialog_negative_btn = (TextView) layout.findViewById(R.id.dialog_negative_btn);
        dialog_positive_btn = (TextView) layout.findViewById(R.id.dialog_positive_btn);
        dialog_ok_btn = (TextView) layout.findViewById(R.id.dialog_ok_btn);
        skip_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        dlg = new Dialog(mActivity, R.style.CustomDialog);
    }


    private void initData() {
        if (TextUtils.isEmpty(message))
            dialogMessage_tv.setVisibility(View.GONE);
        if (TextUtils.isEmpty(negativeBtn) && TextUtils.isEmpty(positiveBtn))
            mTwoBtn.setVisibility(View.GONE);
        if (TextUtils.isEmpty(okBtn))
            dialog_ok_btn.setVisibility(View.GONE);
        //跳过按钮和标题都没有的情况头部布局隐藏
        if (skip_tv.getVisibility() == View.GONE && titleView.getVisibility() == View.GONE) {
            title_layout.setVisibility(View.GONE);
        }
    }

    //跳过按钮显示
    public void showCancelBtn(boolean isShow) {
        skip_tv.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

}
