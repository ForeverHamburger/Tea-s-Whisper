package com.xuptggg.home.view.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuptggg.home.R;
import com.xuptggg.home.model.infos.TeaInfo;

import java.util.List;

public class TeaCardAdapter extends RecyclerView.Adapter<TeaCardAdapter.TeaCardViewHolder> {

    private List<TeaInfo> teaInfoList;

    public TeaCardAdapter(List<TeaInfo> teaInfoList) {
        this.teaInfoList = teaInfoList;
    }

    @NonNull
    @Override
    public TeaCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tea_card, parent, false);
        return new TeaCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeaCardViewHolder holder, int position) {
        TeaInfo teaInfo = teaInfoList.get(position);
        holder.teaName.setText(teaInfo.getName());
        holder.teaOrigin.setText(teaInfo.getDetail());
        holder.teaImage.setImageResource(teaInfo.getImageResId());
    }

    @Override
    public int getItemCount() {
        return teaInfoList.size();
    }

    public static class TeaCardViewHolder extends RecyclerView.ViewHolder {
        TextView teaName;
        TextView teaOrigin;
        ImageView teaImage;

        public TeaCardViewHolder(@NonNull View itemView) {
            super(itemView);
            teaName = itemView.findViewById(R.id.tv_tea_name);
            teaOrigin = itemView.findViewById(R.id.tv_tea_origin);
            teaImage = itemView.findViewById(R.id.iv_tea_image);
        }
    }
}