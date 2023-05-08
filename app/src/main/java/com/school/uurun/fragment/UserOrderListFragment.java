package com.school.uurun.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.school.uurun.R;

@SuppressLint("SetTextI18n")
public class UserOrderListFragment extends Fragment {
    private String title;

    public UserOrderListFragment() {
    }

    public static UserOrderListFragment newInstance(String title) {
        UserOrderListFragment fragment = new UserOrderListFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user_order_list, container, false);
        initViewAndBindEvent(view);
        return view;
    }

    private void initViewAndBindEvent(View view) {
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("title = " + title);
    }
}