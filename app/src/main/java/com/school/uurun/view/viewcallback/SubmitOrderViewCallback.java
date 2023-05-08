package com.school.uurun.view.viewcallback;

import com.school.uurun.entity.Order;

/**
 * 定义SubmitOrderViewCallback视图的相关接口
 */
public interface SubmitOrderViewCallback {

    /**
     * 下单成功回调接口
     *
     * @param order 订单信息
     */
    void onSubmitOrderSuccess(Order order);

    /**
     * 下单失败回调接口
     *
     * @param errorMessage 错误原因
     */
    void onSubmitOrderError(String errorMessage);
}
