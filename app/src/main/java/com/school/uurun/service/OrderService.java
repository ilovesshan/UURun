package com.school.uurun.service;

import com.school.uurun.dao.OrderDao;
import com.school.uurun.entity.Order;
import com.school.uurun.view.viewcallback.ReceiveOrderListViewCallback;
import com.school.uurun.view.viewcallback.ReceiveOrderViewCallback;
import com.school.uurun.view.viewcallback.SubmitOrderViewCallback;
import com.school.uurun.view.viewcallback.UserOrderListViewCallback;

import java.util.List;

/**
 * 负责处理订单模块的逻辑
 */
public class OrderService {
    // View界面的引用
    private SubmitOrderViewCallback submitOrderViewCallback;
    private UserOrderListViewCallback userOrderListViewCallback;
    private ReceiveOrderViewCallback receiveOrderViewCallback;
    private ReceiveOrderListViewCallback receiveOrderListViewCallback;
    private final OrderDao orderDao = new OrderDao();

    public OrderService(SubmitOrderViewCallback submitOrderViewCallback) {
        this.submitOrderViewCallback = submitOrderViewCallback;
    }

    public OrderService(UserOrderListViewCallback userOrderListViewCallback) {
        this.userOrderListViewCallback = userOrderListViewCallback;
    }

    public OrderService(ReceiveOrderViewCallback receiveOrderViewCallback) {
        this.receiveOrderViewCallback = receiveOrderViewCallback;
    }

    public OrderService(ReceiveOrderListViewCallback receiveOrderListViewCallback) {
        this.receiveOrderListViewCallback = receiveOrderListViewCallback;
    }

    /**
     * 根据下单用户ID + 订单状态 + 订单类型 订单信息(主要查询用户相关的订单)
     *
     * @param userId    用户ID
     * @param status    订单状态
     * @param orderType 订单类型
     */
    public void selectUserOrderByUserIdAndStatusAndOrderType(String userId, String status, String orderType) {
        if (userOrderListViewCallback != null) {
            final List<Order> orderList = orderDao.selectUserOrderByUserIdAndStatusAndOrderType(userId, status, orderType);
            if (orderList == null || orderList.size() == 0) {
                userOrderListViewCallback.onLoadEmpty();
                return;
            }
            userOrderListViewCallback.onLoadSuccess(orderList);
        }
    }


    /**
     * 根据下单用户ID  + 订单状态 订单信息(主要查询用户相关的订单)
     *
     * @param userId 用户ID
     * @param status 订单状态
     */
    public void selectUserOrderByUserIdAndStatus(String userId, String status) {
        if (userOrderListViewCallback != null) {
            final List<Order> orderList = orderDao.selectUserOrderByUserIdAnddStatus(userId, status);
            if (orderList == null || orderList.size() == 0) {
                userOrderListViewCallback.onLoadEmpty();
                return;
            }
            userOrderListViewCallback.onLoadSuccess(orderList);
        }
    }


    /**
     * 处理下单逻辑
     *
     * @param order 订单信息
     */
    public void submitOrder(Order order) {
        if (submitOrderViewCallback != null) {
            final long affectRows = orderDao.insertOrderInfo(order);
            if (affectRows <= 0) {
                submitOrderViewCallback.onSubmitOrderError("下单失败,请稍后再试");
                return;
            }
            submitOrderViewCallback.onSubmitOrderSuccess(order);
        }
    }


    /**
     * 根据骑手用户ID + 订单状态订单信息(主要查询骑手相关的订单)
     *
     * @param driverUserId 骑手ID
     * @param status       订单状态
     * @param orderType    订单类型
     */
    public void selectDriverOrderByUserIdAndStatusAndOrderType(String driverUserId, String status, String orderType) {
        if (receiveOrderListViewCallback != null) {
            final List<Order> orderList = orderDao.selectDriverOrderByUserIdAndStatusAndOrderType(driverUserId, status, orderType);
            if (orderList == null || orderList.size() == 0) {
                receiveOrderListViewCallback.onLoadEmpty();
                return;
            }
            receiveOrderListViewCallback.onLoadSuccess(orderList);
        }
    }

    /**
     * 根据骑手用户ID + 订单状态订单信息(主要查询骑手相关的订单)
     *
     * @param driverUserId 骑手ID
     * @param status    订单状态
     */
    public void selectDriverOrderByUserIdAndStatus(String driverUserId, String status) {
        if (receiveOrderListViewCallback != null) {
            final List<Order> orderList = orderDao.selectDriverOrderByUserIdAndStatus(driverUserId, status);
            if (orderList == null || orderList.size() == 0) {
                receiveOrderListViewCallback.onLoadEmpty();
                return;
            }
            receiveOrderListViewCallback.onLoadSuccess(orderList);
        }
    }

    /**
     * 更新订单信息
     *
     * @param order  订单信息
     * @param isList 是否是列表页面更新数据(需要区分View的引用)
     */
    public void updateOrderInfo(Order order, boolean isList) {
        if (isList) {
            if (receiveOrderListViewCallback != null) {
                final long affectRows = orderDao.updateOrderInfo(order);
                if (affectRows <= 0) {
                    receiveOrderListViewCallback.onUpdateError("订单状态更新失败,请稍后再试");
                    return;
                }
                receiveOrderListViewCallback.onUpdateSuccess();
            }
        } else {
            if (receiveOrderViewCallback != null) {
                final long affectRows = orderDao.updateOrderInfo(order);
                if (affectRows <= 0) {
                    receiveOrderViewCallback.onUpdateError("抢单失败,请稍后再试");
                    return;
                }
                receiveOrderViewCallback.onUpdateSuccess();
            }
        }
    }

    /**
     * 根据订单状态查询订单信息(查询待接单列表)
     *
     * @param status 订单状态
     * @return 订单信息
     */
    public void selectOrderByStatusAndOrderType(String status, String orderType) {
        if (receiveOrderViewCallback != null) {
            final List<Order> orderList = orderDao.selectOrderByStatusAndOrderType(status, orderType);
            if (orderList == null || orderList.size() == 0) {
                receiveOrderViewCallback.onLoadEmpty();
                return;
            }
            receiveOrderViewCallback.onLoadSuccess(orderList);
        }
    }

    public void removeSubmitOrderViewCallback() {
        if (this.submitOrderViewCallback != null) {
            this.submitOrderViewCallback = null;
        }
    }

    public void removeUserOrderListViewCallback() {
        if (this.userOrderListViewCallback != null) {
            this.userOrderListViewCallback = null;
        }
    }

    public void removeReceiveOrderViewCallback() {
        if (this.receiveOrderViewCallback != null) {
            this.receiveOrderViewCallback = null;
        }
    }

    public void removeReceiveOrderListViewCallback() {
        if (this.receiveOrderListViewCallback != null) {
            this.receiveOrderListViewCallback = null;
        }
    }
}
