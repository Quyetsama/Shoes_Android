package com.example.android_tkpm.api;

import com.example.android_tkpm.models.SignIn;
import com.example.android_tkpm.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {
    @GET("/")
    Call<User> testApi();

    @POST("/api/auth/signin")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<User> signIn(@Body SignIn signIn);

    @GET("/api/auth/profile")
    Call<User> getProfile(@Header("Authorization") String token);

//    @FormUrlEncoded
//    Call<Login> savePost(@Field("username") String username,
//                         @Field("password") String password);
}
