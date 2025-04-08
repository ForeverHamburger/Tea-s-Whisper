package com.xuptggg.search.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuptggg.search.R;
import com.xuptggg.search.model.TeaShowInfo;

import java.util.List;

public class TeaShowAdapter extends RecyclerView.Adapter<TeaShowAdapter.TeaMakeViewHolder> {

    private List<TeaShowInfo> teaShowInfos;

    public TeaShowAdapter(List<TeaShowInfo> teaShowInfoList) {
        this.teaShowInfos = teaShowInfoList;
    }

    @NonNull
    @Override
    public TeaMakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tea, parent, false);
        return new TeaMakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeaMakeViewHolder holder, int position) {
        TeaShowInfo teaShowInfo = teaShowInfos.get(position);
        holder.teaShowName.setText(teaShowInfo.getTeaName());
        holder.teaShowImage.setImageResource(teaShowInfo.getImageResId());
    }

    @Override
    public int getItemCount() {
        return teaShowInfos.size();
    }

    public static class TeaMakeViewHolder extends RecyclerView.ViewHolder {
        TextView teaShowName;
        ImageView teaShowImage;

        public TeaMakeViewHolder(@NonNull View itemView) {
            super(itemView);
            teaShowName = itemView.findViewById(R.id.tv_tea_show_name);
            teaShowImage = itemView.findViewById(R.id.iv_tea_show_image);
        }
    }
}