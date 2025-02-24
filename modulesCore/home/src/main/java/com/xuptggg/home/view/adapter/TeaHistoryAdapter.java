package com.xuptggg.home.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuptggg.home.R;
import com.xuptggg.home.model.TeaHistoryInfo;

import java.util.List;

public class TeaHistoryAdapter extends RecyclerView.Adapter<TeaHistoryAdapter.TeaHistoryViewHolder> {

    private List<TeaHistoryInfo> teaHistoryInfoList;

    public TeaHistoryAdapter(List<TeaHistoryInfo> teaHistoryInfoList) {
        this.teaHistoryInfoList = teaHistoryInfoList;
    }

    @NonNull
    @Override
    public TeaHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tea_history, parent, false);
        // 请将 your_layout_file_name 替换为实际的布局文件名
        return new TeaHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeaHistoryViewHolder holder, int position) {
        TeaHistoryInfo teaHistoryInfo = teaHistoryInfoList.get(position);
        holder.title.setText(teaHistoryInfo.getTitle());
        holder.detail.setText(teaHistoryInfo.getDetail());
        holder.image.setImageResource(teaHistoryInfo.getImageResId());
    }

    @Override
    public int getItemCount() {
        return teaHistoryInfoList.size();
    }

    public static class TeaHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView detail;
        ImageView image;

        public TeaHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_tea_history_title);
            detail = itemView.findViewById(R.id.tv_tea_history_detail);
            image = itemView.findViewById(R.id.iv_message_icon);
        }
    }
}