package com.example.android_tkpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android_tkpm.adapter.OrderFragmentAdapter;
import com.example.android_tkpm.adapter.VPAdapter;
import com.example.android_tkpm.fragments.DeliveredFragment;
import com.example.android_tkpm.fragments.DeliveringFragment;
import com.example.android_tkpm.fragments.UnconfirmedFragment;
import com.google.android.material.tabs.TabLayout;

public class OrderHistoryActivity extends AppCompatActivity {

    private int type = 0;

    private ImageButton backButton;
    private TextView titleToolbar;

    private TabLayout tabLayout;
//    private ViewPager viewPager;
    private ViewPager2 viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        type = getIntent().getIntExtra("type", 0);

        mapping();
    }

    private void mapping() {
        backButton = (ImageButton) findViewById(R.id.backBtn);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_order);
        viewPager = (ViewPager2) findViewById(R.id.viewpager);

        titleToolbar.setText(("My Order"));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        viewPager.setOffscreenPageLimit(0);
//        tabLayout.setupWithViewPager(viewPager);
//
//
//        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        vpAdapter.addFragment(new UnconfirmedFragment(), "Unconfirmed");
//        vpAdapter.addFragment(new DeliveringFragment(), "Delivering");
//        vpAdapter.addFragment(new DeliveredFragment(), "Delivered");
//
//        viewPager.setAdapter(vpAdapter);
//        viewPager.setCurrentItem(1);


//        viewPager.setOffscreenPageLimit(1);
        OrderFragmentAdapter orderFragmentAdapter = new OrderFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(orderFragmentAdapter);

//        viewPager.setCurrentItem(1);

        viewPager.setCurrentItem(type);
        viewPager.setCurrentItem(type, false);
        tabLayout.getTabAt(type).select();
        tabLayout.selectTab(tabLayout.getTabAt(type));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}