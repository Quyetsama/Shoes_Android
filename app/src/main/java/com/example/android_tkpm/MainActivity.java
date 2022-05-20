package com.example.android_tkpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android_tkpm.fragments.HomeFragment;
import com.example.android_tkpm.fragments.NotifyFragment;
import com.example.android_tkpm.fragments.UserFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("RESUME", active.getClass().getSimpleName());
    }

    final String TAG = "TAG";

    private static Fragment homeFragment = new HomeFragment(), notifyFragment, userFragment;
    final FragmentManager fm = getSupportFragmentManager();
    public static Fragment active = homeFragment;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapping();

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        fm.beginTransaction().add(R.id.container,homeFragment, "1").commit();
    }

    private void mapping() {
        bottomNavigationView = findViewById(R.id.bottomNav);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home:
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                        fm.beginTransaction().add(R.id.container, homeFragment, "1").commit();
                    }
                    fm.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;
                    break;
                case R.id.notify:
                    if (notifyFragment == null) {
                        notifyFragment = new NotifyFragment();
                        fm.beginTransaction().add(R.id.container, notifyFragment, "2").commit();
                    }
                    fm.beginTransaction().hide(active).show(notifyFragment).commit();
                    active = notifyFragment;
                    break;
                case R.id.user:
                    if (userFragment == null) {
                        userFragment = new UserFragment();
                        fm.beginTransaction().add(R.id.container, userFragment, "3").commit();
                    }
                    fm.beginTransaction().hide(active).show(userFragment).commit();
                    active = userFragment;
                    break;
            }
            return true;
        }
    };
}