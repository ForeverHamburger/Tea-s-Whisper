package com.xuptggg.individual.personal.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.xuptggg.individual.personal.view.TabFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<TabFragment> fragmentList;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity
            , List<TabFragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList == null ? null : fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList == null ? 0:fragmentList.size();
    }
}
