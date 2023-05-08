package com.school.uurun.dao;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.school.uurun.entity.Order;
import com.school.uurun.entity.User;
import com.school.uurun.utils.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 连接数据库 负责对订单表进行增删改查
 */
public class OrderDao {
    private SQLiteHelper sqLiteHelper;

    public OrderDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }


    /**
     * 根据下单用户ID + 订单状态 + 订单类型 订单信息(主要查询用户相关的订单)
     *
     * @param userId    用户ID
     * @param status    订单状态
     * @param orderType 订单类型
     * @return 订单信息
     */
    public List<Order> selectUserOrderByUserIdAndStatusAndOrderType(String userId, String status, String orderType) {
        final SQLiteDatabase readableDatabase = sqLiteHelper.getReadableDatabase();
        String sql = "select * from t_order where submit_user_id = ?  and status = ? and order_type = ? order by create_time desc";
        final Cursor cursor = readableDatabase.rawQuery(sql, new String[]{userId, status, orderType});
        return getSingleUserByCursor(cursor);
    }


    /**
     * 根据骑手用户ID + 订单状态订单信息(主要查询骑手相关的订单)
     *
     * @param driverUserId 骑手ID
     * @param status       订单状态
     * @param orderType    订单类型
     * @return 订单信息
     */
    public List<Order> selectDriverOrderByUserIdAndStatus(String driverUserId, String status, String orderType) {
        final SQLiteDatabase readableDatabase = sqLiteHelper.getReadableDatabase();
        String sql = "select * from t_order where receive_user_id = ? and status = ? and order_type = ? order by create_time desc";
        final Cursor cursor = readableDatabase.rawQuery(sql, new String[]{driverUserId, status, orderType});
        return getSingleUserByCursor(cursor);
    }


    /**
     * 根据订单状态查询订单信息
     *
     * @param status 订单状态
     * @return 订单信息
     */
    public List<Order> selectOrderByStatusAndOrderType(String status, String orderType) {
        final SQLiteDatabase readableDatabase = sqLiteHelper.getReadableDatabase();
        String sql = "select * from t_order where status = ? and order_type = ? order by create_time desc";
        final Cursor cursor = readableDatabase.rawQuery(sql, new String[]{status, orderType});
        return getSingleUserByCursor(cursor);
    }


    /**
     * 根据ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    public Order selectOrderById(String orderId) {
        final SQLiteDatabase readableDatabase = sqLiteHelper.getReadableDatabase();
        final Cursor cursor = readableDatabase.rawQuery("select * from t_order where id = ?", new String[]{orderId});
        final List<Order> orderList = getSingleUserByCursor(cursor);
        return orderList == null ? null : orderList.get(0);
    }


    /**
     * 更新订单信息
     *
     * @param order 订单信息
     * @return 受影响行数
     */
    public long updateOrderInfo(Order order) {
        final SQLiteDatabase writableDatabase = sqLiteHelper.getWritableDatabase();
        final ContentValues values = new ContentValues();
        // 下单之后 更新订单时只有这几个字段会发生变化
        values.put("receive_user_id", order.receiveUserId); // 接单用户ID
        values.put("receive_nickname", order.receiveNickname); // 接单用户昵称
        values.put("receive_phone", order.receivePhone); // 接单用户手机号
        values.put("status", order.status); // 订单状态（1待接单、2进行中、3已完结）
        values.put("receive_time", order.receiveTime); // 接单时间
        values.put("finished_time", order.finishedTime); //订单完成时间
        return writableDatabase.update("t_order", values, "id = ?", new String[]{order.id});
    }

    /**
     * 新增订单信息
     *
     * @param order 订单信息
     * @return 受影响行数
     */
    public long insertOrderInfo(Order order) {
        final SQLiteDatabase writableDatabase = sqLiteHelper.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put("id", order.id);
        values.put("order_type", order.orderType);
        values.put("order_goods_name", order.orderGoodsName);
        values.put("submit_user_id", order.submitUserId);
        values.put("submit_nickname", order.submitNickname);
        values.put("submit_phone", order.submitPhone);
        values.put("receive_user_id", order.receiveUserId);
        values.put("receive_nickname", order.receiveNickname);
        values.put("receive_phone", order.receivePhone);
        values.put("delivery_address", order.deliveryAddress);
        values.put("receive_user_address", order.receiveUserAddress);
        values.put("status", order.status);
        values.put("money", order.money);
        values.put("note", order.note);
        values.put("create_time", order.createTime);
        values.put("receive_time", order.receiveTime);
        values.put("finished_time", order.finishedTime);
        return writableDatabase.insert("t_order", null, values);
    }

    // 公用方法 直接从Cursor对象中获取Order信息并返回
    @NonNull
    @SuppressLint("Range")
    private List<Order> getSingleUserByCursor(Cursor cursor) {
        List<Order> orderList = new ArrayList<>();
        while (cursor.moveToNext()) {
            // 封装查询到的数据
            final String id = cursor.getString(cursor.getColumnIndex("id"));
            final String orderType = cursor.getString(cursor.getColumnIndex("order_type"));
            final String orderGoodsName = cursor.getString(cursor.getColumnIndex("order_goods_name"));
            final String submitUserId = cursor.getString(cursor.getColumnIndex("submit_user_id"));
            final String submitNickname = cursor.getString(cursor.getColumnIndex("submit_nickname"));
            final String submitPhone = cursor.getString(cursor.getColumnIndex("submit_phone"));
            final String receiveUserId = cursor.getString(cursor.getColumnIndex("receive_user_id"));
            final String receiveNickname = cursor.getString(cursor.getColumnIndex("receive_nickname"));
            final String receivePhone = cursor.getString(cursor.getColumnIndex("receive_phone"));
            final String deliveryAddress = cursor.getString(cursor.getColumnIndex("delivery_address"));
            final String receiveUserAddress = cursor.getString(cursor.getColumnIndex("receive_user_address"));
            final String status = cursor.getString(cursor.getColumnIndex("status"));
            final String money = cursor.getString(cursor.getColumnIndex("money"));
            final String note = cursor.getString(cursor.getColumnIndex("note"));
            final String createTime = cursor.getString(cursor.getColumnIndex("create_time"));
            final String receiveTime = cursor.getString(cursor.getColumnIndex("receive_time"));
            final String finishedTime = cursor.getString(cursor.getColumnIndex("finished_time"));
            final Order order = new Order(
                    id, orderType, orderGoodsName, submitUserId, submitNickname, submitPhone, receiveUserId, receiveNickname, receivePhone,
                    deliveryAddress, receiveUserAddress, status, money, note, createTime, receiveTime, finishedTime
            );
            orderList.add(order);
        }
        return orderList;
    }
}
