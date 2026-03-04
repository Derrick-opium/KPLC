package com.example.mypower;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Viewpage extends FragmentStateAdapter {
    public Viewpage(FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new
                        Meter();
            case 1:
                return new
                        Activty();

            default:
                return new
                        Meter();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
