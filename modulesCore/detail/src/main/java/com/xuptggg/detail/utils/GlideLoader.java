package com.xuptggg.detail.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;

public class GlideLoader {
    private final Context context;
    private Object source;
    private ImageView imageView;
    private RequestOptions options;
    private RequestListener<Drawable> listener;

    private GlideLoader(Context context) {
        this.context = context.getApplicationContext();
        this.options = new RequestOptions();
    }

    public static GlideLoader with(Context context) {
        return new GlideLoader(context);
    }

    public GlideLoader load(Object source) {
        this.source = source;
        return this;
    }

    public GlideLoader placeholder(@DrawableRes int resId) {
        options.placeholder(resId);
        return this;
    }

    public GlideLoader error(@DrawableRes int resId) {
        options.error(resId);
        return this;
    }

    public GlideLoader override(int width, int height) {
        options.override(width, height);
        return this;
    }

    public GlideLoader circleCrop() {
        options.transform(new CircleCrop());
        return this;
    }

    public GlideLoader roundedCorners(int radius) {
        options.transform(new RoundedCorners(radius));
        return this;
    }

    public GlideLoader diskCacheStrategy(DiskCacheStrategy strategy) {
        options.diskCacheStrategy(strategy);
        return this;
    }

    public GlideLoader skipMemoryCache(boolean skip) {
        options.skipMemoryCache(skip);
        return this;
    }

    public GlideLoader listener(RequestListener<Drawable> listener) {
        this.listener = listener;
        return this;
    }

    public void into(ImageView imageView) {
        if (context == null || source == null || imageView == null) return;

        RequestBuilder<Drawable> builder = Glide.with(context)
                .load(source)
                .apply(options);

        if (listener != null) {
            builder.listener(listener);
        }

        builder.into(imageView);
    }

    // 静态工具方法
    public static void clearCache(final Context context) {
        // 清除内存缓存（主线程）
        Glide.get(context.getApplicationContext()).clearMemory();

        // 清除磁盘缓存（子线程）
        new Thread(() -> Glide.get(context.getApplicationContext()).clearDiskCache()).start();
    }

    public static void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    public static void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }
}


/*// 基本使用
GlideLoader.with(context)
    .load("https://example.com/image.jpg")
    .placeholder(R.drawable.placeholder)
    .error(R.drawable.error)
    .into(imageView);

// 圆形图片
GlideLoader.with(context)
    .load(R.drawable.local_image)
    .circleCrop()
    .into(imageView);

// 自定义缓存策略
GlideLoader.with(context)
    .load(file)
    .diskCacheStrategy(DiskCacheStrategy.NONE)
    .skipMemoryCache(true)
    .into(imageView);

// 带加载监听的圆角图片
GlideLoader.with(context)
    .load(uri)
    .roundedCorners(16)
    .listener(new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(...) {
            // 处理加载失败
            return false;
        }

        @Override
        public boolean onResourceReady(...) {
            // 处理加载成功
            return false;
        }
    })
    .into(imageView);*/