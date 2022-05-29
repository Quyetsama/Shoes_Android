package com.example.android_tkpm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android_tkpm.adapter.CategoryAdapter;
import com.example.android_tkpm.adapter.ColorAdapter;
import com.example.android_tkpm.adapter.SizeAdapter;
import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.ProductService;
import com.example.android_tkpm.models.Category;
import com.example.android_tkpm.models.ItemCart;
import com.example.android_tkpm.models.Product;
import com.example.android_tkpm.models.Size;
import com.example.android_tkpm.utils.CartManager;
import com.example.android_tkpm.utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity implements ColorAdapter.ItemClickListener, SizeAdapter.ItemClickListener {

    private ProductService productService = ApiUtils.getProductService();
    private Product product;

    private String productID = "";
    private Boolean isHeart = false;

    private ImageButton backButton, cartButton, heartButton;
    private Button addButton;
    private ImageView imageProduct;
    private TextView nameProduct, priceProduct, desProduct, badgeCart;

    private RecyclerView recyclerViewSize;
    private List<String> sizeList = new ArrayList<>();
    private SizeAdapter sizeAdapter;

    private RecyclerView recyclerViewColor;
    private List<String> colorList = new ArrayList<>();
    private ColorAdapter colorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mapping();

        createRecyclerView();

        Intent intent = getIntent();
        productID = intent.getStringExtra("_id");

        getProduct();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ProductActivity.this, CartActivity.class);
                ProductActivity.this.startActivity(myIntent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product != null) {

                    CartManager.addToCart(new ItemCart(
                            product.get_id(),
                            product.getName(),
                            sizeAdapter.getSelectedItem(),
                            colorAdapter.getSelectedItem(),
                            product.getImage(),
                            product.getPrice(),
                            1
                    ));
                    badgeCart.setText((CartManager.getCart().size() + ""));
                    Toast.makeText(ProductActivity.this, "ADD TO CART", Toast.LENGTH_SHORT).show();
                }
            }
        });

        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHeart = !isHeart;
                favoriteProduct();
            }
        });
    }

    private void mapping() {
        backButton = (ImageButton) findViewById(R.id.backBtn);
        cartButton = (ImageButton) findViewById(R.id.cartBtn);
        addButton = (Button) findViewById(R.id.addToCartBtn);
        heartButton = (ImageButton) findViewById(R.id.heart_button);
        imageProduct = (ImageView) findViewById(R.id.img_Product);
        nameProduct = (TextView) findViewById(R.id.name_Product);
        priceProduct = (TextView) findViewById(R.id.price_Product);
        desProduct = (TextView) findViewById(R.id.des_Product);
        badgeCart = (TextView) findViewById(R.id.badge_cart);

        if(isHeart) {
            heartButton.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.heart));
        }
        else {
            heartButton.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }

        recyclerViewSize = (RecyclerView) findViewById(R.id.lvSize);
        recyclerViewColor = (RecyclerView) findViewById(R.id.lvColor);
    }

    private void createRecyclerView() {
        sizeAdapter = new SizeAdapter(ProductActivity.this, sizeList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ProductActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewSize.setLayoutManager(mLayoutManager);
        recyclerViewSize.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSize.setAdapter(sizeAdapter);

        colorAdapter = new ColorAdapter(ProductActivity.this, colorList);
        LinearLayoutManager mLayoutManagerColor = new LinearLayoutManager(ProductActivity.this);
        mLayoutManagerColor.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewColor.setLayoutManager(mLayoutManagerColor);
        recyclerViewColor.setItemAnimator(new DefaultItemAnimator());
        recyclerViewColor.setAdapter(colorAdapter);
    }

    private void changeSizeData(List<String> sizes) {

        sizeList.clear();
        sizeList.addAll(sizes);

        sizeAdapter.setClickListener(this);
        sizeAdapter.notifyDataSetChanged();
    }

    private void changeColorData(List<String> colors) {

        colorList.clear();
        colorList.addAll(colors);

        colorAdapter.setClickListener(this);
        colorAdapter.notifyDataSetChanged();
    }

    private void getProduct() {
        productService.getProductById(new TokenManager(getApplicationContext()).getToken(), productID).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

                if (response.isSuccessful()) {
                    product = response.body();

                    Glide.with(ProductActivity.this)
                            .load(product.getImage())
                            .into(imageProduct);
                    nameProduct.setText(product.getName());
                    priceProduct.setText(product.getPrice() + " $");
                    desProduct.setText(product.getDescription());

                    isHeart = product.getFavorite();

                    if(isHeart) {
                        heartButton.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.heart));
                    }
                    else {
                        heartButton.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    }

                    changeSizeData(product.getSize());
                    changeColorData(product.getColor());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                toastMsg("Error");
            }
        });
    }

    private void toastMsg(String msg) {
        Toast toast =   Toast.makeText(ProductActivity.this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onItemClickSize(View view, int position) {
        sizeAdapter.setSelectedIndex(position);
    }

    @Override
    public void onItemClickColor(View view, int position) {
        colorAdapter.setSelectedIndex(position);
    }

    private void favoriteProduct() {
        TokenManager tokenManager = new TokenManager(getApplicationContext());

        if(!tokenManager.getToken().equals("")) {
            productService.favoriteProduct(tokenManager.getToken(), productID, isHeart).enqueue(new Callback<com.example.android_tkpm.models.Response>() {
                @Override
                public void onResponse(Call<com.example.android_tkpm.models.Response> call, Response<com.example.android_tkpm.models.Response> response) {
                    if(response.isSuccessful()) {
                        if(isHeart) {
                            heartButton.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.heart));
                        }
                        else {
                            heartButton.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));
                        }
                    }
                    else if(response.code() == 401) {
                        tokenManager.removeToken();
                    }
                }

                @Override
                public void onFailure(Call<com.example.android_tkpm.models.Response> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            startActivityForResult(new Intent(getApplicationContext(), SignInActivity.class), 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        badgeCart.setText((CartManager.getCart().size() + ""));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getProduct();
    }
}