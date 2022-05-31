package com.example.android_tkpm.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_tkpm.EditProfileActivity;
import com.example.android_tkpm.FavoritesActivity;
import com.example.android_tkpm.MainActivity;
import com.example.android_tkpm.Notifications.PushNotificationService;
import com.example.android_tkpm.OrderHistoryActivity;
import com.example.android_tkpm.R;
import com.example.android_tkpm.SignInActivity;
import com.example.android_tkpm.SignUpActivity;
import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.AuthService;
import com.example.android_tkpm.models.User;
import com.example.android_tkpm.utils.TokenManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment {

    private static final int RESULT_OK = -1;
    private AuthService authService = ApiUtils.getUserService();

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar loadingProfile;
    private RelativeLayout containerProfile, containerAuth, history_container, favorite_container;
    private TextView nameUser, emailUser, logout;
    private AppCompatButton signinButton, signupButton;
    private ImageButton editButton;

    public UserFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        mapping(view);

        checkToken();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                checkToken();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), SignInActivity.class), 1);
//                startActivity(new Intent(getActivity(), SignInActivity.class));
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignUpActivity.class));
            }
        });

        history_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OrderHistoryActivity.class));
            }
        });

        favorite_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FavoritesActivity.class));
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), EditProfileActivity.class));
                startActivityForResult(new Intent(getActivity(), EditProfileActivity.class), 1);
            }
        });

        return view;
    }

    private void mapping(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_profile);
        containerProfile = (RelativeLayout) view.findViewById(R.id.container_profile);
        containerAuth = (RelativeLayout) view.findViewById(R.id.auth_container);
        loadingProfile = (ProgressBar) view.findViewById(R.id.loading_profile);
        nameUser = (TextView) view.findViewById(R.id.name_profile);
        emailUser = (TextView) view.findViewById(R.id.email_profile);
        logout = (TextView) view.findViewById(R.id.logout);
        signinButton = (AppCompatButton) view.findViewById(R.id.signin_button);
        signupButton = (AppCompatButton) view.findViewById(R.id.signup_button);
        history_container = (RelativeLayout) view.findViewById(R.id.history_container);
        favorite_container = (RelativeLayout) view.findViewById(R.id.favorite_container);
        editButton = (ImageButton) view.findViewById(R.id.edit_profile);
    }

    private void getProfile() {
        loadingProfile.setVisibility(View.VISIBLE);

        authService.getProfile(new TokenManager(getActivity()).getToken()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    loadingProfile.setVisibility(View.GONE);

                    User.setUser(response.body());
                    if(User.getUser() != null) {
                        nameUser.setText(User.getUser().getFullName());
                        emailUser.setText(User.getUser().getEmail());
                        containerProfile.setVisibility(View.VISIBLE);
                        containerAuth.setVisibility(View.GONE);
                    }
                }
                else if (response.code() == 401) {
                    new TokenManager(getActivity()).removeToken();
                    loadingProfile.setVisibility(View.GONE);
                    containerProfile.setVisibility(View.GONE);
                    containerAuth.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                loadingProfile.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkToken() {
        if((new TokenManager(getActivity()).getToken()).equals("")) {
            loadingProfile.setVisibility(View.GONE);
            containerProfile.setVisibility(View.GONE);
            containerAuth.setVisibility(View.VISIBLE);
        }
        else {
            getProfile();
        }
    }

    private void logOut() {
        loadingProfile.setVisibility(View.VISIBLE);
        TokenManager tokenManager = new TokenManager(getActivity());;
        String tokenDevice = PushNotificationService.getToken(getActivity());
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("tokenDevice", tokenDevice);

        authService.logOut(tokenManager.getToken(), requestBody).enqueue(new Callback<com.example.android_tkpm.models.Response>() {
            @Override
            public void onResponse(Call<com.example.android_tkpm.models.Response> call, Response<com.example.android_tkpm.models.Response> response) {
                loadingProfile.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    tokenManager.removeToken();
                    containerProfile.setVisibility(View.GONE);
                    containerAuth.setVisibility(View.VISIBLE);
                }
                else if(response.code() == 401) {
                    tokenManager.removeToken();
                }
            }

            @Override
            public void onFailure(Call<com.example.android_tkpm.models.Response> call, Throwable t) {
                loadingProfile.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if(MainActivity.active.getClass().getSimpleName().equals("UserFragment")) {
//            checkToken();
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            checkToken();
        }
    }
}