package com.pratice.myapp.network;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

public class CheckNetworkConnectivity {
    public static boolean isNetworkAvailable=true;

    public static NetworkCallbackClass instance;

    public void invalidateNetworkCallback(){
        instance=null;
    }
    public NetworkCallbackClass getInstance(NetworkConnection networkConnection){
        if(instance==null){
            instance=new NetworkCallbackClass(networkConnection);
        }
        return instance;
    }

    public interface NetworkConnection{
        void onDisconnect();
        void onConnect();
    }

    public static void  registerNetwork(Context context){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest request = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
                    .build();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                connectivityManager.requestNetwork(request,instance,1000);
                connectivityManager.registerNetworkCallback(request,instance);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void  unRegisterNetwork(Context context){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.unregisterNetworkCallback(instance);
                connectivityManager.unregisterNetworkCallback(instance);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static class NetworkCallbackClass extends ConnectivityManager.NetworkCallback{
        NetworkConnection networkConnection;
        NetworkCallbackClass(NetworkConnection networkConnection){
            this.networkConnection=networkConnection;
        }
        @Override
        public void onAvailable (Network network){
            super.onAvailable(network);
            networkConnection.onConnect();
        }
        @Override
        public void onUnavailable (){
            super.onUnavailable();
            isNetworkAvailable=false;
            networkConnection.onDisconnect();
        }
        @Override
        public void onLost (Network network){
            super.onLost(network);
            isNetworkAvailable=false;
            networkConnection.onDisconnect();
        }


        @Override
        public void onCapabilitiesChanged (Network network,
                                           NetworkCapabilities networkCapabilities)
        {
            super.onCapabilitiesChanged(network, networkCapabilities);
         if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
             isNetworkAvailable=true;
             networkConnection.onConnect();
         }
         else{
             isNetworkAvailable=false;
             networkConnection.onDisconnect();
         }
        }
    }

}
