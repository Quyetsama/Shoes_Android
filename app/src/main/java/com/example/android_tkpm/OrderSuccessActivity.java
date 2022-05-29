package com.example.android_tkpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class OrderSuccessActivity extends AppCompatActivity {

    private ImageButton backButton;
    private TextView titleToolBar;
    private AppCompatButton completeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        mapping();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFirstScreen();
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFirstScreen();
            }
        });
    }

    private void mapping() {
        backButton = (ImageButton) findViewById(R.id.backBtn);
        titleToolBar = (TextView) findViewById(R.id.title_toolbar);
        completeButton = (AppCompatButton) findViewById(R.id.completeBtn);

        titleToolBar.setText(("Success"));
    }

    private void goToFirstScreen() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}