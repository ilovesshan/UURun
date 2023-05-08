package com.school.uurun.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.school.uurun.R;

// 代码抽取 用来初始化RecycleView的每一项组合
public class OrderInnerHolder extends RecyclerView.ViewHolder {
    public TextView tvOrderGoodsName;
    public TextView tvOrderStatusAndType;
    public TextView tvSubmitOrReceiveUserKey;
    public TextView tvUserName;
    public TextView tvUserPhone;
    public TextView tvMoney;
    public TextView tvDeliveryAddress;
    public TextView tvReceiveUserAddress;
    public TextView tvCreateTime;
    public TextView tvReceiveTimeKey;
    public TextView tvReceiveTime;
    public TextView tvFinishedTime;
    public TextView tvFinishedTimeKey;
    public TextView tvNote;
    public Button btnConcatDriver;
    public Button btnGrabOnOrder;
    public Button btnConcatUser;
    public Button btnFinishOrder;

    public OrderInnerHolder(@NonNull View itemView) {
        super(itemView);
        tvOrderGoodsName = itemView.findViewById(R.id.tv_order_goods_name);
        tvOrderStatusAndType = itemView.findViewById(R.id.tv_order_status_and_type);
        tvSubmitOrReceiveUserKey = itemView.findViewById(R.id.tv_submit_or_receive_user_key);
        tvUserName = itemView.findViewById(R.id.tv_user_name);
        tvUserPhone = itemView.findViewById(R.id.tv_user_phone);
        tvMoney = itemView.findViewById(R.id.tv_money);
        tvDeliveryAddress = itemView.findViewById(R.id.tv_delivery_address);
        tvReceiveUserAddress = itemView.findViewById(R.id.tv_receive_user_address);
        tvCreateTime = itemView.findViewById(R.id.tv_create_time);
        tvReceiveTime = itemView.findViewById(R.id.tv_receive_time);
        tvReceiveTimeKey = itemView.findViewById(R.id.tv_receive_time_key);
        tvFinishedTime = itemView.findViewById(R.id.tv_finished_time);
        tvFinishedTimeKey = itemView.findViewById(R.id.tv_finished_time_key);
        tvNote = itemView.findViewById(R.id.tv_note);
        btnConcatDriver = itemView.findViewById(R.id.btn_concat_driver);
        btnGrabOnOrder = itemView.findViewById(R.id.btn_grab_on_order);
        btnConcatUser = itemView.findViewById(R.id.btn_concat_user);
        btnFinishOrder = itemView.findViewById(R.id.btn_finish_order);
    }
}