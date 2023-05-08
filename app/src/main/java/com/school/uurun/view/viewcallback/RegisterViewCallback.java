package com.school.uurun.view.viewcallback;


import com.school.uurun.entity.User;

/**
 * 定义RegisterViewCallback视图相关接口
 */
public interface RegisterViewCallback {

    /**
     * 注册成功回调接口
     *
     * @param user 用户信息
     */
    void onRegisterSuccess(User user);

    /**
     * 注册失败回调接口
     *
     * @param errorMessage 错误原因
     */
    void onRegisterError(String errorMessage);
}
