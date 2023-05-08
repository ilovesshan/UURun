package com.school.uurun.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.school.uurun.R;
import com.school.uurun.base.BaseApplication;
import com.school.uurun.entity.Order;
import com.school.uurun.utils.DictUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户相关的订单 适配器
 */
@SuppressLint("SetTextI18n")
public class UserOrderListAdapter extends RecyclerView.Adapter<OrderInnerHolder> {
    private final Context context;
    private List<Order> orderList = new ArrayList<>();

    public UserOrderListAdapter(Context context) {
        this.context = context;
    }

    // 更新数据
    public void setOrderList(List<Order> orderList) {
        if (orderList != null && orderList.size() > 0) {
            this.orderList.clear();
            this.orderList.addAll(orderList);
            // 通知数据更新
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public OrderInnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建一个Holder 将布局文件通过构造函数传入
        final View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderInnerHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderInnerHolder viewHolder, int position) {
        // 给每一条订单信息源绑数据
        final Order order = orderList.get(position);
        // 订单物品名称
        viewHolder.tvOrderGoodsName.setText(order.orderGoodsName);
        // 订单类型以及状态
        viewHolder.tvOrderStatusAndType.setText(DictUtil.getTextByOrderType(order.orderType) + " | " + DictUtil.getTextByOrderStatus(order.status));
        // 如果是已下单状态,则显式本人信息,否则就显式骑手信息
        if ("1".equals(order.status)) {
            viewHolder.tvSubmitOrReceiveUserKey.setText("下单用户:");
            viewHolder.tvUserName.setText(order.submitNickname);
            viewHolder.tvUserPhone.setText(order.submitPhone);
        } else {
            viewHolder.tvSubmitOrReceiveUserKey.setText("接单用户:");
            viewHolder.tvUserName.setText(order.receiveNickname);
            viewHolder.tvUserPhone.setText(order.receivePhone);
        }
        // 打赏金额
        viewHolder.tvMoney.setText("￥" + (order.money.contains(".") ? order.money : order.money + ".00"));
        // 取件地址
        viewHolder.tvDeliveryAddress.setText(order.deliveryAddress);
        // 配送地址
        viewHolder.tvReceiveUserAddress.setText(order.receiveUserAddress);
        // 下单时间
        viewHolder.tvCreateTime.setText(order.createTime);
        // 接单时间(此时订单状态是配送中或者已完成才显示)
        if ("2".equals(order.status) || "3".equals(order.status)) {
            viewHolder.tvReceiveTime.setVisibility(View.VISIBLE);
            viewHolder.tvReceiveTimeKey.setVisibility(View.VISIBLE);
            viewHolder.tvReceiveTime.setText(order.receiveTime);
        } else {
            viewHolder.tvReceiveTime.setVisibility(View.GONE);
            viewHolder.tvReceiveTimeKey.setVisibility(View.GONE);
        }
        // 订单完成时间(此时订单状态是已完成才显示)
        if ("3".equals(order.status)) {
            viewHolder.tvFinishedTime.setVisibility(View.VISIBLE);
            viewHolder.tvFinishedTimeKey.setVisibility(View.VISIBLE);
            viewHolder.tvFinishedTime.setText(order.finishedTime);
        } else {
            viewHolder.tvFinishedTime.setVisibility(View.GONE);
            viewHolder.tvFinishedTimeKey.setVisibility(View.GONE);
        }
        // 订单备注
        viewHolder.tvNote.setText(order.note);

        // 联系骑手按钮(用户使用,只有是配送中或者已完结才显示)
        if ("2".equals(order.status) || "3".equals(order.status)) {
            viewHolder.btnConcatDriver.setVisibility(View.VISIBLE);
            viewHolder.btnConcatDriver.setOnClickListener(v -> {
                Toast.makeText(context, "联系骑手:" + order.receivePhone, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + order.receivePhone));
                BaseApplication.getAppContext().startActivity(intent);
            });
        } else {
            viewHolder.btnConcatDriver.setVisibility(View.GONE);
        }

        // 抢单按钮(骑手使用)
        viewHolder.btnGrabOnOrder.setVisibility(View.GONE);
        // 联系用户按钮(骑手使用)
        viewHolder.btnConcatUser.setVisibility(View.GONE);
        // 订单已完结按钮(骑手使用)
        viewHolder.btnFinishOrder.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

}
