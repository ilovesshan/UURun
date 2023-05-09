package com.school.uurun.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.school.uurun.R;
import com.school.uurun.adapter.UserOrderListAdapter;
import com.school.uurun.entity.Order;
import com.school.uurun.service.OrderService;
import com.school.uurun.view.viewcallback.UserOrderListViewCallback;

import java.util.List;

/**
 * 用户订单列表(已下单/配送中/已完成)
 */
public class UserOrderListActivity extends AppCompatActivity implements UserOrderListViewCallback {
    private static final String TAG = "UserOrderListActivity";
    private RecyclerView rvUserOrderList;
    private OrderService orderService;
    private UserOrderListAdapter orderListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_list);
        // 从Intent中获取数据啊
        final Intent intent = getIntent();
        final String userId = intent.getStringExtra("userId");
        final String status = intent.getStringExtra("status");
        final String orderType = intent.getStringExtra("orderType");


        // 初始化控件以及绑定事件监听
        initViewAndBindEvent();

        // 初始化适配器
        orderListAdapter = new UserOrderListAdapter(this);
        // 给RecyclerView设置布局管理器
        rvUserOrderList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        // 给RecyclerView设置适配器
        rvUserOrderList.setAdapter(orderListAdapter);

        // 请求数据
        orderService = new OrderService(this);
        orderService.selectUserOrderByUserIdAndStatusAndOrderType(userId, status,orderType);
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
    protected void onDestroy() {
        super.onDestroy();

        if (orderService != null) {
            orderService.removeUserOrderListViewCallback();
        }
    }
}