package com.school.uurun.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.school.uurun.R;
import com.school.uurun.adapter.UserOrderListAdapter;
import com.school.uurun.base.BaseApplication;
import com.school.uurun.entity.Order;
import com.school.uurun.service.OrderService;
import com.school.uurun.view.viewcallback.UserOrderListViewCallback;

import java.util.List;

@SuppressLint("SetTextI18n")
public class UserOrderListFragment extends Fragment implements UserOrderListViewCallback {
    private RecyclerView rvUserOrderList;
    private UserOrderListAdapter orderListAdapter;

    // 订单类型
    private String orderType;

    // 订单状态
    private String status;
    private OrderService orderService;
    private View view;

    public UserOrderListFragment() {
    }

    public static UserOrderListFragment newInstance(String orderType, String status) {
        UserOrderListFragment fragment = new UserOrderListFragment();
        Bundle args = new Bundle();
        args.putString("orderType", orderType);
        args.putString("status", status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderType = getArguments().getString("orderType");
            status = getArguments().getString("status");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_order_list, container, false);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initViewAndBindEvent(view);

        // 获取用户ID
        SharedPreferences sharedPreferences = BaseApplication.getAppContext().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        final String userId = sharedPreferences.getString("id", "");

        // 初始化适配器
        orderListAdapter = new UserOrderListAdapter(BaseApplication.getAppContext());
        // 给RecyclerView设置布局管理器
        rvUserOrderList.setLayoutManager(new LinearLayoutManager(BaseApplication.getAppContext(), RecyclerView.VERTICAL, false));
        // 给RecyclerView设置适配器
        rvUserOrderList.setAdapter(orderListAdapter);

        // 请求数据
        orderService = new OrderService(this);
        // orderService.selectUserOrderByUserIdAndStatusAndOrderType(userId, status, orderType);
        orderService.selectUserOrderByUserIdAndStatus(userId, status);
    }

    private void initViewAndBindEvent(View view) {
        rvUserOrderList = view.findViewById(R.id.rv_user_order_list);
    }

    @Override
    public void onLoadSuccess(List<Order> orderList) {
        // 请求成功通过 适配器更新数据
        orderListAdapter.setOrderList(orderList);
    }

    @Override
    public void onLoadEmpty() {
        // 数据为空
        Toast.makeText(BaseApplication.getAppContext(), "数据为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadError(String errorMessage) {
        // 请求失败Toast提示原因
        Toast.makeText(BaseApplication.getAppContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (orderService != null) {
            orderService.removeUserOrderListViewCallback();
        }
    }
}