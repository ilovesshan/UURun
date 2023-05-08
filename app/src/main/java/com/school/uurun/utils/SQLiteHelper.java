package com.school.uurun.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.school.uurun.base.BaseApplication;

/**
 * 数据库连接工具
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper() {
        super( BaseApplication.getAppContext(), "uurun.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 用户表
        db.execSQL("CREATE TABLE t_user(\n" +
                "    id varchar(32), -- 主键ID\n" +
                "    account  varchar(255), -- 账户\n" +
                "    password varchar(255), -- 密码\n" +
                "    nickname varchar(255), -- 昵称\n" +
                "    user_type varchar(1), -- 用户类型（1普通用户、2骑手）\n" +
                "    phone varchar(11) -- 手机号\n" +
                ");");

        // 普通用户
        db.execSQL("insert into t_user values ('1B895BB4D3F74CC0ACC9E470984413EA', 'wyf','123456','ilovesshan','1','14578532211');");
        // 骑手
        db.execSQL("insert into t_user values ('086A84B62D68475D9E85EE92A58837F3', 'jack','123456','筱溜子','2','15454782255');");


        // 订单表
        db.execSQL("CREATE TABLE t_order(\n" +
                "    id varchar(32), -- 主键ID\n" +
                "    order_type varchar(1), -- 订单类型（1快递、2外卖）\n" +
                "    order_goods_name varchar(32), -- 订单物品名称\n" +
                "    submit_user_id varchar(32), -- 下单用户ID\n" +
                "    submit_nickname varchar(32), -- 下单用户昵称\n" +
                "    submit_phone varchar(11), -- 下单用户手机号\n" +
                "    receive_user_id varchar(32), -- 接单用户ID\n" +
                "    receive_nickname varchar(32), -- 接单用户昵称\n" +
                "    receive_phone varchar(11), -- 接单用户手机号\n" +
                "    delivery_address varchar(255), -- 取单地址\n" +
                "    receive_user_address varchar(255), --  配送地址\n" +
                "    status varchar(1), -- 订单状态（1待接单、2进行中、3已完结）\n" +
                "    money real, -- 打赏金额\n" +
                "    note varchar(255), -- 订单备注\n" +
                "    create_time datetime, -- 下单时间\n" +
                "    receive_time datetime, -- 接单时间\n" +
                "    finished_time datetime -- 订单完成时间\n" +
                ");");

        // 待接单订单
        db.execSQL("insert into t_order values ('4C4F7F8E8B6642FEBA386A0C51B2C9FF', '2','两荤一素套饭','1B895BB4D3F74CC0ACC9E470984413EA','ilovesshan','14578532211',null,null,null,'西南科技大学城市学院三食二楼烤鸭拌饭','西南科技大学城市学院学生公寓5栋121','1','5.00','希望尽快送达','2024-05-12 11:12:45', null, null);");
        // 进行中订单
        db.execSQL("insert into t_order values ('8456B92F952F4B2FA172B2D6D287AC9A', '1','3件短袖','1B895BB4D3F74CC0ACC9E470984413EA','ilovesshan','14578532211','086A84B62D68475D9E85EE92A58837F3','jack','15454782255','西南科技大学城市学院菜鸟驿站','西南科技大学城市学院学生公寓3栋520','2','10.00','取件编号分别是：201、251、266，收件人：啊吧啊吧','2024-05-19 14:12:45', '2024-05-19 14:30:15', null);");
        // 已完结订单
        db.execSQL("insert into t_order values ('3735060355E24D169125F6C13094C280', '1','天颛三角支架晾衣杆','1B895BB4D3F74CC0ACC9E470984413EA','ilovesshan','14578532211','086A84B62D68475D9E85EE92A58837F3','jack','15454782255','西南科技大学城市学院菜鸟驿站','西南科技大学城市学院学生公寓2栋220','3','20.00','取件编号是：315，收件人：TXG，重量大概10KG,注意不要破损坏里面物品,谢谢~','2024-05-01 10:12:05', '2024-05-01 10:20:45', '2024-05-01 10:40:55'); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
