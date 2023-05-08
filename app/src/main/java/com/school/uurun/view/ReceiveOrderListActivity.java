package com.school.uurun.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.school.uurun.R;
import com.school.uurun.adapter.ReceiveOrderListAdapter;
import com.school.uurun.entity.Order;
import com.school.uurun.service.OrderService;
import com.school.uurun.utils.DateTimeUtil;
import com.school.uurun.view.viewcallback.ReceiveOrderListViewCallback;

import java.util.List;

/**
 * 骑手相关的订单列表界面(配送中/已完成)
 */
public class ReceiveOrderListActivity extends AppCompatActivity implements ReceiveOrderListViewCallback {
    private static final String TAG = "UserOrderListActivity";

    private RecyclerView rvUserOrderList;
    private OrderService orderService;
    private ReceiveOrderListAdapter orderListAdapter;
    private String userId;
    private String status;
    private String orderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_order_list);
        // 初始化控件以及绑定事件监听
        initViewAndBindEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 从Intent中获取数据啊
        final Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        status = intent.getStringExtra("status");
        orderType = intent.getStringExtra("orderType");

        // 初始化适配器
        orderListAdapter = new ReceiveOrderListAdapter(this);
        // 订单结算按钮被点击
        orderListAdapter.setOnItemBtnClick(order -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ReceiveOrderListActivity.this);
            builder.setTitle("订单结算");
            builder.setMessage("风里雨里,终于走完这一单,辛苦啦 亲亲~");
            builder.setCancelable(false);
            builder.setNegativeButton("ok", (dialog, which) -> {
                // 更新状态
                order.status = "3";
                order.finishedTime = DateTimeUtil.getCurrentDateTimeWithText();
                // 调用service处理业务逻辑
                orderService.updateOrderInfo(order, true);
            });
            builder.show();
        });
        // 给RecyclerView设置布局管理器
        rvUserOrderList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        // 给RecyclerView设置适配器
        rvUserOrderList.setAdapter(orderListAdapter);

        // 请求数据
        orderService = new OrderService(this);
        orderService.selectDriverOrderByUserIdAndStatusAndOrderType(userId, status, orderType);
    }


    private void initViewAndBindEvent() {
        rvUserOrderList = findViewById(R.id.rv_user_order_list);
    }

    @Override
    public void onLoadSuccess(List<Order> orderList) {
        // 请求成功通过 适配器更新数据
        Log.d(TAG, "onLoadSuccess: " + orderList);
        orderListAdapter.setOrderList(orderList);
    }

    @Override
    public void onLoadEmpty() {
        // 数据为空
        Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadError(String errorMessage) {
        // 请求失败Toast提示原因
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onUpdateError(String errorMessage) {
        // 请求失败Toast提示原因
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateSuccess() {
        // 订单结算成功 跳去抢单页面
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (orderService != null) {
            orderService.removeReceiveOrderListViewCallback();
        }
    }
}