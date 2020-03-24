package com.house.mine.util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.house.mine.adapter.SingleSelectAdapter;
import com.house.mine.bean.DataInfo;
import com.house.mine.view.SingleSelectPopupwindw;

import java.util.List;

public class SingleSelectHelper {

    public static void singleSelect(Context context, List<DataInfo> list, View parent, SingleSelectPopupwindw.ChooseItemListener<DataInfo> listener){
        SingleSelectAdapter<DataInfo> adapter = new SingleSelectAdapter<DataInfo>(context) {
            @Override
            public void bindData(DataInfo certificateInfo, TextView tv) {
                tv.setText(certificateInfo.getName());
            }
        };
        SingleSelectPopupwindw<DataInfo> popupwindw = new SingleSelectPopupwindw<>(context, adapter);
        popupwindw.setData(list);
        popupwindw.show(parent);
        popupwindw.setChooseItemListener(listener);
    }
}
