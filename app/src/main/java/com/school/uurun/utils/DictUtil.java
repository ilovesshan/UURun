package com.school.uurun.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 字典工具类
 */
public class DictUtil {
    /**
     * 根据 status 获取订单状态
     *
     * @param status code码
     * @return 订单状态
     */
    public static String getTextByOrderStatus(String status) {
        if ("1".equals(status)) {
            return "待接单";
        }
        if ("2".equals(status)) {
            return "配送中";
        }
        if ("3".equals(status)) {
            return "已完成";
        }
        return "";
    }

    /**
     * 根据 type 获取订单类型
     *
     * @param type code码
     * @return 订单类型
     */
    public static String getTextByOrderType(String type) {
        if ("1".equals(type)) {
            return "快递";
        }
        if ("2".equals(type)) {
            return "外卖";
        }
        return "";
    }


    /**
     * 根据 userType 获取用户类型
     *
     * @param userType code码
     * @return 订单状态
     */
    public static String getTextByUserType(String userType) {
        if ("1".equals(userType)) {
            return "普通用户";
        }
        if ("2".equals(userType)) {
            return "骑手用户";
        }
        return "";
    }

}
