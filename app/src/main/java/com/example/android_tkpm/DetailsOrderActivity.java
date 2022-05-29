package com.example.android_tkpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_tkpm.adapter.OrdersInfoAdapter;
import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.OrderService;
import com.example.android_tkpm.models.DetailsOrder;
import com.example.android_tkpm.models.OrderInfo;
import com.example.android_tkpm.utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsOrderActivity extends AppCompatActivity {

    private OrderService orderService = ApiUtils.getOrderService();
    private String _id = "";

    private ImageButton backButton;
    private TextView titleToolbar;

    private ScrollView scrollViewContainer;
    private TextView name, phone, address, subTotal, shipping, total;
    private LinearLayout deliveryTimeContainer;

    private ListView lvOrderInfo;
    private OrdersInfoAdapter ordersInfoAdapter;
    private List<OrderInfo> orderInfoList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order);

        Intent intent = getIntent();
        _id = intent.getStringExtra("_id");

        mapping();

        getData();
    }

    private void mapping() {
        backButton = (ImageButton) findViewById(R.id.backBtn);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);
        scrollViewContainer = (ScrollView) findViewById(R.id.scrollView_container);
        name = (TextView) findViewById(R.id.name_details_order);
        phone = (TextView) findViewById(R.id.phone_details_order);
        address = (TextView) findViewById(R.id.address_details_order);
        subTotal = (TextView) findViewById(R.id.subTotal_details_order);
        shipping = (TextView) findViewById(R.id.shipping_details_order);
        total = (TextView) findViewById(R.id.total_details_order);
        deliveryTimeContainer = (LinearLayout) findViewById(R.id.delivery_time_container);

        lvOrderInfo = (ListView) findViewById(R.id.lvOrderInfo);
        ordersInfoAdapter = new OrdersInfoAdapter(getApplicationContext(), R.layout.item_cart_2, orderInfoList);
        lvOrderInfo.setAdapter(ordersInfoAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleToolbar.setText(("Details Order"));
    }

    private void getData() {
        orderService.getDetailsOrder(new TokenManager(getApplicationContext()).getToken(), _id).enqueue(new Callback<DetailsOrder>() {
            @Override
            public void onResponse(Call<DetailsOrder> call, Response<DetailsOrder> response) {
                if (response.isSuccessful()) {
                    DetailsOrder detailsOrder = response.body();

                    if(detailsOrder != null) {
                        name.setText(detailsOrder.getName());
                        phone.setText(detailsOrder.getPhone());
                        address.setText(detailsOrder.getAddress());
                        subTotal.setText(("$" + detailsOrder.getSubTotal()));
                        shipping.setText(("$" + detailsOrder.getShipping()));
                        total.setText(("$" +detailsOrder.getTotal()));

                        if(detailsOrder.getDeliveryTime() != null) {
                            deliveryTimeContainer.setVisibility(View.VISIBLE);
                        }
                        else {
                            deliveryTimeContainer.setVisibility(View.GONE);
                        }

                        ordersInfoAdapter.updateOrderInfoList(detailsOrder.getOrdersInfo());
                    }

//                    Toast.makeText(getApplicationContext(), detailsOrder.getOrdersInfo().get(0).getProduct().getName(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    Toast.makeText(getApplicationContext(), "401", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailsOrder> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}