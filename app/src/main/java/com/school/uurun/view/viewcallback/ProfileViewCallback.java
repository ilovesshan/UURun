package com.school.uurun.view.viewcallback;


import com.school.uurun.entity.User;

/**
 * 定义ProfileViewCallback视图相关接口
 */
public interface ProfileViewCallback {

    /**
     * 用户信息更新成功回调接口
     *
     * @param user 用户信息
     */
    void onUpdateSuccess(User user);

    /**
     * 用户信息更新失败回调接口
     *
     * @param errorMessage 错误原因
     */
    void onUpdateError(String errorMessage);
}
