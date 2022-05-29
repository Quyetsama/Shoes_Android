package com.example.android_tkpm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.android_tkpm.R;
import com.example.android_tkpm.models.OrderInfo;
import com.example.android_tkpm.models.Product;

import java.util.List;

public class OrdersInfoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<OrderInfo> orderInfoList;

    public OrdersInfoAdapter(Context context, int layout, List<OrderInfo> orderInfoList) {
        this.context = context;
        this.layout = layout;
        this.orderInfoList = orderInfoList;
    }

    @Override
    public int getCount() {
        return orderInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView name, price, size, quantity;
        CardView color;
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OrdersInfoAdapter.ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            convertView = inflater.inflate(layout, null);

            viewHolder = new OrdersInfoAdapter.ViewHolder();

            viewHolder.name = convertView.findViewById(R.id.name_Product);
            viewHolder.price = convertView.findViewById(R.id.price_Product);
            viewHolder.size = convertView.findViewById(R.id.value_size);
            viewHolder.quantity = convertView.findViewById(R.id.quantity_Product);
            viewHolder.color = convertView.findViewById(R.id.value_color);
            viewHolder.image = convertView.findViewById(R.id.image_Product);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (OrdersInfoAdapter.ViewHolder) convertView.getTag();
        }

        OrderInfo orderInfo = orderInfoList.get(position);

        viewHolder.name.setText(orderInfo.getProduct().getName());
        viewHolder.price.setText((orderInfo.getPrice() + " $"));
        viewHolder.size.setText((orderInfo.getSize() + ""));
        viewHolder.color.setBackgroundColor(Color.parseColor(orderInfo.getColor()));
        viewHolder.quantity.setText(("x" + orderInfo.getQuantity()));

        Glide.with(context)
                .load(orderInfo.getProduct().getImage())
                .into(viewHolder.image);

        return convertView;
    }

    public void updateOrderInfoList(List<OrderInfo> newlist) {
        orderInfoList.clear();
        orderInfoList.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
