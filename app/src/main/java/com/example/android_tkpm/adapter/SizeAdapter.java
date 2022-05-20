package com.example.android_tkpm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_tkpm.R;
import com.example.android_tkpm.models.Category;
import com.example.android_tkpm.models.Size;

import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.MyViewHolder> {
    private List<String> sizeList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    private int selectedIndex = 0;

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView size;
        MyViewHolder(View view) {
            super(view);
            size = view.findViewById(R.id.value_size);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClickSize(v, getAdapterPosition());
        }
    }


    public SizeAdapter(Context context, List<String> sizeList) {
        this.mInflater = LayoutInflater.from(context);
        this.sizeList = sizeList;
    }

    public void setSelectedIndex(int ind)
    {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public String getSelectedItem()
    {
        return sizeList.get(selectedIndex);
    }

    @NonNull
    @Override
    public SizeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_size, parent, false);

        return new SizeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeAdapter.MyViewHolder holder, int position) {
        String size = sizeList.get(position);

        if(selectedIndex!= -1 && position == selectedIndex)
        {
            holder.size.setTextColor(Color.parseColor("#1C1C1C"));
            holder.size.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            holder.size.setTextColor(Color.parseColor("gray"));
            holder.size.setTypeface(null, Typeface.NORMAL);
        }

        holder.size.setText(size);
    }

    @Override
    public int getItemCount() {
        return sizeList.size();
    }

    public String getItem(int position) {
        return sizeList.get(position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClickSize(View view, int position);
    }
}
