package com.example.android_tkpm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.android_tkpm.CartActivity;
import com.example.android_tkpm.R;
import com.example.android_tkpm.models.ItemCart;
import com.example.android_tkpm.models.Notify;
import com.example.android_tkpm.models.Product;

import java.util.List;

public class NotifyAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Notify> notifyList;

    public NotifyAdapter(Context context, int layout, List<Notify> notifyList) {
        this.context = context;
        this.layout = layout;
        this.notifyList = notifyList;
    }

    @Override
    public int getCount() {
        return notifyList.size();
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
        TextView title, body;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NotifyAdapter.ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            convertView = inflater.inflate(layout, null);

            viewHolder = new NotifyAdapter.ViewHolder();

            viewHolder.title = convertView.findViewById(R.id.title_notify);
            viewHolder.body = convertView.findViewById(R.id.body_notify);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (NotifyAdapter.ViewHolder) convertView.getTag();
        }

        Notify notify = notifyList.get(position);

        viewHolder.title.setText(notify.getTitle());
        viewHolder.body.setText(notify.getBody());

        return convertView;
    }

    public void updateNotifyList(List<Notify> newlist) {
        notifyList.clear();
        notifyList.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
