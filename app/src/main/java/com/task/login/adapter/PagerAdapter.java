package com.task.login.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.task.login.fragment.CurrentUser;
import com.task.login.fragment.UserList;
import com.task.login.fragment.UserRegistration;

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(
            @NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
            fragment = new UserList();
        else if (position == 1)
            fragment = new UserRegistration();
        else if (position == 2)
            fragment = new CurrentUser();

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
            title = "User List";
        else if (position == 1)
            title = "User Registration";
        else if (position == 2)
            title = "Current User";
        return title;
    }
}
