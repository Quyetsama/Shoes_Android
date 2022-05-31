package com.example.android_tkpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.AuthService;
import com.example.android_tkpm.models.Response;
import com.example.android_tkpm.models.SignUp;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity {

    private AuthService authService = ApiUtils.getUserService();

    private Button backButton, signUpButton;
    private EditText fullName, email, password;
    private ProgressBar loadingSignUp;
    private TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mapping();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                    fullName.getText().toString().trim().equals("") ||
                    email.getText().toString().trim().equals("") ||
                    password.getText().toString().trim().equals("")
                ) {
                    toastMsg("Please don't be empty");
                }
                else {
                    signUp();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });
    }

    private void mapping() {
        backButton = (Button) findViewById(R.id.backBtn);
        signUpButton = (Button) findViewById(R.id.signUpBtn);
        fullName = (EditText) findViewById(R.id.fullNameEdt);
        email = (EditText) findViewById(R.id.emailEdt);
        password = (EditText) findViewById(R.id.passwordEdt);
        loadingSignUp = (ProgressBar) findViewById(R.id.loadingSignUp);
        loginButton = (TextView) findViewById(R.id.loginBtn);
    }

    private void signUp() {
        loadingSignUp.setVisibility(View.VISIBLE);

//        JsonObject requestBody = new JsonObject();
//        requestBody.addProperty("fullName", fullName.getText().toString().trim());
//        requestBody.addProperty("email", email.getText().toString().trim());
//        requestBody.addProperty("password", password.getText().toString().trim());

        SignUp signUp = new SignUp(
            fullName.getText().toString().trim(),
            email.getText().toString().trim(),
            password.getText().toString().trim()
        );

        authService.signUp(signUp).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                loadingSignUp.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    toastMsg("Success");
                    fullName.setText("");
                    email.setText("");
                    password.setText("");
                }
                else if(response.code() == 403 ) {
                    try {
                        ResponseBody response1 = response.errorBody();
                        Gson gson = new Gson();
                        Response responseError = gson.fromJson(response1.string(), Response.class);
                        toastMsg(responseError.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                loadingSignUp.setVisibility(View.GONE);
                toastMsg("Error");
            }
        });
    }

    private void toastMsg(String msg) {
        Toast toast =   Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}