package com.funi.view.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.funi.view.R;


/**
 * 弹框
 */
public class PopWindowUtil {

    public static PopupWindow showPopWindow(boolean clickOutside, final Context context, final View parentView, final PopWindowInterface popWindowInterface) {
        return showPopWindow(clickOutside, context, parentView, 0, 0, popWindowInterface);
    }

    public static PopupWindow showPopWindow(boolean clickOutside, final Context context, final View parentView, final int layoutId, int topMargin, final PopWindowInterface popWindowInterface) {
        LinearLayout popupView = null;
        popupView = (LinearLayout) View.inflate(context, R.layout.popwindow_base_layout, null);
        LinearLayout layout = (LinearLayout) popupView.findViewById(R.id.view_pop);
        final PopupWindow mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        View popupContentV = null;
        if (layoutId == 0) {
            popupContentV = View.inflate(context, R.layout.popwindow_item_view, null);
        } else {
            popupContentV = View.inflate(context, layoutId, null);
        }
        LinearLayout.LayoutParams layoutParams = null;
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int marign = (int) context.getResources().getDimension(R.dimen.margin_25);
        layoutParams.leftMargin = marign;
        layoutParams.rightMargin = marign;
        if (topMargin > 0) {
            layoutParams.topMargin = layoutParams.bottomMargin = topMargin;
        } else {
            layoutParams.topMargin = layoutParams.bottomMargin = (int) context.getResources().getDimension(R.dimen.layout_height_48);
        }

        layoutParams.gravity = Gravity.CENTER;
        layout.addView(popupContentV, layoutParams);

        popupContentV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        popWindowInterface.setViewActions(mPopupWindow, popupView);
        mPopupWindow.setAnimationStyle(R.style.PopWindowAnim);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);//outside hide
        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (clickOutside) {
            mPopupWindow.getContentView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow.dismiss();
                }
            });
        }
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.update();
        mPopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        return mPopupWindow;
    }
}
