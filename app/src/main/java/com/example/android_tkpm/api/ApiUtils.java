package com.example.android_tkpm.api;

import com.example.android_tkpm.utils.ENV;

public class ApiUtils {
    public static final String BASE_URL = ENV.domain;

    public static AuthService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(AuthService.class);
    }

    public static ProductService getProductService() {
        return RetrofitClient.getClient(BASE_URL).create(ProductService.class);
    }

    public static CategoryService getCategoryService() {
        return RetrofitClient.getClient(BASE_URL).create(CategoryService.class);
    }

    public static OrderService getOrderService() {
        return RetrofitClient.getClient(BASE_URL).create(OrderService.class);
    }
}
