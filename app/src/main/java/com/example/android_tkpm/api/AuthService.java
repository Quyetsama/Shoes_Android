package com.example.android_tkpm.api;

import com.example.android_tkpm.models.Response;
import com.example.android_tkpm.models.SignIn;
import com.example.android_tkpm.models.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
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

    @POST("/api/auth/signup")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Response> signUp(@Body JsonObject object);

//    @POST("/api/auth/signup")
//    @FormUrlEncoded
//    Call<Response> signUp(
//        @Field("fullName") String fullName,
//        @Field("email") String email,
//        @Field("password") String password
//    );


    @POST("/api/auth/logout")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Response> logOut(@Header("Authorization") String token, @Body JsonObject tokenDevice);

    @GET("/api/auth/profile")
    Call<User> getProfile(@Header("Authorization") String token);

//    @FormUrlEncoded
//    Call<Login> savePost(@Field("username") String username,
//                         @Field("password") String password);
}
