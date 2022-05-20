package com.example.android_tkpm.api;

import com.example.android_tkpm.models.Category;
import com.example.android_tkpm.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    @GET("/api/category")
    Call<List<Category>> getAll();
}
