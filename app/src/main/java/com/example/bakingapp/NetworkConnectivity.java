package com.example.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkConnectivity implements ConnectivityManager.OnNetworkActiveListener{

    private static final String TAG = NetworkConnectivity.class.getSimpleName();

    private boolean isConnected;
    private Context context;

    public NetworkConnectivity(Context ctx) {
        this.context = ctx;
    }


    @Override
    public void onNetworkActive() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnected();

        Log.d(TAG, "onNetworkActive: " + isConnected);
        if(isConnected == true) {
            Toast.makeText(context, "Network Connection GOOD!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Network Connection BAD!", Toast.LENGTH_SHORT).show();
        }


    }
}
