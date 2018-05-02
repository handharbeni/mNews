package com.mdirect.mnews.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.mdirect.mnews.MainActivity;
import com.mdirect.mnews.fragment.FragmentItemNews;

import java.util.List;

import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;

/**
 * Created by Beni on 23/03/2018.
 */

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    private Context ctx;
    private List<String> data;
    private Fragment[] fragments;
    private Session session;

    public TabsPagerAdapter(Context ctx, FragmentManager fm, List<String> data) {
        super(fm);
        this.ctx = ctx;
        this.data = data;
        fragments = new Fragment[data.size()];
        session = new Session(this.ctx, new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        String id = data.get(position);

        FragmentItemNews dinamisFragment = new FragmentItemNews();
        dinamisFragment.setIds(id);
        fragment = dinamisFragment;

        session.setCustomParams(MainActivity.KEY_POSITION, 0);
        if (fragments[position] == null) {
            session.setCustomParams(MainActivity.KEY_POSITION, position);
            fragments[position] = fragment;
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}