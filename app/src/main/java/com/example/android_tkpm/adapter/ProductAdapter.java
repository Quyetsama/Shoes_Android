package com.example.android_tkpm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.android_tkpm.MainActivity;
import com.example.android_tkpm.R;
import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.ProductService;
import com.example.android_tkpm.fragments.HomeFragment;
import com.example.android_tkpm.models.Notify;
import com.example.android_tkpm.models.Product;
import com.example.android_tkpm.models.Response;
import com.example.android_tkpm.utils.TokenManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

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
        ImageView imgProduct, heartButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        if(position == getCount()-1){
//            Log.e("LOAD MORE: ", "LOAD MORE");
////
////            HomeFragment.addProduct();
////            notifyDataSetChanged();
//        }

        ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            convertView = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();

            viewHolder.nameProduct = convertView.findViewById(R.id.nameProduct);
            viewHolder.categoryProduct = convertView.findViewById(R.id.categoryProduct);
            viewHolder.priceProduct = convertView.findViewById(R.id.priceProduct);
            viewHolder.imgProduct = convertView.findViewById(R.id.imgProduct);
            viewHolder.heartButton = convertView.findViewById(R.id.heart_button_product);

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

        if(product.getFavorite() != null) {
            viewHolder.heartButton.setVisibility(View.VISIBLE);

            if(product.getFavorite()) {
                viewHolder.heartButton.setColorFilter(ContextCompat.getColor(context, R.color.heart));
            }
            else {
                viewHolder.heartButton.setColorFilter(ContextCompat.getColor(context, R.color.white));
            }

            viewHolder.heartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductService productService = ApiUtils.getProductService();
                    TokenManager tokenManager = new TokenManager(context);

                    productService.favoriteProduct(
                            tokenManager.getToken(),
                            product.get_id(),
                            false
                    ).enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if(response.isSuccessful()) {
                                productList.remove(position);
                                notifyDataSetChanged();
                            }
                            else if(response.code() == 401) {
                                tokenManager.removeToken();
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        return convertView;
    }

    public void updateProductList(List<Product> newlist) {
        productList.clear();
        productList.addAll(newlist);
        this.notifyDataSetChanged();
    }

    public void addProductList(List<Product> newlist) {
        productList.addAll(newlist);
        this.notifyDataSetChanged();
    }

}
