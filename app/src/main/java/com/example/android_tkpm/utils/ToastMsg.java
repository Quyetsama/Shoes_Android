package com.example.android_tkpm.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastMsg {
    public static void show(Context context, String msg) {
        Toast toast =   Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
