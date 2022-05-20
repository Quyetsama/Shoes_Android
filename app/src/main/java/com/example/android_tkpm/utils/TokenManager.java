package com.example.android_tkpm.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static SharedPreferences mSharedPref;
    private final int PRIVATE_MODE = 0;

    public TokenManager() { }

    public TokenManager(Context context) {
        mSharedPref = context.getSharedPreferences("TOKEN", PRIVATE_MODE);
    }

    public boolean saveToken(String value) {
        SharedPreferences.Editor editor = mSharedPref.edit ();
        editor.putString ("token", value);
        return editor.commit ();
    }

    public String getToken() {
        String value = mSharedPref.getString ("token", "");
        return value;
    }

    public boolean removeToken() {
        return mSharedPref.edit().remove("token").commit();
    }
}
