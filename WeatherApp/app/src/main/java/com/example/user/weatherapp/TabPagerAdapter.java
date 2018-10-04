package com.example.user.weatherapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter{

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new DailyFragment();
            case 1:
                return new ForecastFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
