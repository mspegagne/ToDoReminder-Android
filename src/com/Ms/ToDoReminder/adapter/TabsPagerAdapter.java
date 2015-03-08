package com.Ms.ToDoReminder.adapter;

/**
 * @author SPEGAGNE Mathieu on 08/03/15.
 * @author https://github.com/mspegagne
 */

import com.Ms.ToDoReminder.AddFragment;
import com.Ms.ToDoReminder.ToDoFragment;
import com.Ms.ToDoReminder.HistoryFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new HistoryFragment();
            case 1:
                // Games fragment activity
                return new ToDoFragment();
            case 2:
                // Movies fragment activity
                return new AddFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}