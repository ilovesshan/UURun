package com.school.uurun.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.school.uurun.R;
import com.school.uurun.entity.User;
import com.school.uurun.service.UserService;
import com.school.uurun.view.viewcallback.ProfileViewCallback;

/**
 * 个人中心界面
 */
public class UserInfoDetailActivity extends AppCompatActivity implements ProfileViewCallback {
    private EditText etAccount;
    private EditText etNickname;
    private EditText etPassword;
    private EditText etPhone;
    private RadioButton rbNormalUser;
    private RadioButton rbDriverUser;
    private Button btnSaveUserInfo;


    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_detail);

        userService = new UserService(this);

        // 初始化控件以及绑定事件监听
        initViewAndBindEvent();

    }

    private void initViewAndBindEvent() {
        etAccount = findViewById(R.id.et_order_name);
        etNickname = findViewById(R.id.et_nickname);
        etPassword = findViewById(R.id.et_password);
        etPhone = findViewById(R.id.et_phone);
        rbNormalUser = findViewById(R.id.rb_normal_user);
        rbDriverUser = findViewById(R.id.rb_driver_user);
        btnSaveUserInfo = findViewById(R.id.btn_save_user_info);


        // 获取当前登录用户信息
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        final String currentUerId = sharedPreferences.getString("id", "");
        final String currentUserAccount = sharedPreferences.getString("account", "");
        final String currentUserNickname = sharedPreferences.getString("nickname", "");
        final String currentUserPassword = sharedPreferences.getString("password", "");
        final String currentUserUserType = sharedPreferences.getString("userType", "");
        final String currentUserPhone = sharedPreferences.getString("phone", "");


        // 数据回显
        etAccount.setText(currentUserAccount);
        etNickname.setText(currentUserNickname);
        etPassword.setText(currentUserPassword);
        etPhone.setText(currentUserPhone);
        if ("1".equals(currentUserUserType)) {
            rbNormalUser.setChecked(true);
        } else {
            rbDriverUser.setChecked(true);
        }


        // 保存用户信息按钮被点击
        btnSaveUserInfo.setOnClickListener(v -> {
            // 获取用户输入的数据
            final String account = etAccount.getText().toString();
            final String nickname = etNickname.getText().toString();
            final String password = etPassword.getText().toString();
            final String phone = etPhone.getText().toString();

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

            // 构建用户实体对象
            final User user = new User(currentUerId, account, password, nickname, currentUserUserType, phone);
            // 调用 userService方法 处理具体的业务逻辑
            userService.updateUserInfo(user);
        });
    }


    // 用户信息更新成功
    @Override
    public void onUpdateSuccess(User user) {
        Toast.makeText(this, "用户信息更新成功", Toast.LENGTH_SHORT).show();

        // 更新sharedPreferences中的数据
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", user.id);
        editor.putString("account", user.account);
        editor.putString("nickname", user.nickname);
        editor.putString("password", user.password);
        editor.putString("userType", user.userType);
        editor.putString("phone", user.phone);
        editor.apply();
    }

    // 用户信息更新失败
    @Override
    public void onUpdateError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userService != null) {
            userService.removeProfileViewCallback();
        }
    }
}