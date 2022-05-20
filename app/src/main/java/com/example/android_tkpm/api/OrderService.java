package com.example.android_tkpm.api;

import com.example.android_tkpm.models.ItemCart;
import com.example.android_tkpm.models.ItemOrder;
import com.example.android_tkpm.models.OrderRequest;
import com.example.android_tkpm.models.Product;
import com.example.android_tkpm.models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {
    @POST("/api/order")
    Call<Response> sendOrder(@Header("Authorization") String token, @Body OrderRequest orderRequest);
}
