package com.school.uurun.service;

import android.content.Context;

import com.school.uurun.base.BaseApplication;
import com.school.uurun.dao.UserDao;
import com.school.uurun.entity.User;
import com.school.uurun.view.viewcallback.LoginViewCallback;
import com.school.uurun.view.viewcallback.ProfileViewCallback;
import com.school.uurun.view.viewcallback.RegisterViewCallback;

/**
 * 负责处理用户模块的逻辑
 */
public class UserService {
    // View界面的引用
    private RegisterViewCallback registerViewCallback;
    private LoginViewCallback loginViewCallback;
    private ProfileViewCallback profileViewCallback;
    private UserDao userDao = null;

    public UserService(RegisterViewCallback registerViewCallback) {
        this.registerViewCallback = registerViewCallback;
        if (userDao == null) {
            userDao = new UserDao((Context) registerViewCallback);
        }
    }

    public UserService(LoginViewCallback loginViewCallback) {
        this.loginViewCallback = loginViewCallback;
        if (userDao == null) {
            userDao = new UserDao((Context) loginViewCallback);
        }
    }

    public UserService(ProfileViewCallback profileViewCallback) {
        this.profileViewCallback = profileViewCallback;
        if (userDao == null) {
            userDao = new UserDao((Context) profileViewCallback);
        }
    }

    /**
     * 处理用户登录逻辑
     *
     * @param accountOrPhone 账户或者手机号
     * @param password       密码
     */
    public void login(String accountOrPhone, String password) {
        if (userDao != null && loginViewCallback != null) {
            final User user = userDao.selectUserByAccountOrPhoneAndPassword(accountOrPhone, password);
            // 用户信息没查到
            if (user == null) {
                loginViewCallback.onLoginError("账户不存在，或者密码输入错误");
            } else {
                // 登录成功
                loginViewCallback.onLoginSuccess(user);
            }
        }
    }

    /**
     * 处理用户信息更新逻辑
     *
     * @param user 用户信息
     */
    public void updateUserInfo(User user) {
        if (userDao != null && profileViewCallback != null) {
            final User selectedUser = userDao.selectUserById(user.id);
            if (selectedUser == null) {
                profileViewCallback.onUpdateError("当前账号信息存在异常,更新失败");
                return;
            }
            final long affectsRows = userDao.updateUserInfo(user);
            if (affectsRows <= 0) {
                profileViewCallback.onUpdateError("更新失败,请检查信息后重试");
                return;
            }
            profileViewCallback.onUpdateSuccess(user);
        }
    }


    /**
     * 处理用户注册逻辑
     *
     * @param user 用户信息
     */
    public void register(User user) {
        if (userDao != null && registerViewCallback != null) {
            User selectedUser = userDao.selectUserByPhone(user.phone);
            // 对数据进行校验
            if (selectedUser != null) {
                registerViewCallback.onRegisterError("该手机号已被注册，换一个试试");
                return;
            }
            selectedUser = userDao.selectUserByAccount(user.account);
            if (selectedUser != null) {
                registerViewCallback.onRegisterError("该账户已被注册，换一个试试");
                return;
            }

            // 可以插入数据了
            final long affectRows = userDao.insertUserInfo(user);
            if (affectRows <= 0) {
                registerViewCallback.onRegisterError("注册失败，请稍后再试");
                return;
            }
            registerViewCallback.onRegisterSuccess(user);
        }
    }


    public void removeRegisterViewCallback() {
        if (this.registerViewCallback != null) {
            this.registerViewCallback = null;
        }
    }

    public void removeLoginViewCallback() {
        if (this.loginViewCallback != null) {
            this.loginViewCallback = null;
        }
    }

    public void removeProfileViewCallback() {
        if (this.profileViewCallback != null) {
            this.profileViewCallback = null;
        }
    }
}
