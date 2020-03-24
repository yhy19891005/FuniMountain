package com.funi.view.popwindow;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.funi.view.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 传递过来的
 *
 * @param <T>
 */
public class PopwindowAdapter<T> extends BaseAdapter {
    private Context context = null;
    private List<T> valueList = null;
    private String chooseText;//当前选择的
    private PopWindowI popWindowI;
    private boolean isDissmiss = false;

    public PopwindowAdapter(Context context, List<T> valueList, String chooseText, PopWindowI popWindowI) {
        this.context = context;
        this.valueList = valueList;
        this.chooseText = chooseText;
        this.popWindowI = popWindowI;
        this.isDissmiss = false;
    }

    public void refreshData(String chooseText) {
        this.chooseText = chooseText;
        this.isDissmiss = true;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return valueList == null ? 0 : valueList.size();
    }

    @Override
    public Object getItem(int position) {
        return valueList == null ? null : valueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.popwindow_item, null);
        TextView textView = view.findViewById(R.id.list_item_text);
        ImageView imageView = view.findViewById(R.id.list_item_image);

        T t = valueList.get(position);
        if (null != t) {
            Class clazz = t.getClass();// 获取集合中的对象类型
            Field[] fds = clazz.getDeclaredFields();// 获取他的字段数组
            for (Field field : fds) {// 遍历该数组
                String fdName = field.getName();// 得到字段名，
                Method metd = null;// 根据字段名找到对应的get方法，null表示无参数
                try {
                    metd = clazz.getMethod("get" + change(fdName), new Class[0]);

                    // 比较是否在字段数组中存在name字段，如果不存在短路，如果存在继续判断该字段的get方法是否存在，同时存在继续执行
                    if ("name".equals(fdName) && metd != null) {
                        Object name = metd.invoke(t, new Object[]{});// 调用该字段的get方法
                        textView.setText(name.toString());
                        if (chooseText.equals(name.toString())) {
                            imageView.setImageResource(R.drawable.choosed);
                            if (isDissmiss && valueList.size() > 10) {
                                handler.sendEmptyMessageDelayed(1, 150);
                            }
                        } else {
                            imageView.setImageResource(R.drawable.choosed_no);
                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (isDissmiss && (position + 1) == valueList.size()) {
            handler.sendEmptyMessage(1);
        }

        return view;
    }

    /**
     * @param src 源字符串
     * @return 字符串，将src的第一个字母转换为大写，src为空时返回null
     */
    public String change(String src) {
        if (src != null) {
            StringBuffer sb = new StringBuffer(src);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        } else {
            return null;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            popWindowI.dissmis();
        }
    };
}
