package com.school.uurun.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.school.uurun.R;
import com.school.uurun.fragment.HomeFragment;
import com.school.uurun.fragment.OrderFragment;
import com.school.uurun.fragment.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";
    private ViewPager2 vp2Container;
    private BottomNavigationView bnvBottomBar;
    final List<Fragment> fragments = new ArrayList<Fragment>() {{
        add(new HomeFragment(MenuActivity.this));
        add(new OrderFragment());
        add(new ProfileFragment());
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initViewAndBindEvent();
    }

    private void initViewAndBindEvent() {
        vp2Container = findViewById(R.id.vp2_container);
        bnvBottomBar = findViewById(R.id.bnv_bottom_bar);

        vp2Container.setUserInputEnabled(false);
        vp2Container.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        vp2Container.setOffscreenPageLimit(fragments.size());
        vp2Container.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                switch (position) {
                    case 0:
                        bnvBottomBar.setSelectedItemId(R.id.home);
                        break;
                    case 1:
                        bnvBottomBar.setSelectedItemId(R.id.order_center);
                        break;
                    case 2:
                        bnvBottomBar.setSelectedItemId(R.id.user_center);
                        break;
                    default:
                }
            }
        });
        vp2Container.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });

        bnvBottomBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    vp2Container.setCurrentItem(0);
                    break;
                case R.id.order_center:
                    vp2Container.setCurrentItem(1);
                    break;
                case R.id.user_center:
                    vp2Container.setCurrentItem(2);
                    break;
                default:
            }
            return true;
        });
    }
}