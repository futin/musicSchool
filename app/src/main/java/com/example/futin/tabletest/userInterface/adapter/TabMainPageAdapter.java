package com.example.futin.tabletest.userInterface.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.futin.tabletest.userInterface.fragments.FragmentDisplayData;
import com.example.futin.tabletest.userInterface.fragments.FragmentEnterData;

/**
 * Created by Futin on 3/7/2015.
 */
public class TabMainPageAdapter extends FragmentPagerAdapter {

    public TabMainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentEnterData();
            case 1: return new FragmentDisplayData();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:	return "Enter Data";
            case 1:	return "Display Data";
        }
        return null;
    }
}
