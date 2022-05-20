package com.example.android_tkpm.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.android_tkpm.CartActivity;
import com.example.android_tkpm.R;
import com.example.android_tkpm.models.ItemCart;
import com.example.android_tkpm.models.Product;
import com.example.android_tkpm.utils.CartManager;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ItemCart> carts;

    public CartAdapter(Context context, int layout, List<ItemCart> carts) {
        this.context = context;
        this.layout = layout;
        this.carts = carts;
    }

    @Override
    public int getCount() {
        return carts.size();
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
        TextView nameProduct, priceProduct, sizeProduct, quantity;
        CardView colorProduct;
        ImageView imgProduct;
        ImageButton plusQt, minusQt, deleteButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CartAdapter.ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            convertView = inflater.inflate(layout, null);

            viewHolder = new CartAdapter.ViewHolder();

            viewHolder.nameProduct = convertView.findViewById(R.id.name_Product);
            viewHolder.priceProduct = convertView.findViewById(R.id.price_Product);
            viewHolder.sizeProduct = convertView.findViewById(R.id.value_size);
            viewHolder.colorProduct = convertView.findViewById(R.id.value_color);
            viewHolder.quantity = convertView.findViewById(R.id.quantity_tv);
            viewHolder.imgProduct = convertView.findViewById(R.id.imgProduct);
            viewHolder.plusQt = convertView.findViewById(R.id.plus_button);
            viewHolder.minusQt = convertView.findViewById(R.id.minus_button);
            viewHolder.deleteButton = convertView.findViewById(R.id.delete_button);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (CartAdapter.ViewHolder) convertView.getTag();
        }

        ItemCart cart = carts.get(position);

        viewHolder.nameProduct.setText(cart.getName());
        viewHolder.priceProduct.setText(cart.getPrice() + " $");
        viewHolder.sizeProduct.setText(cart.getSize());
        viewHolder.colorProduct.setBackgroundColor(Color.parseColor(cart.getColor()));
        viewHolder.quantity.setText(cart.getQuantity() + "");
        Glide.with(context)
                .load(cart.getImage())
                .into(viewHolder.imgProduct);

        viewHolder.plusQt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.increaseQuantity();
                notifyDataSetChanged();
                CartActivity.changeDataCart();
            }
        });

        viewHolder.minusQt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.getQuantity() == 1) {
                    showDialogDelete(position);
                }
                else {
                    cart.decreaseQuantity();
                    notifyDataSetChanged();
                    CartActivity.changeDataCart();
                }
            }
        });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete(position);
            }
        });

        return convertView;
    }

    private void showDialogDelete(int index) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);

        final TextView delete = dialog.findViewById(R.id.delete_product);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CartManager.removeFromCart(index);
                notifyDataSetChanged();
                CartActivity.changeDataCart();
            }
        });

        dialog.show();
    }
}
