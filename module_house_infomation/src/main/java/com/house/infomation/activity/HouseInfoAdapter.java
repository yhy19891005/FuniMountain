package com.house.infomation.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.funi.utils.TextUtil;
import com.funi.view.MyTextView;
import com.funi.view.OnItemClickListener;
import com.house.infomation.R;
import com.house.infomation.model.HouseInfo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * HousInformationAdapter
 *
 * @Description: 房产资讯
 * @Author: pengqiang.zou
 * @CreateDate: 2019-04-04 10:37
 */
public class HouseInfoAdapter extends RecyclerView.Adapter<HouseInfoAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<HouseInfo> informations;
    private OnItemClickListener itemClickListener;

    public HouseInfoAdapter(Context context, ArrayList<HouseInfo> data) {
        this.context = context;
        this.informations = data;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void refreshData(ArrayList<HouseInfo> list) {
        this.informations = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(context, R.layout.hi_view_house_information_item, null);
        //返回MyViewHolder的对象
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        HouseInfo information = informations.get(position);
        if (null != information) {
            holder.tvTitle.setText(TextUtil.getText(information.getChannelName()));
            holder.tvDate.setText(TextUtil.getText(information.getReleaseTime()));
            holder.tvContent.setText(TextUtil.getText(information.getTitle()));

            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != itemClickListener) {
                        itemClickListener.onItemClick(v, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return informations.size();
    }

    //继承RecyclerView.ViewHolder抽象类的自定义ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutItem;
        TextView tvTitle;
        TextView tvDate;
        MyTextView tvContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_view_item);
            tvTitle = itemView.findViewById(R.id.hi_text_title);
            tvDate = itemView.findViewById(R.id.hi_text_date);
            tvContent = itemView.findViewById(R.id.hi_text_content);
        }
    }
}
