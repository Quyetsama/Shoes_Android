package com.example.android_tkpm.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_tkpm.MainActivity;
import com.example.android_tkpm.R;
import com.example.android_tkpm.SignInActivity;
import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.AuthService;
import com.example.android_tkpm.models.User;
import com.example.android_tkpm.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment {

    private AuthService authService = ApiUtils.getUserService();

    private ProgressBar loadingProfile;
    private RelativeLayout containerProfile, containerAuth;
    private TextView nameUser, emailUser, logout;
    private AppCompatButton signinButton, signupButton;

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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TokenManager(getActivity()).removeToken();
                containerProfile.setVisibility(View.GONE);
                containerAuth.setVisibility(View.VISIBLE);
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignInActivity.class));
            }
        });

        return view;
    }

    private void mapping(View view) {
        containerProfile = (RelativeLayout) view.findViewById(R.id.container_profile);
        containerAuth = (RelativeLayout) view.findViewById(R.id.auth_container);
        loadingProfile = (ProgressBar) view.findViewById(R.id.loading_profile);
        nameUser = (TextView) view.findViewById(R.id.name_profile);
        emailUser = (TextView) view.findViewById(R.id.email_profile);
        logout = (TextView) view.findViewById(R.id.logout);
        signinButton = (AppCompatButton) view.findViewById(R.id.signin_button);
        signupButton = (AppCompatButton) view.findViewById(R.id.signup_button);

    }

    private void getProfile() {
        loadingProfile.setVisibility(View.VISIBLE);

        authService.getProfile(new TokenManager(getActivity()).getToken()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    loadingProfile.setVisibility(View.GONE);

                    User user = response.body();
                    if(user != null) {
                        Toast.makeText(getActivity(), user.getFullName(), Toast.LENGTH_SHORT).show();
                        nameUser.setText(user.getFullName());
                        emailUser.setText(user.getEmail());
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

    @Override
    public void onResume() {
        super.onResume();
        if(MainActivity.active.getClass().getSimpleName().equals("UserFragment")) {
            checkToken();
        }
    }
}