package com.school.uurun.view.viewcallback;


import com.school.uurun.entity.User;

/**
 * 定义LoginActivity视图相关接口
 */
public interface LoginViewCallback {

    /**
     * 登录成功回调接口
     *
     * @param user 用户信息
     */
    void onLoginSuccess(User user);

    /**
     * 登录失败回调接口
     *
     * @param errorMessage 错误原因
     */
    void onLoginError(String errorMessage);
}
