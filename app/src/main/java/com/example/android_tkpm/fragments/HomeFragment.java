package com.example.android_tkpm.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android_tkpm.CartActivity;
import com.example.android_tkpm.MainActivity;
import com.example.android_tkpm.Notifications.PushNotificationService;
import com.example.android_tkpm.ProductActivity;
import com.example.android_tkpm.R;
import com.example.android_tkpm.SignInActivity;
import com.example.android_tkpm.adapter.CategoryAdapter;
import com.example.android_tkpm.adapter.ProductAdapter;
import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.CategoryService;
import com.example.android_tkpm.api.ProductService;
import com.example.android_tkpm.models.Category;
import com.example.android_tkpm.models.Product;
import com.example.android_tkpm.utils.CartManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements CategoryAdapter.ItemClickListener {

    public static ProductService productService = ApiUtils.getProductService();
    private CategoryService categoryService = ApiUtils.getCategoryService();


    // category
    private RecyclerView recyclerViewCategory;
    private List<Category> categoryList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;

    // product
    private ListView lvContainer;
    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter;

    private Button cartButton;

    public HomeFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mapping(view);
        createRecyclerView();
        getCategories();
        getProducts(getActivity());

        lvContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), ProductActivity.class);
                myIntent.putExtra("_id", productList.get(position).get_id()); //Optional parameters
                startActivity(myIntent);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), CartActivity.class);
                startActivity(myIntent);
            }
        });

        return view;
    }

    private void mapping(View view) {
        recyclerViewCategory = (RecyclerView) view.findViewById(R.id.lvCategory);
        lvContainer = (ListView) view.findViewById(R.id.lvContainer);

        cartButton = (Button) view.findViewById(R.id.cartBtn);
    }

    private void createRecyclerView() {
        categoryAdapter = new CategoryAdapter(getActivity(), categoryList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategory.setLayoutManager(mLayoutManager);
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCategory.setAdapter(categoryAdapter);
    }

    private void changeCategoryData(List<Category> categories) {

        categoryList.addAll(categories);

        categoryAdapter.setClickListener(this);
        categoryAdapter.notifyDataSetChanged();
    }

    private void getCategories() {
        categoryService.getAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> categories = response.body();
                    changeCategoryData(categories);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                toastMsg("Error");
            }
        });
    }

    private void getProducts(Context context) {
        productService.getAll().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productList = response.body();
                    productAdapter = new ProductAdapter(context, R.layout.item_product, productList);
                    lvContainer.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
//                toastMsg("Error");
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        categoryAdapter.setSelectedIndex(position);
//        Toast.makeText(getActivity(), categoryAdapter.getItem(position) , Toast.LENGTH_SHORT).show();
    }

    private void toastMsg(String msg) {
        Toast toast =   Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}