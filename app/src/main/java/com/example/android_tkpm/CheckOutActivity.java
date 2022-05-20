package com.example.android_tkpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.OrderService;
import com.example.android_tkpm.models.ItemCart;
import com.example.android_tkpm.models.ItemOrder;
import com.example.android_tkpm.models.OrderRequest;
import com.example.android_tkpm.models.Product;
import com.example.android_tkpm.models.Response;
import com.example.android_tkpm.utils.CartManager;
import com.example.android_tkpm.utils.TokenManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CheckOutActivity extends AppCompatActivity {

    private OrderService orderService = ApiUtils.getOrderService();

    private TextView titleToolBar;
    private MaterialButton backButton;

    private EditText fullnameEdt, phoneEdt, addressEdt;
    private TextView subTotal, shipping, total;
    private Button completeButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        mapping();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsValid()) {
                    sendOrder();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please complete", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void mapping() {
        backButton = (MaterialButton) findViewById(R.id.backBtn);
        titleToolBar = (TextView) findViewById(R.id.title_toolbar);
        fullnameEdt = (EditText) findViewById(R.id.fullname_edt);
        phoneEdt = (EditText) findViewById(R.id.phone_edt);
        addressEdt = (EditText) findViewById(R.id.address_edt);
        subTotal = (TextView) findViewById(R.id.subTotal);
        shipping = (TextView) findViewById(R.id.shipping);
        total = (TextView) findViewById(R.id.total);
        completeButton = (Button) findViewById(R.id.completeBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        //        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_cart);
//        backButton.setIcon(drawable);
        titleToolBar.setText(("Checkout"));

        subTotal.setText((CartManager.getTotal() + " $"));
        shipping.setText((CartManager.getCart().size() + " $"));
        total.setText((CartManager.getTotal() + CartManager.getCart().size() + " $"));
    }
    
    private Boolean IsValid() {
        return (
            !fullnameEdt.getText().toString().trim().equals("") &&
            !phoneEdt.getText().toString().trim().equals("") &&
            !addressEdt.getText().toString().trim().equals("")
        );
    }

    private void sendOrder() {
        progressBar.setVisibility(View.VISIBLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        List<ItemOrder> orderList = new ArrayList<>();

        for(ItemCart itemCart : CartManager.getCart()) {
            orderList.add(new ItemOrder(
                    itemCart.get_id(),
                    Integer.parseInt(itemCart.getSize()),
                    itemCart.getColor(),
                    itemCart.getPrice(),
                    itemCart.getQuantity()
            ));
        }

        orderService.sendOrder(
            new TokenManager(getApplicationContext()).getToken(),
            new OrderRequest(orderList,
            fullnameEdt.getText().toString().trim(),
            phoneEdt.getText().toString().trim(),
            addressEdt.getText().toString().trim(),
            CartManager.getTotal(),
            CartManager.getCart().size(),
            CartManager.getTotal() + 5)
        )
        .enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                progressBar.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                if(response.code() == 401) {
                    new TokenManager(getApplicationContext()).removeToken();
                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                }
                else {
                    CartManager.clearCart();
                    Intent intent = new Intent(getApplicationContext(), OrderSuccessActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }
}
