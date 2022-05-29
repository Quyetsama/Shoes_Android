package com.example.android_tkpm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_tkpm.R;
import com.example.android_tkpm.models.Category;
import com.example.android_tkpm.models.Notify;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Category> categoryList;
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;

    private int selectedIndex;
    private int selectedColor = Color.parseColor("#FF0000");

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        RelativeLayout indicator;
        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.nameCategory);
            indicator = view.findViewById(R.id.indicator);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }


    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.mInflater = LayoutInflater.from(context);
        this.categoryList = categoryList;
    }

    public void setSelectedIndex(int ind)
    {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_category, parent, false);
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = categoryList.get(position);

        if(selectedIndex!= -1 && position == selectedIndex)
        {
//            holder.indicator.setBackgroundColor(Color.BLACK);
            holder.name.setTextSize(24);
            holder.indicator.setVisibility(View.VISIBLE);
        }
        else
        {
//            holder.indicator.setBackgroundColor(Color.WHITE);
            holder.name.setTextSize(18);
            holder.indicator.setVisibility(View.GONE);
        }

        holder.name.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public Category getItem(int position) {
        return categoryList.get(position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void updateCategoryList(List<Category> newlist) {
        categoryList.clear();
        categoryList.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
