
package com.school.uurun.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.school.uurun.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单列表
 */
public class OrderFragment extends Fragment {

    private Context context;

    public OrderFragment(Context context) {
        this.context = context;
    }

    // 订单页面的Tab的Title
    private final String[] orderCenterTitles = {"已下单", "配送中", "已完结"};

    // 订单页面的Tab的对应的内容
    final List<Fragment> fragments = new ArrayList<Fragment>() {{
        add(UserOrderListFragment.newInstance("1", "1"));
        add(UserOrderListFragment.newInstance("1", "2"));
        add(UserOrderListFragment.newInstance("1", "3"));
    }};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order, container, false);
        // 初始化控件以及绑定事件监听
        initViewAndBindEvent(view);
        return view;
    }

    private void initViewAndBindEvent(View view) {
        // 初始化控件
        TabLayout tlTabBar = view.findViewById(R.id.tl_tab_bar);
        ViewPager2 vp2OrderContainer = view.findViewById(R.id.vp2_order_container);

        // 设置ViewPager2滚动方向
        vp2OrderContainer.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        // 设置ViewPager2最大翻页数
        vp2OrderContainer.setOffscreenPageLimit(fragments.size());
        // 设置ViewPager2适配器
        vp2OrderContainer.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });
        // 绑定ViewPager2和TabLayout进行联动
        new TabLayoutMediator(tlTabBar, vp2OrderContainer, true, (tab, position) -> tab.setText(orderCenterTitles[position])).attach();
    }
}