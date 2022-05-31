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

import com.example.android_tkpm.Notifications.PushNotificationService;
import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.AuthService;
import com.example.android_tkpm.models.SignIn;
import com.example.android_tkpm.models.User;
import com.example.android_tkpm.utils.TokenManager;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private Button backButton, signInBtn;
    private EditText email, password;
    private ProgressBar loadingSignIn;
    private TextView registerButton;

    private AuthService authService = ApiUtils.getUserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mapping();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                    email.getText().toString().trim().equals("") ||
                    password.getText().toString().trim().equals("")
                ) {
                    toastMsg("Please don't be empty");
                }
                else {
                    onSignIn();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }

    private void mapping() {
        backButton = findViewById(R.id.backBtn);
        signInBtn = findViewById(R.id.signInBtn);
        email = findViewById(R.id.emailEdt);
        password = findViewById(R.id.passwordEdt);
        loadingSignIn = findViewById(R.id.loadingSignIn);
        registerButton = (TextView) findViewById(R.id.registerBtn);
    }

    private void onSignIn() {
        loadingSignIn.setVisibility(View.VISIBLE);
        SignIn signIn = new SignIn(email.getText().toString().trim(), password.getText().toString().trim(), PushNotificationService.getToken(getApplicationContext()));
        authService.signIn(signIn).enqueue(new Callback<com.example.android_tkpm.models.Response>() {
            @Override
            public void onResponse(Call<com.example.android_tkpm.models.Response> call, Response<com.example.android_tkpm.models.Response> response) {
                loadingSignIn.setVisibility(View.INVISIBLE);
                Headers headers = response.headers();
                if (response.isSuccessful()) {
                    if(new TokenManager(SignInActivity.this).saveToken(headers.get("Authorization"))) {
                        setResult(RESULT_OK);
                        finish();
                    }
                }
                else if (response.code() == 401) {
                    toastMsg("Incorrect email or password!");
                }
                else if(response.code() == 403 ) {
                    try {
                        ResponseBody response1 = response.errorBody();
                        Gson gson = new Gson();
                        com.example.android_tkpm.models.Response responseError = gson.fromJson(response1.string(), com.example.android_tkpm.models.Response.class);
                        toastMsg(responseError.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<com.example.android_tkpm.models.Response> call, Throwable t) {
                loadingSignIn.setVisibility(View.INVISIBLE);
                toastMsg("Error");
            }
        });
    }

    private void toastMsg(String msg) {
        Toast toast =   Toast.makeText(SignInActivity.this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}