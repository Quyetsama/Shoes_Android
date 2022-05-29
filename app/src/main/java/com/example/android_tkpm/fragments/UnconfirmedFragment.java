package com.example.android_tkpm.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_tkpm.R;
import com.example.android_tkpm.adapter.OrderAdapter;
import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.OrderService;
import com.example.android_tkpm.models.OrderHistory;
import com.example.android_tkpm.utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnconfirmedFragment extends Fragment {

    private OrderService orderService = ApiUtils.getOrderService();

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lvUnconfirmed;
    private OrderAdapter orderAdapter;
    private List<OrderHistory> orderHistoryList = new ArrayList<>();

    public UnconfirmedFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Log.e("CREATE:::", "1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_unconfirmed, container, false);

        Log.e("CREATE:::", "1");
        mapping(view);
        getOrder();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrder();
            }
        });

        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            Log.e("UNCONFIRMED:::", "VISIBLE");
        }
    }

    private void mapping(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_order0);
        lvUnconfirmed = (ListView) view.findViewById(R.id.lvUnconfirmed);

        orderAdapter = new OrderAdapter(getContext(), R.layout.item_order, orderHistoryList);
        lvUnconfirmed.setAdapter(orderAdapter);
    }

    private void getOrder() {
        orderService.getOrderByStatus(new TokenManager(getContext()).getToken(), 0).enqueue(new Callback<List<OrderHistory>>() {
            @Override
            public void onResponse(Call<List<OrderHistory>> call, Response<List<OrderHistory>> response) {
                swipeRefreshLayout.setRefreshing(false);

                if(response.isSuccessful()) {
                    orderAdapter.updateOrderList(response.body());
                }
                else if(response.code() == 401) {
                    Toast.makeText(getContext(), "401", Toast.LENGTH_SHORT).show();
                    new TokenManager(getContext()).removeToken();
                }
            }

            @Override
            public void onFailure(Call<List<OrderHistory>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);

                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}