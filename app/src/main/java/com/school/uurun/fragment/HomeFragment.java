package com.school.uurun.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.school.uurun.R;
import com.school.uurun.adapter.SwiperAdapter;
import com.school.uurun.view.ReceiveOrderActivity;
import com.school.uurun.view.SubmitOrderActivity;

/**
 * 首页
 */
public class HomeFragment extends Fragment {
    private Context context;

    public HomeFragment(Context context) {
        this.context = context;
    }

    private ViewPager2 vpSwiper;
    private LinearLayout llTakeOut;
    private LinearLayout llExpress;
    private String userType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        // 初始化控件以及绑定事件监听
        initViewAndBindEvent(view);
        return view;
    }

    private void initViewAndBindEvent(View view) {
        vpSwiper = view.findViewById(R.id.vp_swiper);
        llTakeOut = view.findViewById(R.id.ll_take_out);
        llExpress = view.findViewById(R.id.ll_express);

        // 获取用户信息
        SharedPreferences sharedPreferences = context.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        userType = sharedPreferences.getString("userType", "");

        // 给轮播图设置适配器
        vpSwiper.setAdapter(new SwiperAdapter(context));

        // 快递帮取点击
        llTakeOut.setOnClickListener(v -> jumpToPageWithOrderType("1"));

        // 外卖帮拿点击
        llExpress.setOnClickListener(v -> jumpToPageWithOrderType("2"));
    }

    // 根据用户类型判断,跳转到不同的界面
    private void jumpToPageWithOrderType(String orderType) {
        if ("1".equals(userType)) {
            // 用户入口
            final Intent intent = new Intent(context, SubmitOrderActivity.class);
            // orderType = 1  订单类型: 快递帮取
            intent.putExtra("orderType", orderType);
            startActivity(intent);
        } else {
            // 骑手入口
            final Intent intent = new Intent(context, ReceiveOrderActivity.class);
            // orderType = 1  订单类型: 快递帮取
            intent.putExtra("orderType", orderType);
            startActivity(intent);
        }
    }
}