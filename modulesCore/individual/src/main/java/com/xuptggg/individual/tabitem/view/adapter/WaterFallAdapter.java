package com.xuptggg.individual.tabitem.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xuptggg.individual.R;
import com.xuptggg.individual.tabitem.model.ForumInfo;

import java.util.List;

public class WaterFallAdapter extends RecyclerView.Adapter<WaterFallAdapter.ViewHolder> {
    private static final String TAG = "WaterFallAdapter";
    private List<ForumInfo> mWaterFallInfoList;
    private Context mContext;
    public WaterFallAdapter(List<ForumInfo> mWaterFallInfoList, Context context) {
        this.mWaterFallInfoList = mWaterFallInfoList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.recycler_waterfall_item, null);
        ViewHolder holder = new ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForumInfo info = mWaterFallInfoList.get(position);

        holder.title.setText(info.getTitle());

        if (info.getAuthor_url().toString().equals("")) {
            Glide.with(mContext)
                    .load(R.drawable.icon_me)
                    .into(holder.userIcon);
        } else {
            Glide.with(mContext)
                    .load(info.getAuthor_url().toString())
                    .into(holder.userIcon);
        }

        if (info.getAuthor_name().toString().equals("")) {
            holder.userName.setText("匿名奶龙");
        } else {
            holder.userName.setText(info.getAuthor_name());
        }

        holder.loveCount.setText(info.getVotes());

        Glide.with(mContext)
                .load(info.getUrl().toString())
                .into(holder.picture);

        holder.loveIcon.setImageResource(R.drawable.icon_love);

        final boolean[] statusLove = {true};

        holder.loveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusLove[0]) {
                    statusLove[0] = false;
                    holder.loveIcon.setImageResource(R.drawable.icon_love_empty);
                } else {
                    statusLove[0] = true;
                    holder.loveIcon.setImageResource(R.drawable.icon_love);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mWaterFallInfoList == null ? 0 : mWaterFallInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView title;
        ImageView userIcon;
        TextView userName;
        ImageView loveIcon;
        TextView loveCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.iv_waterfall_icon);
            title = itemView.findViewById(R.id.tv_waterfall_title);

            userIcon = itemView.findViewById(R.id.iv_waterfall_user_icon);
            userName = itemView.findViewById(R.id.tv_waterfall_user_name);

            loveIcon = itemView.findViewById(R.id.iv_wf_love_icon);
            loveCount = itemView.findViewById(R.id.tv_wf_love_count);
        }
    }
}
