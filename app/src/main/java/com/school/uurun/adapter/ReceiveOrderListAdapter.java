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
 * 骑手相关的订单 适配器
 */
@SuppressLint("SetTextI18n")
public class ReceiveOrderListAdapter extends RecyclerView.Adapter<OrderInnerHolder> {
    private final Context context;
    private List<Order> orderList = new ArrayList<>();

    private OnItemBtnClickListener onItemBtnClickListener;

    public ReceiveOrderListAdapter(Context context) {
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
    public void onBindViewHolder(@NonNull OrderInnerHolder viewOrderInnerHolder, int position) {
        // 给每一条订单信息源绑数据
        final Order order = orderList.get(position);
        // 订单物品名称
        viewOrderInnerHolder.tvOrderGoodsName.setText(order.orderGoodsName);
        // 订单类型以及状态
        viewOrderInnerHolder.tvOrderStatusAndType.setText(DictUtil.getTextByOrderType(order.orderType) + " | " + DictUtil.getTextByOrderStatus(order.status));
        // 下单用户信息
        viewOrderInnerHolder.tvSubmitOrReceiveUserKey.setText("下单用户:");
        viewOrderInnerHolder.tvUserName.setText(order.submitNickname);
        viewOrderInnerHolder.tvUserPhone.setText(order.submitPhone);
        // 打赏金额
        viewOrderInnerHolder.tvMoney.setText("￥" + (order.money.contains(".") ? order.money : order.money + ".00"));
        // 取件地址
        viewOrderInnerHolder.tvDeliveryAddress.setText(order.deliveryAddress);
        // 配送地址
        viewOrderInnerHolder.tvReceiveUserAddress.setText(order.receiveUserAddress);
        // 下单时间
        viewOrderInnerHolder.tvCreateTime.setText(order.createTime);

        // 接单时间(此时订单状态是配送中或者已完成才显示)
        if ("2".equals(order.status) || "3".equals(order.status)) {
            viewOrderInnerHolder.tvReceiveTime.setVisibility(View.VISIBLE);
            viewOrderInnerHolder.tvReceiveTimeKey.setVisibility(View.VISIBLE);
            viewOrderInnerHolder.tvReceiveTime.setText(order.receiveTime);
        } else {
            viewOrderInnerHolder.tvReceiveTime.setVisibility(View.GONE);
            viewOrderInnerHolder.tvReceiveTimeKey.setVisibility(View.GONE);
        }

        // 订单完成时间(此时订单状态是已完成才显示)
        if ("3".equals(order.status)) {
            viewOrderInnerHolder.tvFinishedTime.setVisibility(View.VISIBLE);
            viewOrderInnerHolder.tvFinishedTimeKey.setVisibility(View.VISIBLE);
            viewOrderInnerHolder.tvFinishedTime.setText(order.finishedTime);
        } else {
            viewOrderInnerHolder.tvFinishedTime.setVisibility(View.GONE);
            viewOrderInnerHolder.tvFinishedTimeKey.setVisibility(View.GONE);
        }

        // 订单备注
        viewOrderInnerHolder.tvNote.setText(order.note);

        // 联系骑手按钮(用户使用)
        viewOrderInnerHolder.btnConcatDriver.setVisibility(View.GONE);

        // 抢单按钮(骑手使用)未接单才显示
        if ("1".equals(order.status)) {
            viewOrderInnerHolder.btnGrabOnOrder.setVisibility(View.VISIBLE);
            viewOrderInnerHolder.btnGrabOnOrder.setOnClickListener(v -> {
                if (onItemBtnClickListener != null) {
                    onItemBtnClickListener.onItemClick(order);
                }
            });
        } else {
            viewOrderInnerHolder.btnGrabOnOrder.setVisibility(View.GONE);
        }

        // 联系用户按钮(骑手使用) 接单之后才显示
        if ("2".equals(order.status) || "3".equals(order.status)) {
            viewOrderInnerHolder.btnConcatUser.setVisibility(View.VISIBLE);
            viewOrderInnerHolder.btnConcatUser.setOnClickListener(v -> {
                Toast.makeText(context, "联系用户:" + order.submitPhone, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + order.receivePhone));
                BaseApplication.getAppContext().startActivity(intent);
            });
        } else {
            viewOrderInnerHolder.btnConcatUser.setVisibility(View.GONE);
        }

        // 订单已完结按钮(骑手使用)接单之后才显示
        if ("2".equals(order.status)) {
            viewOrderInnerHolder.btnFinishOrder.setVisibility(View.VISIBLE);
            viewOrderInnerHolder.btnFinishOrder.setOnClickListener(v -> {
                if (onItemBtnClickListener != null) {
                    onItemBtnClickListener.onItemClick(order);
                }
            });
        } else {
            viewOrderInnerHolder.btnFinishOrder.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public interface OnItemBtnClickListener {
        // 抢单按钮回调
        void onItemClick(Order order);
    }

    public void setOnItemBtnClick(OnItemBtnClickListener onItemBtnClickListener) {
        this.onItemBtnClickListener = onItemBtnClickListener;
    }

    public void removeOnItemBtnClick() {
        this.onItemBtnClickListener = null;
    }
}
