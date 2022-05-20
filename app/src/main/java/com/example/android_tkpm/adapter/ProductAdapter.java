package com.example.android_tkpm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android_tkpm.MainActivity;
import com.example.android_tkpm.R;
import com.example.android_tkpm.fragments.HomeFragment;
import com.example.android_tkpm.models.Product;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Product> productList;

    public ProductAdapter(Context context, int layout, List<Product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
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
        TextView nameProduct, categoryProduct, priceProduct;
        ImageView imgProduct;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(position == getCount()-1){
            Log.e("LOAD MORE: ", "LOAD MORE");
//
//            HomeFragment.addProduct();
//            notifyDataSetChanged();
        }

        ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            convertView = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();

            viewHolder.nameProduct = convertView.findViewById(R.id.nameProduct);
            viewHolder.categoryProduct = convertView.findViewById(R.id.categoryProduct);
            viewHolder.priceProduct = convertView.findViewById(R.id.priceProduct);
            viewHolder.imgProduct = convertView.findViewById(R.id.imgProduct);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = productList.get(position);

        viewHolder.nameProduct.setText(product.getName());
        viewHolder.categoryProduct.setText(product.getCategory().getName());
        viewHolder.priceProduct.setText(product.getPrice() + " $");

        Glide.with(context)
                .load(product.getImage())
                .into(viewHolder.imgProduct);

        return convertView;
    }

}
