package com.house.mine.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.house.mine.R;
import com.house.mine.util.DensityUtil;

public class ChoosePicPopuwindow extends PopupWindow implements View.OnClickListener {

    private Activity mActivity;

    public ChoosePicPopuwindow(Activity activity) {
        super(activity);
        mActivity = activity;
        View layout = View.inflate(activity, R.layout.public_popupwindow_choose_pic, null);
        layout.findViewById(R.id.media_type).setOnClickListener(this);
        layout.findViewById(R.id.photo_type).setOnClickListener(this);
        layout.findViewById(R.id.cancel).setOnClickListener(this);

        super.setContentView(layout);
        super.setWidth(LayoutParams.MATCH_PARENT);
        super.setHeight(LayoutParams.WRAP_CONTENT);
        super.setOutsideTouchable(true);
        super.setBackgroundDrawable(null);
        super.update();
    }

    public void show(View view) {
        super.setWidth(DensityUtil.getWindowWidth(mActivity));
        super.update();
        super.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        super.setFocusable(true);
        super.update();

        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.7f;
        mActivity.getWindow().setAttributes(lp);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.alpha = 1f;
        mActivity.getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.media_type){
            if(mListener != null) mListener.mediaType();
        }else if(v.getId() == R.id.photo_type){
            if(mListener != null) mListener.photoType();
        }else if(v.getId() == R.id.cancel){
            dismiss();
        }
    }

    private OnPicSelectListener mListener;

    public void setOnPicSelectListener(OnPicSelectListener listener){
        mListener = listener;
    }

    public interface OnPicSelectListener{

        void mediaType(); //从相册中选取

        void photoType(); //拍摄
    }
}
