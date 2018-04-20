package com.duozhuan.bitalk.ui.discover;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DiscoverAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> mTitleList;

    public DiscoverAdapter(FragmentManager manager, List<Fragment> fragments1){
        super(manager);
        fragments = fragments1;
    }

    @Override
    public Fragment getItem(int position){
        return fragments.get(position);
    }

    @Override
    public int getCount(){
        return fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

    public void addTitles(List<String> titleList){
        mTitleList=new ArrayList<>();
        this.mTitleList.addAll(titleList);
        notifyDataSetChanged();
    }
}
