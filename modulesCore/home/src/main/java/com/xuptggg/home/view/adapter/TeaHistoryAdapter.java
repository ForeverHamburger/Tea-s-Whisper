package com.xuptggg.home.view.adapter;

import android.animation.ObjectAnimator;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjectAnimator scaleXDown = ObjectAnimator.ofFloat(itemView, "scaleX", 1f, 0.9f);
                    ObjectAnimator scaleYDown = ObjectAnimator.ofFloat(itemView, "scaleY", 1f, 0.9f);
                    scaleXDown.setDuration(100);
                    scaleYDown.setDuration(100);
                    scaleXDown.start();
                    scaleYDown.start();
                    scaleXDown.addListener(new android.animation.Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(android.animation.Animator animation) {}

                        @Override
                        public void onAnimationEnd(android.animation.Animator animation) {
                            ObjectAnimator scaleXUp = ObjectAnimator.ofFloat(itemView, "scaleX", 0.9f, 1f);
                            ObjectAnimator scaleYUp = ObjectAnimator.ofFloat(itemView, "scaleY", 0.9f, 1f);
                            scaleXUp.setDuration(200);
                            scaleYUp.setDuration(200);
                            scaleXUp.setInterpolator(new android.view.animation.BounceInterpolator());
                            scaleYUp.setInterpolator(new android.view.animation.BounceInterpolator());
                            scaleXUp.start();
                            scaleYUp.start();
                        }

                        @Override
                        public void onAnimationCancel(android.animation.Animator animation) {}

                        @Override
                        public void onAnimationRepeat(android.animation.Animator animation) {}
                    });
                }
            });
        }

    }
}