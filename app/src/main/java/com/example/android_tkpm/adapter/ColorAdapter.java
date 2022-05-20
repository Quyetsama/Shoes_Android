package com.example.android_tkpm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_tkpm.R;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {
    private List<String> colorList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    private int selectedIndex;

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout color;
        CardView cardView;
        MyViewHolder(View view) {
            super(view);
            color = view.findViewById(R.id.bg_color);
            cardView = view.findViewById(R.id.item_color_container);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClickColor(v, getAdapterPosition());
        }
    }


    public ColorAdapter(Context context, List<String> colorList) {
        this.mInflater = LayoutInflater.from(context);
        this.colorList = colorList;
        this.context = context;
    }

    public void setSelectedIndex(int ind)
    {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public String getSelectedItem()
    {
        return colorList.get(selectedIndex);
    }

    @NonNull
    @Override
    public ColorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_color, parent, false);

        return new ColorAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.MyViewHolder holder, int position) {
        String color = colorList.get(position);

        if(selectedIndex!= -1 && position == selectedIndex)
        {
            final float scale = context.getResources().getDisplayMetrics().density;
            int px = (int) (42 * scale + 0.5f);  // replace 100 with your dimensions
            holder.cardView.getLayoutParams().width = px;
            holder.cardView.getLayoutParams().height = px;
        }
        else
        {
            final float scale = context.getResources().getDisplayMetrics().density;
            int px = (int) (32 * scale + 0.5f);  // replace 100 with your dimensions
            holder.cardView.getLayoutParams().width = px;
            holder.cardView.getLayoutParams().height = px;
        }

        holder.color.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public String getItem(int position) {
        return colorList.get(position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClickColor(View view, int position);
    }
}
