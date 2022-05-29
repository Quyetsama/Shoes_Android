package com.example.android_tkpm;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity {

    private AuthService authService = ApiUtils.getUserService();

    private Button backButton, signUpButton;
    private EditText fullName, email, password;
    private ProgressBar loadingSignUp;

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
                    toastMsg("Please don't be empt");
                }
                else {
                    signUp();
                }
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
    }

    private void signUp() {
        loadingSignUp.setVisibility(View.VISIBLE);

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("fullName", fullName.getText().toString().trim());
        requestBody.addProperty("email", email.getText().toString().trim());
        requestBody.addProperty("password", password.getText().toString().trim());

        authService.signUp(requestBody).enqueue(new Callback<Response>() {
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
                    toastMsg("Email already exists");
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