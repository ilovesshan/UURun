package com.school.uurun.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.school.uurun.R;
import com.school.uurun.adapter.ReceiveOrderListAdapter;
import com.school.uurun.entity.Order;
import com.school.uurun.service.OrderService;
import com.school.uurun.utils.DateTimeUtil;
import com.school.uurun.view.viewcallback.ReceiveOrderViewCallback;

import java.util.List;

/**
 * 骑手抢单界面
 */
public class ReceiveOrderActivity extends AppCompatActivity implements ReceiveOrderViewCallback {
    private static final String TAG = "ReceiveOrderActivity";
    private RecyclerView rvReceiveOrderList;
    private OrderService orderService;
    private ReceiveOrderListAdapter receiveOrderListAdapter;
    private String orderType;
    private String userId;
    private String nickname;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_order);

        // 初始化控件以及绑定事件监听
        initViewAndBindEvent();
    }

    private void initViewAndBindEvent() {
        rvReceiveOrderList = findViewById(R.id.rv_receive_order_list);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 从Intent中获取订单类型
        final Intent intent = getIntent();
        orderType = intent.getStringExtra("orderType");

        // 获取用户ID
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
        nickname = sharedPreferences.getString("nickname", "");
        phone = sharedPreferences.getString("phone", "");


        // 初始化适配器
        receiveOrderListAdapter = new ReceiveOrderListAdapter(this);
        // 抢单按钮被点击
        receiveOrderListAdapter.setOnItemBtnClick(order -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ReceiveOrderActivity.this);
            builder.setTitle("发起抢单");
            builder.setMessage("亲亲,阅读单据信息后,确定要抢该单吗?");
            builder.setCancelable(false);
            builder.setPositiveButton("再考虑考虑", (dialog, which) -> {
            });
            builder.setNegativeButton("马上抢单", (dialog, which) -> {
                // 更新状态
                order.status = "2";
                order.receiveTime = DateTimeUtil.getCurrentDateTimeWithText();
                order.receiveUserId = userId;
                order.receiveNickname = nickname;
                order.receivePhone = phone;
                orderService.updateOrderInfo(order, false);
            });
            builder.show();
        });

        // 给RecyclerView设置布局管理器
        rvReceiveOrderList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        // 给RecyclerView设置适配器
        rvReceiveOrderList.setAdapter(receiveOrderListAdapter);

        // 请求数据
        orderService = new OrderService(this);
        orderService.selectOrderByStatusAndOrderType("1", orderType);
    }

    // 创建上下文菜单
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_driver_options, menu);
        return true;
    }

    // 监听菜单点击
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String status = "";
        switch (item.getItemId()) {
            case R.id.in_progress_driver_order:
                //配送中
                status = "2";
                break;
            case R.id.history_driver_order:
                // 已完成
                status = "3";
                break;
            default:
        }
        final Intent intent = new Intent(ReceiveOrderActivity.this, ReceiveOrderListActivity.class);
        intent.putExtra("status", status);
        intent.putExtra("orderType", orderType);
        intent.putExtra("userId", userId);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLoadSuccess(List<Order> orderList) {
        // 请求成功通过 适配器更新数据
        Log.d(TAG, "onLoadSuccess: " + orderList);
        receiveOrderListAdapter.setOrderList(orderList);
    }

    @Override
    public void onLoadEmpty() {
        // 数据为空
        Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
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
        // 抢单成功 跳去配送中页面
        final Intent intent = new Intent(ReceiveOrderActivity.this, ReceiveOrderListActivity.class);
        intent.putExtra("status", "2");
        intent.putExtra("orderType", orderType);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (orderService != null) {
            orderService.removeReceiveOrderViewCallback();
        }
    }
}