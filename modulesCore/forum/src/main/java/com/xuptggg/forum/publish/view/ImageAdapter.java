package com.xuptggg.forum.publish.view;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.xuptggg.forum.R;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Uri> mImageUris;
    private OnAddImageClickListener mOnAddImageClickListener;
    private static final int MAX_IMAGE_COUNT = 9;
    public ImageAdapter(Context context) {
        mContext = context;
        mImageUris = new ArrayList<>();
    }

    public void addImages(List<Uri> uris) {
        mImageUris.addAll(uris);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        if (position < mImageUris.size()) {
            // 显示图片
            Uri uri = mImageUris.get(position);
            holder.ivPublishPicture.setVisibility(View.VISIBLE);
            holder.ivAddPicture.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(uri)
                    .into(holder.ivPublishPicture);
        } else if (mImageUris.size() < MAX_IMAGE_COUNT) {
            // 显示灰色加号
            holder.ivPublishPicture.setVisibility(View.GONE);
            holder.ivAddPicture.setVisibility(View.VISIBLE);
            holder.ivAddPicture.setOnClickListener(v -> {
                if (mOnAddImageClickListener != null) {
                    mOnAddImageClickListener.onAddImageClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return Math.min(mImageUris.size() + 1, MAX_IMAGE_COUNT);
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPublishPicture;
        ImageView ivAddPicture;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPublishPicture = itemView.findViewById(R.id.iv_publish_picture);
            ivAddPicture = itemView.findViewById(R.id.iv_add_picture);
        }
    }

    public interface OnAddImageClickListener {
        void onAddImageClick();
    }
}