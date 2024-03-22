package com.example.appbanhangandroid;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appbanhangandroid.fragments.CartScreen;
import com.example.appbanhangandroid.fragments.FavoriteScreen;
import com.example.appbanhangandroid.fragments.HomeScreen;
import com.example.appbanhangandroid.fragments.SettingScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNav extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bottom_nav);

        bottomNav = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frameLayout);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.navHome) {
                    loadFragment(new HomeScreen(),false);
                } else if (itemId == R.id.navCart) {
                    loadFragment(new CartScreen(),false);
                } else if (itemId == R.id.navFavorite) {
                    loadFragment(new FavoriteScreen(),false);
                } else if (itemId == R.id.navSetting) {
                    loadFragment(new SettingScreen(),false);
                }

                return true;
            }
        });
        loadFragment(new HomeScreen(),true);
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(isAppInitialized){
            fragmentTransaction.add(R.id.frameLayout,fragment);
        }else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }
        fragmentTransaction.commit();
    }
}