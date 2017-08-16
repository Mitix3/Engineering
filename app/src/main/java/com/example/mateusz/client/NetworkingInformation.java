package com.example.mateusz.client;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Mateusz on 15/03/2017.
 */

public abstract class NetworkingInformation {


    public static NetworkInfo createWifiManager(Context contextOfCurrentActivity)
    {
        ConnectivityManager connManager;

        connManager = (ConnectivityManager) contextOfCurrentActivity.getSystemService(contextOfCurrentActivity.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo;
    }

    public static NetworkInfo createMobileConnectionManager (Context contextOfCurrentActivity)
    {
        ConnectivityManager connManager;

        connManager = (ConnectivityManager) contextOfCurrentActivity.getSystemService(contextOfCurrentActivity.CONNECTIVITY_SERVICE);
        NetworkInfo mobileConnection = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobileConnection;


    }

    public static boolean checkIfWifiConnectionIsEstabilished(NetworkInfo wifiInformation, Context contextOfCurrentActivity)
    {
        if (wifiInformation.isConnected())
        {
            Toast.makeText(contextOfCurrentActivity, "You are connected to WIFI",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        else
        {
            Toast.makeText(contextOfCurrentActivity, "You are not connected to WIFI",
                    Toast.LENGTH_LONG).show();
            return false;
        }


    }

    public static boolean checkIfMobileConnectionIsEstabilished(NetworkInfo mobileConnection, Context contextOfCurrentActivity)
    {
        if (mobileConnection.isConnected())
        {
            Toast.makeText(contextOfCurrentActivity, "You are connected to mobile network",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        else
        {
            Toast.makeText(contextOfCurrentActivity, "You are not connected to mobile network",
                    Toast.LENGTH_LONG).show();
            return false;
        }


    }





}
