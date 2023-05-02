package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyPagerAdapter extends FragmentStateAdapter {
    public MyPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //根据position返回不同的Fragment
        if (position == 0) {
            return new mianfrg();
//            return new tablefrg();
//            return new favfrg();
        } else {
            return new favfrg();
        }
    }

    @Override
    public int getItemCount() {
        //返回要显示的Fragment数量
        return 2;
    }
}
