package com.school.uurun.dao;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.school.uurun.base.BaseApplication;
import com.school.uurun.entity.User;
import com.school.uurun.utils.SQLiteHelper;

/**
 * 连接数据库 负责对用户表进行增删改查
 */
public class UserDao {
    private SQLiteHelper sqLiteHelper = new SQLiteHelper();
    /**
     * 根据账户/手机号 + 密码 查询用户
     *
     * @param accountOrPhone 账户/手机号
     * @param pwd            密码
     * @return 用户信息
     */

    public User selectUserByAccountOrPhoneAndPassword(String accountOrPhone, String pwd) {
        final SQLiteDatabase readableDatabase = sqLiteHelper.getReadableDatabase();
        // String sql = "select * from t_user where (account = " + accountOrPhone + "or phone = " + accountOrPhone + ") and password = " + password;
        String sql = "select * from t_user where (account = ? or phone = ?) and password = ?";
        final Cursor cursor = readableDatabase.rawQuery(sql, new String[]{accountOrPhone, accountOrPhone, pwd});
        return getSingleUserByCursor(cursor);
    }

    /**
     * 根据ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */

    public User selectUserById(String userId) {
        final SQLiteDatabase readableDatabase = sqLiteHelper.getReadableDatabase();
        final Cursor cursor = readableDatabase.rawQuery("select * from t_user where id = ?", new String[]{userId});
        return getSingleUserByCursor(cursor);
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param phone 用户ID
     * @return 用户信息
     */

    public User selectUserByPhone(String phone) {
        final SQLiteDatabase readableDatabase = sqLiteHelper.getReadableDatabase();
        final Cursor cursor = readableDatabase.rawQuery("select * from t_user where phone = ?", new String[]{phone});
        return getSingleUserByCursor(cursor);
    }

    /**
     * 根据account查询用户信息
     *
     * @param account 用户ID
     * @return 用户信息
     */

    public User selectUserByAccount(String account) {
        final SQLiteDatabase readableDatabase = sqLiteHelper.getReadableDatabase();
        final Cursor cursor = readableDatabase.rawQuery("select * from t_user where account = ?", new String[]{account});
        return getSingleUserByCursor(cursor);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 受影响行数
     */
    public long updateUserInfo(User user) {
        final SQLiteDatabase writableDatabase = sqLiteHelper.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put("account", user.account);
        values.put("password", user.password);
        values.put("nickname", user.nickname);
        values.put("user_type", user.userType);
        values.put("phone", user.phone);
        return writableDatabase.update("t_user", values, "id = ?", new String[]{user.id});
    }

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 受影响行数
     */
    public long insertUserInfo(User user) {
        final SQLiteDatabase writableDatabase = sqLiteHelper.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put("id", user.id);
        values.put("account", user.account);
        values.put("password", user.password);
        values.put("nickname", user.nickname);
        values.put("user_type", user.userType);
        values.put("phone", user.phone);
        return writableDatabase.insert("t_user", null, values);
    }


    // 公用方法 直接从Cursor对象中获取User信息并返回
    @SuppressLint("Range")
    @Nullable
    private User getSingleUserByCursor(Cursor cursor) {
        if (cursor.moveToNext()) {
            // 封装查询到的数据
            final String id = cursor.getString(cursor.getColumnIndex("id"));
            final String account = cursor.getString(cursor.getColumnIndex("account"));
            final String password = cursor.getString(cursor.getColumnIndex("password"));
            final String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
            final String userType = cursor.getString(cursor.getColumnIndex("user_type"));
            final String phone = cursor.getString(cursor.getColumnIndex("phone"));
            return new User(id, account, password, nickname, userType, phone);
        } else {
            return null;
        }
    }
}
