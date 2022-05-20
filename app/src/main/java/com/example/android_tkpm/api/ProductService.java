package com.example.android_tkpm.api;

import com.example.android_tkpm.models.Product;
import com.example.android_tkpm.models.SignIn;
import com.example.android_tkpm.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductService {
    @GET("/api/product")
    Call<List<Product>> getAll();

    @GET("/api/product/{id}")
    Call<Product> getProductById(@Path("id") String _id);
}
