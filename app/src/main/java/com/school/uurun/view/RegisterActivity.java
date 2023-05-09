package com.school.uurun.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.school.uurun.R;
import com.school.uurun.entity.User;
import com.school.uurun.service.UserService;
import com.school.uurun.view.viewcallback.RegisterViewCallback;

import java.util.UUID;

/**
 * 注册界面
 */
public class RegisterActivity extends AppCompatActivity implements RegisterViewCallback {
    private static final String TAG = "RegisterActivity";

    private EditText etAccount;
    private EditText etNickname;
    private EditText etPassword;
    private EditText etPhone;
    private RadioButton rbNormalUser;
    private Button btnRegister;
    private Button btnLogin;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // 给Service层注册回调，方便Service将业务处理结果通知给当前界面
        userService = new UserService(this);

        // 初始化控件以及绑定事件监听
        initViewAndBindEvent();

    }

    private void initViewAndBindEvent() {
        // 绑定控件
        etAccount = findViewById(R.id.et_order_name);
        etNickname = findViewById(R.id.et_nickname);
        etPassword = findViewById(R.id.et_password);
        etPhone = findViewById(R.id.et_phone);
        rbNormalUser = findViewById(R.id.rb_normal_user);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        // 登录
        btnLogin.setOnClickListener(v -> {
            final Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // 注册
        btnRegister.setOnClickListener(v -> {
            // 获取用户输入的数据
            final String id = UUID.randomUUID().toString().replaceAll("-", "");
            final String account = etAccount.getText().toString();
            final String nickname = etNickname.getText().toString();
            final String password = etPassword.getText().toString();
            final String phone = etPhone.getText().toString();
            final String userType = rbNormalUser.isChecked() ? "1" : "2";

            // 对数据进行校验
            if (TextUtils.isEmpty(account)) {
                Toast.makeText(this, "账户不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(nickname)) {
                Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            final User user = new User(id, account, password, nickname, userType, phone);
            // 调用Service层处理逻辑
            userService.register(user);
        });
    }

    @Override
    public void onRegisterSuccess(User user) {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        // 注册成功，去登录界面登录
        final Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRegisterError(String errorMessage) {
        // 注册失败Toast提示原因
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userService != null) {
            userService.removeRegisterViewCallback();
        }
    }
}