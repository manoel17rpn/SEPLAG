package br.com.seplag.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Manoel Neto on 29/04/2017.
 */

public class InternetHelper {

    public boolean TestConnection(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if(info != null && info.isConnected()){
            return true;
        }else{
            return false;
        }

    }
}
