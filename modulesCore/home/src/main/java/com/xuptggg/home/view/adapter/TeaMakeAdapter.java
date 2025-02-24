package com.xuptggg.home.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuptggg.home.R;
import com.xuptggg.home.model.TeaMakeInfo;

import java.util.List;

public class TeaMakeAdapter extends RecyclerView.Adapter<TeaMakeAdapter.TeaMakeViewHolder> {

    private List<TeaMakeInfo> teaMakeInfoList;

    public TeaMakeAdapter(List<TeaMakeInfo> teaMakeInfoList) {
        this.teaMakeInfoList = teaMakeInfoList;
    }

    @NonNull
    @Override
    public TeaMakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tea_make, parent, false);
        return new TeaMakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeaMakeViewHolder holder, int position) {
        TeaMakeInfo teaMakeInfo = teaMakeInfoList.get(position);
        holder.teaMakeName.setText(teaMakeInfo.getMakeName());
        holder.teaMakeDetail.setText(teaMakeInfo.getMakeDetail());
        holder.teaMakeImage.setImageResource(teaMakeInfo.getImageResId());
    }

    @Override
    public int getItemCount() {
        return teaMakeInfoList.size();
    }

    public static class TeaMakeViewHolder extends RecyclerView.ViewHolder {
        TextView teaMakeName;
        TextView teaMakeDetail;
        ImageView teaMakeImage;

        public TeaMakeViewHolder(@NonNull View itemView) {
            super(itemView);
            teaMakeName = itemView.findViewById(R.id.tv_tea_make_name);
            teaMakeDetail = itemView.findViewById(R.id.tv_tea_make_detail);
            teaMakeImage = itemView.findViewById(R.id.iv_tea_image);
        }
    }
}