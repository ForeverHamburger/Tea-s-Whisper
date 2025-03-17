package com.xuptggg.forum.publish.view.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xuptggg.forum.R;
import com.xuptggg.forum.publish.utils.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.animation.Animator;
import android.animation.AnimatorSet;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Uri> mImageUris;
    private OnAddImageClickListener mOnAddImageClickListener;
    private static final int MAX_IMAGE_COUNT = 9;

    public ImageAdapter(Context context, OnAddImageClickListener onAddImageClickListener) {
        mContext = context;
        mImageUris = new ArrayList<>();
        mOnAddImageClickListener = onAddImageClickListener;
    }

    public void addImages(List<Uri> uris) {
        int startPosition = mImageUris.size();
        mImageUris.addAll(uris);
        notifyItemRangeInserted(startPosition, uris.size());
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
            holder.cvPublishPictureContainer.setVisibility(View.VISIBLE);
            holder.ivAddPicture.setVisibility(View.GONE);
            holder.cvDeleteContainer.setVisibility(View.VISIBLE);

            // 应用淡入动画
            AnimatorSet showImageAnimatorSet = new AnimatorSet();
            showImageAnimatorSet.playTogether(
                    AnimationUtils.fadeIn(holder.cvPublishPictureContainer, 200),
                    AnimationUtils.fadeIn(holder.cvDeleteContainer, 200)
            );
            showImageAnimatorSet.start();

            Glide.with(mContext)
                    .load(uri)
                    .into(holder.ivPublishPicture);

            // 设置删除按钮点击事件
            holder.cvDeleteContainer.setOnClickListener(v -> {
                int removedPosition = holder.getAdapterPosition();
                // 应用淡出动画
                AnimatorSet deleteAnimatorSet = new AnimatorSet();
                deleteAnimatorSet.playTogether(
                        AnimationUtils.fadeOut(holder.cvPublishPictureContainer, 200),
                        AnimationUtils.fadeOut(holder.cvDeleteContainer, 200)
                );
                deleteAnimatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mImageUris.remove(removedPosition);
                        notifyItemRemoved(removedPosition);
                        if (removedPosition < mImageUris.size()) {
                            notifyItemRangeChanged(removedPosition, mImageUris.size() - removedPosition);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
                deleteAnimatorSet.start();
            });
        } else if (mImageUris.size() < MAX_IMAGE_COUNT) {
            // 显示灰色加号
            holder.cvPublishPictureContainer.setVisibility(View.GONE);
            holder.ivAddPicture.setVisibility(View.VISIBLE);
            holder.cvDeleteContainer.setVisibility(View.GONE);

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
        CardView cvPublishPictureContainer;
        CardView cvDeleteContainer;
        ImageView ivDeletePicture;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPublishPicture = itemView.findViewById(R.id.iv_publish_picture);
            ivAddPicture = itemView.findViewById(R.id.iv_add_picture);
            cvPublishPictureContainer = itemView.findViewById(R.id.cv_publish_picture_container);
            cvDeleteContainer = itemView.findViewById(R.id.cv_delete_container);
            ivDeletePicture = itemView.findViewById(R.id.iv_delete_picture);
        }
    }

    public interface OnAddImageClickListener {
        void onAddImageClick();
    }
}