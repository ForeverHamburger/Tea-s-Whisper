package com.xuptggg.guidepage.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.jem.liquidswipe.base.LiquidSwipeLayout;
import com.jem.liquidswipe.clippathprovider.LiquidSwipeClipPathProvider;
import com.xuptggg.guidepage.R;
import com.xuptggg.guidepage.model.GuideInfo;

import java.util.ArrayList;
import java.util.List;

public class SwipePagerAdapter extends PagerAdapter {

    private Context context;
    private LiquidSwipeClipPathProvider[] liquidSwipeClipPathProviders;
    private List<Integer> indicatorList;
    private List<GuideInfo> guideInfos;

    public SwipePagerAdapter(Context context, LiquidSwipeClipPathProvider[] liquidSwipeClipPathProviders,
                             List<GuideInfo> guideInfos) {
        this.context = context;
        this.liquidSwipeClipPathProviders = liquidSwipeClipPathProviders;
        this.guideInfos = guideInfos;
        indicatorList = new ArrayList<>();
        indicatorList.add(R.drawable.indicator1);
        indicatorList.add(R.drawable.indicator2);
        indicatorList.add(R.drawable.indicator3);
        indicatorList.add(R.drawable.indicator4);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.fragment_dummy, container, false);

        int index = position;

        ImageView picture = layout.findViewById(R.id.iv_dummy_picture);
        TextView subHead = layout.findViewById(R.id.tv_dummy_subheading);
        TextView head = layout.findViewById(R.id.tv_dummy_head);
        TextView content = layout.findViewById(R.id.tv_dummy_content);
        ImageView indicator = layout.findViewById(R.id.iv_dummy_indicator);
        TextView skip = layout.findViewById(R.id.tv_dummy_skip);
        ImageView background1 = layout.findViewById(R.id.iv_dummy_background1);
        ImageView background2 = layout.findViewById(R.id.iv_dummy_background2);

        layout.setBackgroundColor(guideInfos.get(index).getColorResourse());
        picture.setImageResource(guideInfos.get(index).getPictureResourse());
        subHead.setText(guideInfos.get(index).getSubheading());
        head.setText(guideInfos.get(index).getHead());
        content.setText(guideInfos.get(index).getContent());
        indicator.setImageResource(indicatorList.get(index));

        setBackground(background1,background2,position);

        if (position == 2) {
            subHead.setTextColor(Color.WHITE);
            head.setTextColor(Color.WHITE);
            content.setTextColor(Color.WHITE);
            skip.setTextColor(Color.WHITE);
        }

        LiquidSwipeLayout liquidSwipeLayout = (LiquidSwipeLayout) layout;
        liquidSwipeLayout.setClipPathProvider(liquidSwipeClipPathProviders[index]);

        container.addView(layout);
        return layout;
    }

    public void setBackground(ImageView background1,ImageView background2,int postion){
        if (postion == 0) {
            background2.setVisibility(View.GONE);
            background1.setVisibility(View.VISIBLE);
            background1.setImageResource(R.drawable.pic_bg1);
        } else if (postion == 1) {
            background2.setVisibility(View.GONE);
            background1.setVisibility(View.VISIBLE);
            background1.setImageResource(R.drawable.pic_bg1);
        } else if (postion == 2) {
            background2.setVisibility(View.VISIBLE);
            background1.setVisibility(View.GONE);
            background2.setImageResource(R.drawable.pic_bg3);
        } else {
            background2.setVisibility(View.VISIBLE);
            background1.setVisibility(View.GONE);
            background2.setImageResource(R.drawable.pic_bg2);
        }
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
        return guideInfos.size();
    }
}
