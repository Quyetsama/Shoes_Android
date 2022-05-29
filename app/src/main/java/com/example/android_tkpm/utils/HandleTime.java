package com.example.android_tkpm.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HandleTime {

    public static String convertMongoDate(String val){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat= new SimpleDateFormat("dd/MM/yyyy");
        try {
            String finalStr = outputFormat.format(inputFormat.parse(val));
            System.out.println(finalStr);
            return finalStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
