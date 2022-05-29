package com.example.android_tkpm.api;

import com.example.android_tkpm.models.Product;
import com.example.android_tkpm.models.Response;
import com.example.android_tkpm.models.SignIn;
import com.example.android_tkpm.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    @GET("/api/product")
    Call<List<Product>> getAll();

    @GET("/api/product/{id}")
    Call<Product> getProductById(@Header("Authorization") String token, @Path("id") String _id);

    @GET("/api/category/{id}")
    Call<List<Product>> getProductByCategory(@Path("id") String _id);

    @GET("/api/product/suggest")
    Call<List<Product>> suggestProduct();

    @GET("/api/product/search")
    Call<List<Product>> searchProduct(@Query("name") String name);

    @POST("/api/product/favorites")
    Call<Response> favoriteProduct(
            @Header("Authorization") String token,
            @Query("product") String product,
            @Query("love") Boolean love
    );

    @GET("/api/product/favorites")
    Call<List<Product>> getFavorites(
            @Header("Authorization") String token
    );
}
