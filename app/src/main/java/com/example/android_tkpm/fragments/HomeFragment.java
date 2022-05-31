package com.example.android_tkpm.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_tkpm.CartActivity;
import com.example.android_tkpm.MainActivity;
import com.example.android_tkpm.Notifications.PushNotificationService;
import com.example.android_tkpm.ProductActivity;
import com.example.android_tkpm.R;
import com.example.android_tkpm.SearchActivity;
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

    private final int REFRESH = 0, LOAD_MORE = 1;

    public static ProductService productService = ApiUtils.getProductService();
    private CategoryService categoryService = ApiUtils.getCategoryService();


    private SwipeRefreshLayout swipeRefreshLayout;
    // category
    private String currentCategory = "1";
    private RecyclerView recyclerViewCategory;
    private List<Category> categoryList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;

    // product
    private ListView lvContainer;
    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter;
    private mHandler mHandler;
    private View footerView;
    private int page = 1;
    private Boolean isLoading = false;

    private ImageButton searchButton, cartButton;
    public static TextView badgeCart;

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
        getProducts(getActivity(), currentCategory, LOAD_MORE);

        lvContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), ProductActivity.class);
                myIntent.putExtra("_id", productList.get(position).get_id()); //Optional parameters
                startActivity(myIntent);
            }
        });

        lvContainer.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(view.getLastVisiblePosition() == totalItemCount - 1 && isLoading == false) {
//                    page += 1;
//                    isLoading = true;
//                    getProducts(getActivity(), currentCategory, LOAD_MORE);
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), CartActivity.class);
                startActivity(myIntent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
//                page = 1;
//                mHandler.sendEmptyMessage(3);
                getCategories();
                getProducts(getActivity(), currentCategory, REFRESH);
            }
        });

        return view;
    }

    private void mapping(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_home);
        recyclerViewCategory = (RecyclerView) view.findViewById(R.id.lvCategory);
        lvContainer = (ListView) view.findViewById(R.id.lvContainer);
        mHandler = new mHandler();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.footer_view, null);

        searchButton = (ImageButton) view.findViewById(R.id.searchBtn);
        cartButton = (ImageButton) view.findViewById(R.id.cartBtn);
        badgeCart = (TextView) view.findViewById(R.id.badge_cart);

        productAdapter = new ProductAdapter(getActivity(), R.layout.item_product, productList);
        lvContainer.setAdapter(productAdapter);
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

        List<Category> newList = new ArrayList<>();
        newList.add(new Category("1", "All"));
        newList.addAll(categories);

        categoryAdapter.setClickListener(this);
        categoryAdapter.updateCategoryList(newList);
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

    private void getProducts(Context context, String category, int action) {
//        lvContainer.addFooterView(footerView);
//        mHandler.sendEmptyMessage(0);

        if(category.equals("1")) {
            productService.getAll().enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    lvContainer.removeFooterView(footerView);
                    isLoading = false;
                    if (response.isSuccessful()) {
//                        List<Product> products = response.body();
//                        if(products != null && products.size() > 0) {
//                            if(action == LOAD_MORE) {
////                                Message message = mHandler.obtainMessage(1, products);
////                                mHandler.sendMessage(message);
//                                productAdapter.addProductList(response.body());
//                            }
//                            else {
////                                Message message = mHandler.obtainMessage(2, products);
////                                mHandler.sendMessage(message);
//                                productAdapter.updateProductList(response.body());
//                            }
//                        }
//                        mHandler.sendEmptyMessage(3);
                        productAdapter.updateProductList(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    mHandler.sendEmptyMessage(3);
                    toastMsg("Error");
                }
            });
        }
        else {
            productService.getProductByCategory(category).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    lvContainer.removeFooterView(footerView);
                    isLoading = false;
                    if(response.isSuccessful()) {
//                        List<Product> products = response.body();
//                        if(products != null && products.size() > 0) {
//                            if(action == LOAD_MORE) {
////                                Message message = mHandler.obtainMessage(1, products);
////                                mHandler.sendMessage(message);
//                              productAdapter.addProductList(response.body());
//                            }
//                            else {
////                                Message message = mHandler.obtainMessage(2, products);
////                                mHandler.sendMessage(message);
//                              productAdapter.updateProductList(response.body());
//                            }
//                        }
//                        mHandler.sendEmptyMessage(3);
                        productAdapter.updateProductList(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    mHandler.sendEmptyMessage(3);
                    toastMsg("Error");
                }
            });
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        currentCategory = categoryAdapter.getItem(position).get_id();
        categoryAdapter.setSelectedIndex(position);
        productAdapter.updateProductList(new ArrayList<>());
//        page = 1;
//        isLoading = true;
        getProducts(getActivity(), categoryAdapter.getItem(position).get_id(), REFRESH);
    }

    private void toastMsg(String msg) {
        Toast toast =   Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onResume() {
        super.onResume();

//        if(MainActivity.active.getClass().getSimpleName().equals("HomeFragment")) {
//            badgeCart.setText((CartManager.getCart().size() + ""));
//            if(NotifyFragment.badgeCart != null) {
//                NotifyFragment.badgeCart.setText((CartManager.getCart().size() + ""));
//            }
//        }
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    lvContainer.addFooterView(footerView);
                    break;
                // Add data
                case 1:
                    lvContainer.removeFooterView(footerView);
                    productAdapter.addProductList((List<Product>) msg.obj);
                    productAdapter.notifyDataSetChanged();
                    lvContainer.invalidateViews();
                    isLoading = false;
                    break;
                // Refresh
                case 2:
                    lvContainer.removeFooterView(footerView);
                    productAdapter.updateProductList((List<Product>) msg.obj);
                    productAdapter.notifyDataSetChanged();
                    lvContainer.invalidateViews();
                    isLoading = false;
                    break;
                // Error
                case 3:
                    lvContainer.removeFooterView(footerView);
                    isLoading = false;
                    break;
            }
        }
    }
}