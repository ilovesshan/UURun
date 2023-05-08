package com.school.uurun.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.school.uurun.R;
import com.school.uurun.entity.User;
import com.school.uurun.service.UserService;
import com.school.uurun.view.viewcallback.LoginViewCallback;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity implements LoginViewCallback {
    private EditText etAccountOrPhone;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 给Service层注册回调，方便Service将业务处理结果通知给当前界面
        userService = new UserService(this);

        // 初始化控件以及绑定事件监听
        initViewAndBindEvent();

    }

    private void initViewAndBindEvent() {
        etAccountOrPhone = findViewById(R.id.et_account_or_phone);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        // 注册
        btnRegister.setOnClickListener(v -> {
            final Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


        // 登录
        btnLogin.setOnClickListener(v -> {
            final String accountOrPhone = etAccountOrPhone.getText().toString();
            final String password = etPassword.getText().toString();
            // 调用Controller 处理业务
            userService.login(accountOrPhone, password);
        });
    }

    @Override
    public void onLoginSuccess(User user) {
        // 保存用户信息
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", user.id);
        editor.putString("account", user.account);
        editor.putString("nickname", user.nickname);
        editor.putString("password", user.password);
        editor.putString("userType", user.userType);
        editor.putString("phone", user.phone);
        editor.apply();

        // 登录成功跳转到首页
        final Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginError(String errorMessage) {
        // 登录失败Toast提示原因
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userService != null) {
            userService.removeLoginViewCallback();
        }
    }
}