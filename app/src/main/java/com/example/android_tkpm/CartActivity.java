package com.example.android_tkpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_tkpm.adapter.CartAdapter;
import com.example.android_tkpm.adapter.ProductAdapter;
import com.example.android_tkpm.models.ItemCart;
import com.example.android_tkpm.models.Product;
import com.example.android_tkpm.utils.CartManager;
import com.example.android_tkpm.utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    // product
    private ListView lvContainer;
    private CartAdapter cartAdapter;

    private Button backButton;
    private static TextView totalCart;
    private static AppCompatButton checkOutButton;
    private static ImageView emptyCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mapping();

        changeDataCart();

        cartAdapter = new CartAdapter(CartActivity.this, R.layout.item_cart, CartManager.getCart());
        lvContainer.setAdapter(cartAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CartManager.getCart().size() == 0) {
                    toastMsg("Please add products to continue");
                }
                else if(new TokenManager(CartActivity.this).getToken().equals("")) {
                    Intent intent = new Intent(CartActivity.this, SignInActivity.class);
                    CartActivity.this.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(CartActivity.this, CheckOutActivity.class);
                    CartActivity.this.startActivity(intent);
//                    toastMsg("Success");
                }
            }
        });
    }

    private void mapping() {
        lvContainer = (ListView) findViewById(R.id.lvCart);
        backButton = (Button) findViewById(R.id.backBtn);
        totalCart = (TextView) findViewById(R.id.total_cart);
        checkOutButton = (AppCompatButton) findViewById(R.id.checkoutBtn);
        emptyCart = (ImageView) findViewById(R.id.image_empty);
    }

    public static void changeDataCart() {
        if(CartManager.getCart().size() == 0) {
            emptyCart.setVisibility(View.VISIBLE);
        }

        totalCart.setText(CartManager.getTotal() + " $");
    }

    private void toastMsg(String msg) {
        Toast toast =   Toast.makeText(CartActivity.this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}