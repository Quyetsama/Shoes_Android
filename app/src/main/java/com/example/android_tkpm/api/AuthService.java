package com.example.android_tkpm.api;

import com.example.android_tkpm.models.Response;
import com.example.android_tkpm.models.SignIn;
import com.example.android_tkpm.models.SignUp;
import com.example.android_tkpm.models.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface AuthService {
    @GET("/")
    Call<User> testApi();

    @POST("/api/auth/signin")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Response> signIn(@Body SignIn signIn);

    @POST("/api/auth/signup")
    @Headers({ "Content-Type: application/json" })
    Call<Response> signUp(@Body SignUp signUp);

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

    @PATCH("/api/auth/profile")
    @Headers({ "Content-Type: application/json" })
    Call<Response> updateProfile(@Header("Authorization") String token, @Body JsonObject object);

//    @FormUrlEncoded
//    Call<Login> savePost(@Field("username") String username,
//                         @Field("password") String password);
}
