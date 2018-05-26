package com.mdirect.mnews.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mdirect.mnews.fragment.FragmentItemNews;

import java.util.ArrayList;
import java.util.List;

import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;

/**
 * Created by Beni on 23/03/2018.
 */

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    private Context ctx;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabTitles = new ArrayList<>();

    private Session session;

    public TabsPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        this.ctx = ctx;
        session = new Session(this.ctx, new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });
    }

    @Override
    public Fragment getItem(int position) {
        return  fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    public void initFragment(ArrayList<String> dataMenu){
        for (String s : dataMenu){
            fragments.add(new FragmentItemNews().newInstance(s));
            tabTitles.add(s);
        }
    }
}