package com.example.android_tkpm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.android_tkpm.fragments.DeliveredFragment;
import com.example.android_tkpm.fragments.DeliveringFragment;
import com.example.android_tkpm.fragments.UnconfirmedFragment;

public class OrderFragmentAdapter extends FragmentStateAdapter {
    public OrderFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) {
            return new UnconfirmedFragment();
        }
        else if(position == 1) {
            return new DeliveringFragment();
        }

        return new DeliveredFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
