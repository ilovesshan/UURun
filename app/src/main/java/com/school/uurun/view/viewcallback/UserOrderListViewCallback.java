package com.school.uurun.view.viewcallback;


import com.school.uurun.entity.Order;
import com.school.uurun.entity.User;

import java.util.List;

/**
 * 定义UserOrderListActivity视图相关接口
 */
public interface UserOrderListViewCallback {

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
}
