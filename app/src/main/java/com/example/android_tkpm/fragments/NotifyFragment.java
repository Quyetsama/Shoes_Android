package com.example.android_tkpm.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_tkpm.CartActivity;
import com.example.android_tkpm.MainActivity;
import com.example.android_tkpm.OrderHistoryActivity;
import com.example.android_tkpm.R;
import com.example.android_tkpm.adapter.NotifyAdapter;
import com.example.android_tkpm.api.ApiUtils;
import com.example.android_tkpm.api.NotifyService;
import com.example.android_tkpm.models.Notify;
import com.example.android_tkpm.utils.CartManager;
import com.example.android_tkpm.utils.TimeAgo;
import com.example.android_tkpm.utils.TokenManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotifyFragment extends Fragment {

    private NotifyService notifyService = ApiUtils.getNotifyService();

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lvNotify;
    private NotifyAdapter notifyAdapter;
    private List<Notify> notifyList = new ArrayList<>();
    private TextView titleToolbar;
    private ImageButton cartBtn;
    public static TextView badgeCart;
    private ProgressBar loading;

    public NotifyFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notify, container, false);

        mapping(view);

        getNotify();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getNotify();
            }
        });

        lvNotify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
                intent.putExtra("type", notifyList.get(position).getType());
                startActivity(intent);
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });

        return view;
    }

    private void mapping(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_notify);
        lvNotify = (ListView) view.findViewById(R.id.lvNotify);
        titleToolbar = (TextView) view.findViewById(R.id.title_toolbar);
        cartBtn = (ImageButton) view.findViewById(R.id.cartBtn);
        badgeCart = (TextView) view.findViewById(R.id.badge_cart_2);
        loading = (ProgressBar) view.findViewById(R.id.loading_notify);

        titleToolbar.setText("Notifications");
        badgeCart.setText((CartManager.getCart().size() + ""));
        notifyAdapter = new NotifyAdapter(getActivity(), R.layout.item_notify, notifyList);
        lvNotify.setAdapter(notifyAdapter);
    }

    private void getNotify() {
        loading.setVisibility(View.VISIBLE);

        notifyService.getAll(new TokenManager(getActivity()).getToken()).enqueue(new Callback<List<Notify>>() {
            @Override
            public void onResponse(Call<List<Notify>> call, Response<List<Notify>> response) {
                loading.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    notifyAdapter.updateNotifyList(response.body());
                }
                else if(response.code() == 401) {
                    notifyAdapter.updateNotifyList(notifyList);
                }
            }

            @Override
            public void onFailure(Call<List<Notify>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTokenFirebase() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.e("Token_FCM: ", token);
                        Toast.makeText(getActivity(), token, Toast.LENGTH_LONG).show();
                    }
                });
    }
}