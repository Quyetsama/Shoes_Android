package com.example.android_tkpm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.android_tkpm.DetailsOrderActivity;
import com.example.android_tkpm.R;
import com.example.android_tkpm.models.Notify;
import com.example.android_tkpm.models.OrderHistory;
import com.example.android_tkpm.utils.HandleTime;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<OrderHistory> orderHistoryList;

    public OrderAdapter(Context context, int layout, List<OrderHistory> orderHistoryList) {
        this.context = context;
        this.layout = layout;
        this.orderHistoryList = orderHistoryList;
    }

    @Override
    public int getCount() {
        return orderHistoryList.size();
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
        TextView code, quantity, total, createdAt;
        AppCompatButton detailsButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OrderAdapter.ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            convertView = inflater.inflate(layout, null);

            viewHolder = new OrderAdapter.ViewHolder();

            viewHolder.code = convertView.findViewById(R.id.code_order);
            viewHolder.quantity = convertView.findViewById(R.id.quantity_order);
            viewHolder.total = convertView.findViewById(R.id.total_order);
            viewHolder.createdAt = convertView.findViewById(R.id.time_order);
            viewHolder.detailsButton = convertView.findViewById(R.id.details_order);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (OrderAdapter.ViewHolder) convertView.getTag();
        }

        OrderHistory orderHistory = orderHistoryList.get(position);

        viewHolder.code.setText(("Order #" + orderHistory.getCode()));
        viewHolder.quantity.setText(("Quantity: " + orderHistory.getQuantity()));
        viewHolder.total.setText(("Total Price: " + orderHistory.getTotal()));
        viewHolder.createdAt.setText(HandleTime.convertMongoDate(orderHistory.getCreatedAt()));
        viewHolder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsOrderActivity.class);
                intent.putExtra("_id", orderHistory.get_id());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public void updateOrderList(List<OrderHistory> newlist) {
        orderHistoryList.clear();
        orderHistoryList.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
