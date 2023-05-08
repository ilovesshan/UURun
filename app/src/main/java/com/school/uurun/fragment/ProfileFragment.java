package com.school.uurun.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.school.uurun.R;
import com.school.uurun.utils.DictUtil;
import com.school.uurun.view.LoginActivity;
import com.school.uurun.view.UserInfoDetailActivity;


public class ProfileFragment extends Fragment {
    private Context context;
    private View view;

    public ProfileFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 初始化控件以及绑定事件监听
        initViewAndBindEvent(view);
    }

    private void initViewAndBindEvent(View view) {
        TextView tvNickname = view.findViewById(R.id.tv_nickname);
        TextView tvUserType = view.findViewById(R.id.tv_user_type);
        TextView tvPhone = view.findViewById(R.id.tv_phone);

        RelativeLayout rlUserCard = view.findViewById(R.id.rl_user_card);
        RelativeLayout rlShareApp = view.findViewById(R.id.rl_share_app);
        RelativeLayout rlAboutApp = view.findViewById(R.id.rl_about_app);
        RelativeLayout rlLogoutApp = view.findViewById(R.id.rl_logout_app);

        // 获取当前登录用户信息
        SharedPreferences sharedPreferences = context.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        final String currentUserNickname = sharedPreferences.getString("nickname", "");
        final String userType = sharedPreferences.getString("userType", "");
        final String phone = sharedPreferences.getString("phone", "");

        // 数据回显
        tvNickname.setText(currentUserNickname);
        tvUserType.setText(DictUtil.getTextByUserType(userType));
        tvPhone.setText(phone);

        // 用户信息卡被点击
        rlUserCard.setOnClickListener(v -> {
            startActivity(new Intent(context, UserInfoDetailActivity.class));
        });

        // 推广有奖被点击
        rlShareApp.setOnClickListener(v -> {
            Toast.makeText(context, "推广有奖功能正在开发中，敬请期待...", Toast.LENGTH_SHORT).show();
        });

        // 关于软件被点击
        rlAboutApp.setOnClickListener(v -> {
            Toast.makeText(context, "UURun power by android version 1.0.0", Toast.LENGTH_SHORT).show();
        });

        // 退出登录被点击
        rlLogoutApp.setOnClickListener(v -> {
            //跳转到登录界面
            startActivity(new Intent(context, LoginActivity.class));
        });
    }
}