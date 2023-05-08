package com.school.uurun.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.school.uurun.R;
import com.school.uurun.entity.Order;
import com.school.uurun.service.OrderService;
import com.school.uurun.utils.DateTimeUtil;
import com.school.uurun.view.viewcallback.SubmitOrderViewCallback;

import java.util.UUID;

/**
 * 用户下单界面(快递/外卖)
 */
public class SubmitOrderActivity extends AppCompatActivity implements SubmitOrderViewCallback {
    private static final String TAG = "SubmitOrderActivity";

    private TextView tvOrderType;
    private EditText etOrderName;
    private EditText etDeliveryAddress;
    private EditText etReceiveUserAddress;
    private EditText etMoney;
    private EditText etNote;
    private Button btnSubmitOrder;
    private OrderService orderService;
    private String userId;
    private String nickname;
    private String phone;
    private String orderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);

        orderService = new OrderService(this);

        // 初始化控件以及绑定事件监听
        initViewAndBindEvent();
    }

    private void initViewAndBindEvent() {
        // 从Intent中获取订单类型
        final Intent intent = getIntent();
        orderType = intent.getStringExtra("orderType");
        final String orderTypeText = "1".equals(orderType) ? "快递帮取" : "外卖帮拿";

        // 从SharedPreferences中获取用户信息
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
        nickname = sharedPreferences.getString("nickname", "");
        phone = sharedPreferences.getString("phone", "");


        // 控件绑定
        tvOrderType = findViewById(R.id.tv_order_type);
        etOrderName = findViewById(R.id.et_order_name);
        etDeliveryAddress = findViewById(R.id.et_delivery_address);
        etReceiveUserAddress = findViewById(R.id.et_receive_user_address);
        etMoney = findViewById(R.id.et_money);
        etNote = findViewById(R.id.et_note);
        btnSubmitOrder = findViewById(R.id.btn_submit_order);

        // 区分当前的订单类型(1快递、2外卖)
        tvOrderType.setText(orderTypeText);


        // 订单提交按钮
        btnSubmitOrder.setOnClickListener(v -> {
            // 获取用户输入的数据
            final String orderName = etOrderName.getText().toString();
            final String deliveryAddress = etDeliveryAddress.getText().toString();
            final String receiveUserAddress = etReceiveUserAddress.getText().toString();
            final String money = etMoney.getText().toString();
            final String note = etNote.getText().toString();

            // 对数据进行校验
            if (TextUtils.isEmpty(orderName)) {
                Toast.makeText(this, "订单名称不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(deliveryAddress)) {
                Toast.makeText(this, "取单地址不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(receiveUserAddress)) {
                Toast.makeText(this, " 配送地址不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(money)) {
                Toast.makeText(this, "打赏金额不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if ("1".equals(orderType) && TextUtils.isEmpty(note)) {
                Toast.makeText(this, "快递帮取,请提前添加备注告知骑手快递编号,收件人等信息", Toast.LENGTH_SHORT).show();
                return;
            }


            // 弹出AlertDialog 告知用户检查下单数据信息是否填写正确
            final AlertDialog.Builder builder = new AlertDialog.Builder(SubmitOrderActivity.this);
            builder.setTitle(orderTypeText);
            builder.setMessage("亲亲,下单前请检查信息是否填写正确,以免推迟订单送达时间~");
            builder.setCancelable(false);
            builder.setPositiveButton("再看看", (dialog, which) -> {
            });
            builder.setNegativeButton("确认无误", (dialog, which) -> {
                // 封装订单数据
                final Order order = new Order(
                        UUID.randomUUID().toString().replaceAll("-", ""),
                        orderType, orderName, userId, nickname, phone, null, null, null,
                        deliveryAddress, receiveUserAddress, "1", money, note, DateTimeUtil.getCurrentDateTimeWithText(), null, null
                );
                // 调用Service处理下单逻辑
                orderService.submitOrder(order);
            });
            builder.show();
        });
    }

    // 创建上下文菜单
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_options, menu);
        return true;
    }

    // 监听菜单点击
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String status = "1";
        switch (item.getItemId()) {
            case R.id.submited_user_order:
                // 已下单
                status = "1";
                // Toast.makeText(this, "用户订单(已下单)", Toast.LENGTH_SHORT).show();
                break;
            case R.id.in_progress_user_order:
                //配送中
                status = "2";
                // Toast.makeText(this, "用户订单(配送中)", Toast.LENGTH_SHORT).show();
                break;
            case R.id.history_user_order:
                // 已完成
                status = "3";
                // Toast.makeText(this, "用户订单(已完成)", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        final Intent intent = new Intent(SubmitOrderActivity.this, UserOrderListActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("status", status);
        intent.putExtra("orderType", orderType);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSubmitOrderSuccess(Order order) {
        // 下单成功
        Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
        // 回到主界面
        finish();
    }

    @Override
    public void onSubmitOrderError(String errorMessage) {
        // 下单失败Toast提示原因
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orderService != null) {
            orderService.removeSubmitOrderViewCallback();
        }
    }
}