package com.school.uurun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.school.uurun.utils.PermissionUtil;
import com.school.uurun.view.LoginActivity;
import com.school.uurun.view.RegisterActivity;

/**
 * APP 启动入口类
 */
public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 首次加载时 申请所需要的权限
        PermissionUtil.checkAndRequestPermission(this, PermissionUtil.PERMISSIONS, 0);

        // 初始化控件以及绑定事件监听
        initViewAndBindEvent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.checkGranted(this, requestCode, permissions, grantResults);
    }

    private void initViewAndBindEvent() {
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(v -> {
            // 去登录界面
            final Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(v -> {
            // 去注册界面
            final Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}