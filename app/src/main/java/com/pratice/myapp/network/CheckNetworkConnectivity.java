package com.pratice.myapp.network;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CheckNetworkConnectivity {
    public static boolean isNetworkAvailable=true;

    public static NetworkCallbackClass instance;
    public NetworkCallbackClass getInstance(){
        if(instance==null){
            instance=new NetworkCallbackClass();
        }
        return instance;
    }

    public interface NetworkConnection{
        public void onDisconnect();
        public void onConnect();
    }

    public static void  registerNetwork(Context context){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest request = new NetworkRequest.Builder()
//                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//                    .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
                    .build();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                connectivityManager.requestNetwork(request,instance,1000);
            }

        }catch (Exception e){
                isNetworkAvailable = false;
        }
    }
    public static void  unRegisterNetwork(Context context){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.unregisterNetworkCallback(instance);
            }
        }catch (Exception e){
            isNetworkAvailable = false;
        }
    }

    public class NetworkCallbackClass extends ConnectivityManager.NetworkCallback{
        @Override
        public void onAvailable (Network network){
            InetAddress inetAddress;
            try {
                inetAddress=network.getByName("www.google.com");
            } catch (UnknownHostException e) {
                inetAddress=null;
            }
            if(inetAddress!=null){
                isNetworkAvailable=true;
            }
            else{
                isNetworkAvailable=false;
            }

        }
        @Override
        public void onUnavailable (){
            isNetworkAvailable=false;
        }
//        @Override
//        public void onLost (Network network){
//            isNetworkAvailable=false;
//        }
        @Override
        public void onCapabilitiesChanged (Network network,
                                           NetworkCapabilities networkCapabilities)
        {
            InetAddress inetAddress;
            try {
                inetAddress=network.getByName("www.google.com");
            } catch (UnknownHostException e) {
                inetAddress=null;
            }
         if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && (inetAddress!=null)) {
             isNetworkAvailable=true;
         }
         else{
             isNetworkAvailable=false;
         }
        }
//        @Override
//        public void onLinkPropertiesChanged (Network network, LinkProperties linkProperties){
//
//        }
    }

}
