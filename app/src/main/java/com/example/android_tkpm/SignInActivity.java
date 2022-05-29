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

import org.json.JSONObject;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private Button backButton, signInBtn;
    private EditText email, password;
    private ProgressBar loadingSignIn;

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
                onSignIn();
            }
        });
    }

    private void mapping() {
        backButton = findViewById(R.id.backBtn);
        signInBtn = findViewById(R.id.signInBtn);
        email = findViewById(R.id.emailEdt);
        password = findViewById(R.id.passwordEdt);
        loadingSignIn = findViewById(R.id.loadingSignIn);
    }

    private void onSignIn() {
        loadingSignIn.setVisibility(View.VISIBLE);
        SignIn signIn = new SignIn(email.getText().toString().trim(), password.getText().toString().trim(), PushNotificationService.getToken(getApplicationContext()));
        authService.signIn(signIn).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
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
//                User user = response.body();
//
//                if (user != null) {
////                    Log.e("Response: ", user.isSuccess().toString);
//                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
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