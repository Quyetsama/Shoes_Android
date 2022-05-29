package com.example.android_tkpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_tkpm.adapter.ProductAdapter;
import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.ProductService;
import com.example.android_tkpm.models.Product;
import com.example.android_tkpm.utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends AppCompatActivity {

    ProductService productService = ApiUtils.getProductService();

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lvFavorite;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();

    private ImageButton backButton;
    private TextView titleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        mapping();
        getFavorites();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getFavorites();
            }
        });

        lvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getApplicationContext(), ProductActivity.class);
                myIntent.putExtra("_id", productList.get(position).get_id());
                startActivity(myIntent);
            }
        });
    }

    private void mapping() {
        backButton = (ImageButton) findViewById(R.id.backBtn);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout_favorites);
        lvFavorite = (ListView) findViewById(R.id.lvFavorite);

        productAdapter = new ProductAdapter(getApplicationContext(), R.layout.item_product, productList);
        lvFavorite.setAdapter(productAdapter);

        titleToolbar.setText("My Favorites");
    }

    private void getFavorites() {
        TokenManager tokenManager = new TokenManager(getApplicationContext());

        productService.getFavorites(tokenManager.getToken()).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()) {
                    productAdapter.updateProductList(response.body());
                }
                else if(response.code() == 401) {
                    tokenManager.removeToken();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}