package com.example.android_tkpm.api;

import com.example.android_tkpm.models.Notify;
import com.example.android_tkpm.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface NotifyService {
    @GET("/api/notification")
    Call<List<Notify>> getAll(@Header("Authorization") String token);
}
