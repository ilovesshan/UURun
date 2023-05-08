package com.school.uurun.view.viewcallback;


import com.school.uurun.entity.Order;

import java.util.List;

/**
 * 定义 ReceiveOrderActivity视图相关接口
 */
public interface ReceiveOrderViewCallback {

    /**
     * 请求订单数据成功回调接口
     *
     * @param orderList 订单数据
     */
    void onLoadSuccess(List<Order> orderList);

    /**
     * 请求订单数据为空
     *
     */
    void onLoadEmpty();

    /**
     * 请求订单数据失败回调接口
     *
     * @param errorMessage 错误原因
     */
    void onLoadError(String errorMessage);



    /**
     * 更新订单数据失败回调接口
     *
     * @param errorMessage 错误原因
     */
    void onUpdateError(String errorMessage);

    /**
     * 更新订单数据失败回调接口
     */
    void onUpdateSuccess();
}
