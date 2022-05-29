package com.example.android_tkpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_tkpm.adapter.ProductAdapter;
import com.example.android_tkpm.adapter.ProductAdapterSearch;
import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.ProductService;
import com.example.android_tkpm.models.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    ProductService productService = ApiUtils.getProductService();

    private RelativeLayout searchContainer;
    private EditText searchEdt;
    private TextView cancelTv, title_search;

    private GridView gridView;
    private ProductAdapterSearch productAdapterSearch;
    private List<Product> productList = new ArrayList<>();

    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mapping();
        suggestProduct();

        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });

        searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(!searchEdt.getText().toString().trim().equals("")) {
                    searchProduct();
                }
                return false;
            }
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getApplicationContext(), ProductActivity.class);
                myIntent.putExtra("_id", productList.get(position).get_id());
                startActivity(myIntent);
            }
        });

    }

    private void mapping() {
        searchContainer = (RelativeLayout) findViewById(R.id.search_container);
        searchEdt = (EditText) findViewById(R.id.search_edt);
        cancelTv = (TextView) findViewById(R.id.cancel_tv);
        title_search = (TextView) findViewById(R.id.title_search);
        gridView = (GridView) findViewById(R.id.grid_container);
        loading = (ProgressBar) findViewById(R.id.loading_search);

        searchEdt.requestFocus();
        title_search.setText("Suggested");
        productAdapterSearch = new ProductAdapterSearch(this, R.layout.item_product_2, productList);
        gridView.setAdapter(productAdapterSearch);
    }

    private void searchProduct() {
        setLoading(true);

        productService.searchProduct(searchEdt.getText().toString().trim()).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    setLoading(false);

                    title_search.setText("Search result (" + response.body().size() + ")");
                    productAdapterSearch.updateProductList(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        });
    }

    private void suggestProduct() {
        productService.suggestProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productAdapterSearch.updateProductList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLoading(Boolean value) {
        if(value) {
            loading.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else {
            loading.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

}