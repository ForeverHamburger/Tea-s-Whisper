package com.xuptggg.guidepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.jem.liquidswipe.base.LiquidSwipeLayout;
import com.jem.liquidswipe.clippathprovider.LiquidSwipeClipPathProvider;
import com.xuptggg.guidepage.R;

import java.util.List;

public class SwipePagerAdapter extends PagerAdapter {

    private Context context;
    private LiquidSwipeClipPathProvider[] liquidSwipeClipPathProviders;
    private List<Integer> backgroundColorArray;
    private List<Integer> resourceArray;
    private List<String> titleArray;

    public SwipePagerAdapter(Context context, LiquidSwipeClipPathProvider[] liquidSwipeClipPathProviders,
                             List<Integer> backgroundColorArray, List<Integer> resourceArray, List<String> titleArray) {
        this.context = context;
        this.liquidSwipeClipPathProviders = liquidSwipeClipPathProviders;
        this.backgroundColorArray = backgroundColorArray;
        this.resourceArray = resourceArray;
        this.titleArray = titleArray;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.fragment_dummy, container, false);

        int colorIndex = position % titleArray.size();
        layout.setBackgroundColor(backgroundColorArray.get(colorIndex));

//        LottieAnimationView lottieAnimationView = layout.findViewById(R.id.lottieAnimationView);
//        lottieAnimationView.setAnimation(resourceArray[colorIndex]);
//        lottieAnimationView.repeatCount(LottieDrawable.INFINITE);
//        lottieAnimationView.repeatMode(LottieDrawable.REVERSE);
//        lottieAnimationView.playAnimation();

        TextView fragmentTextView = layout.findViewById(R.id.tv_dummy_content);
        fragmentTextView.setText(titleArray.get(colorIndex));

        LiquidSwipeLayout liquidSwipeLayout = (LiquidSwipeLayout) layout;
        liquidSwipeLayout.setClipPathProvider(liquidSwipeClipPathProviders[colorIndex]);

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    @Override
    public int getCount() {
        return titleArray.size() * 20;
    }
}
