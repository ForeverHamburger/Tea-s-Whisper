package com.xuptggg.detection.history.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xuptggg.detection.R;
import com.xuptggg.detection.history.model.DetectionHistoryInfo;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private final List<DetectionHistoryInfo> historyInfos;
    private OnItemClickListener listener;

    // 点击事件接口
    public interface OnItemClickListener {
        void onItemClick(DetectionHistoryInfo item);
        void onMoreButtonClick(DetectionHistoryInfo item);
    }

    public HistoryAdapter(List<DetectionHistoryInfo> historyList, OnItemClickListener listener) {
        this.historyInfos = historyList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detection_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetectionHistoryInfo item = historyInfos.get(position);

        // 绑定茶叶检测数据
        holder.tvTeaName.setText(item.getTeaName());      // 茶叶名称
        holder.tvTeaTip.setText(item.getDetectionTip());  // 检测提示信息
        holder.tvTime.setText(item.getDetectionTime());   // 检测时间

        // 点击事件
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });

        holder.ivMore.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMoreButtonClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyInfos.size();
    }

    public void updateData(List<DetectionHistoryInfo> newList) {
        historyInfos.clear();
        historyInfos.addAll(newList);
        notifyDataSetChanged();
    }
    // ViewHolder 类
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // After
        ImageView ivLeaves;   // 茶叶图片
        TextView tvTeaName;   // 茶叶名称
        TextView tvTeaTip;    // 检测提示
        TextView tvTime;      // 检测时间
        ImageView ivMore;      // 更多操作按钮

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLeaves = itemView.findViewById(R.id.iv_leaves);
            tvTeaName = itemView.findViewById(R.id.tv_tea_name);
            tvTeaTip = itemView.findViewById(R.id.tv_tea_tip);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivMore = itemView.findViewById(R.id.iv_more);
        }
    }
}
