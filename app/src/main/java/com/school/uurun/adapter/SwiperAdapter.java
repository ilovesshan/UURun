package com.school.uurun.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.school.uurun.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图 适配器
 */
public class SwiperAdapter extends RecyclerView.Adapter<SwiperAdapter.Holder> {
    private final Context context;

    public SwiperAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 通过Java代码方式创建一个ImageView
        final ImageView imageView = new ImageView(context);
        // 设置ImageView宽高填充父容器
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        return new Holder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        // 给当前展示的View设置背景资源
        holder.itemView.setBackgroundResource(R.drawable.run);
    }

    @Override
    public int getItemCount() {
        // 返回轮播图数量
        return 1000;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
