package com.school.uurun.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.school.uurun.R;
import com.school.uurun.adapter.SwiperAdapter;

/**
 * 首页界面
 */
public class HomeActivity extends AppCompatActivity {
    private ViewPager2 vpSwiper;
    private LinearLayout llTakeOut;
    private LinearLayout llExpress;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // 获取用户信息
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        userType = sharedPreferences.getString("userType", "");

        // 初始化控件以及绑定事件监听
        initViewAndBindEvent();

    }

    private void initViewAndBindEvent() {
        vpSwiper = findViewById(R.id.vp_swiper);
        llTakeOut = findViewById(R.id.ll_take_out);
        llExpress = findViewById(R.id.ll_express);

        // 给轮播图设置适配器
        vpSwiper.setAdapter(new SwiperAdapter(this));

        // 快递帮取点击
        llTakeOut.setOnClickListener(v -> jumpToPageWithOrderType("1"));

        // 外卖帮拿点击
        llExpress.setOnClickListener(v -> jumpToPageWithOrderType("2"));
    }

    // 创建上下文菜单
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_options, menu);
        return true;
    }

    // 监听菜单点击
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_info:
                //跳转到个人信息界面
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                break;
            case R.id.logout:
                //跳转到登录界面
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    // 根据用户类型判断,跳转到不同的界面
    private void jumpToPageWithOrderType(String orderType) {
        if ("1".equals(userType)) {
            // 用户入口
            final Intent intent = new Intent(HomeActivity.this, SubmitOrderActivity.class);
            // orderType = 1  订单类型: 快递帮取
            intent.putExtra("orderType", orderType);
            startActivity(intent);
        } else {
            // 骑手入口
            final Intent intent = new Intent(HomeActivity.this, ReceiveOrderActivity.class);
            // orderType = 1  订单类型: 快递帮取
            intent.putExtra("orderType", orderType);
            startActivity(intent);
        }
    }
}