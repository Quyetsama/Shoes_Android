package com.example.android_tkpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.AuthService;
import com.example.android_tkpm.models.Response;
import com.example.android_tkpm.models.User;
import com.example.android_tkpm.utils.ToastMsg;
import com.example.android_tkpm.utils.TokenManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class EditProfileActivity extends AppCompatActivity {

    private AuthService authService = ApiUtils.getUserService();

    private ImageButton backButton;
    private TextView titleToolbar;
    private RelativeLayout form_edit_password;
    private EditText nameEdt, emailEdt, currentPassEdt, newPass1Edt, newPass2Edt;
    private TextView edit;
    private Boolean isEditPassword = false;
    private ProgressBar loadingEditProfile;

    private AppCompatButton completeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mapping();
    }

    private void mapping() {
        backButton = (ImageButton) findViewById(R.id.backBtn);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);
        form_edit_password = (RelativeLayout) findViewById(R.id.form_edit_password);
        nameEdt = (EditText) findViewById(R.id.fullname_edt);
        emailEdt = (EditText) findViewById(R.id.email_edt);
        currentPassEdt = (EditText) findViewById(R.id.password_edt);
        newPass1Edt = (EditText) findViewById(R.id.password_edt_1);
        newPass2Edt = (EditText) findViewById(R.id.password_edt_2);
        edit = (TextView) findViewById(R.id.edit_password);
        completeButton = (AppCompatButton) findViewById(R.id.completeBtn);
        loadingEditProfile = (ProgressBar) findViewById(R.id.loading_edit_profile);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleToolbar.setText(("Profile"));

        nameEdt.setText(User.getUser().getFullName());
        emailEdt.setText(User.getUser().getEmail());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditPassword) {
                    form_edit_password.setVisibility(View.GONE);
                    currentPassEdt.setFocusable(false);
                    currentPassEdt.setText("************");
                    newPass1Edt.setText("");
                    newPass2Edt.setText("");
                    edit.setText(("Edit"));
                }
                else {
                    form_edit_password.setVisibility(View.VISIBLE);
                    currentPassEdt.setFocusableInTouchMode(true);
                    currentPassEdt.setText("");
                    edit.setText(("Cancel"));
                }

                isEditPassword = !isEditPassword;

            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()) {
                    updateProfile();
                }
                else {
                    ToastMsg.show(getApplicationContext(), "Not validated");
                }
            }
        });
    }

    private Boolean isValid() {
        return (
                !nameEdt.getText().toString().trim().equals(User.getUser().getFullName()) ||
                (
                      isEditPassword &&
                      !currentPassEdt.getText().toString().trim().equals("") &&
                      !newPass1Edt.getText().toString().trim().equals("") &&
                      !newPass2Edt.getText().toString().trim().equals("") &&
                      newPass2Edt.getText().toString().trim().equals(newPass1Edt.getText().toString().trim())
                )
        );
    }

    private void updateProfile() {
        loadingEditProfile.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        TokenManager tokenManager = new TokenManager(getApplicationContext());

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("fullName", nameEdt.getText().toString().trim());
        if(isEditPassword) {
            requestBody.addProperty("currentPassword", currentPassEdt.getText().toString().trim());
            requestBody.addProperty("newPassword1", newPass1Edt.getText().toString().trim());
            requestBody.addProperty("newPassword2", newPass2Edt.getText().toString().trim());
        }

        authService.updateProfile(tokenManager.getToken(), requestBody).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                loadingEditProfile.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if(response.isSuccessful()) {
                    Response responseServer = response.body();
                    ToastMsg.show(getApplicationContext(), responseServer.getMessage());
                    setResult(RESULT_OK);
                    finish();
                }
                else if(response.code() == 403 ) {
                    try {
                        ResponseBody responseServer = response.errorBody();
                        Gson gson = new Gson();
                        Response responseError = gson.fromJson(responseServer.string(), Response.class);
                        ToastMsg.show(getApplicationContext(), responseError.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(response.code() == 401) {
                    tokenManager.removeToken();
                    User.clearUser();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                loadingEditProfile.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                ToastMsg.show(getApplicationContext(), "Error");
            }
        });
    }
}