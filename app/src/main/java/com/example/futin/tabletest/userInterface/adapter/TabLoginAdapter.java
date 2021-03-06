package com.example.futin.tabletest.userInterface.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.futin.tabletest.userInterface.fragments.FragmentFindMeOnTheMap;
import com.example.futin.tabletest.userInterface.fragments.FragmentLogin;
import com.example.futin.tabletest.userInterface.fragments.FragmentRegistration;

/**
 * Created by Futin on 3/3/2015.
 */
public class TabLoginAdapter extends FragmentPagerAdapter
{

    public TabLoginAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentLogin();
            case 1: return new FragmentRegistration();
            case 2: return new FragmentFindMeOnTheMap();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:	return "Login";
            case 1:	return "Registration";
            case 2: return "Find me on the Map";
        }
        return null;
    }
}
