package com.example.android.AmritaResouce.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtil {

    public static boolean isNetworkConnected(Context context){
        boolean isConnected;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            isConnected = cm != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception ex){
            isConnected = false;
        }

        return isConnected;
    }
}
