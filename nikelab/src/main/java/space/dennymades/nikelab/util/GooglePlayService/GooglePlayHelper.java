package space.dennymades.nikelab.util.GooglePlayService;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import space.dennymades.nikelab.util.PermissionHelper;

/**
 * Created by abrain on 2/17/17.
 */

public class GooglePlayHelper implements ConnectionCallbacks, OnConnectionFailedListener {
    private final String TAG = this.getClass().getSimpleName();
    private GoogleApiClient mClient;
    private Context mContext;

    public GooglePlayHelper(Context context){
        if(mClient==null){
            mClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mContext = context;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "g-play disconnected");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "g-play connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "g-play suspended");
    }

    public GoogleApiClient getClient(){
        return mClient;
    }

    public void connect(){
        mClient.connect();
    }

    public void disconnect(){
        mClient.disconnect();
    }

    public Location getLastLocation(Context context){
        boolean permission = (PermissionHelper.checkPermission(mContext, PermissionHelper.permissions));
        Log.d(TAG, "permission : "+permission);
        Location location;
        if(permission){
            location = LocationServices.FusedLocationApi.getLastLocation(mClient);
            Log.d(TAG, ""+location);
            return location;
        }else{
            return null;
        }
    }

    public String getLocality(Context context, Location loc){
        String address="";
        Geocoder gcd = new Geocoder(mContext, Locale.getDefault());

        if(gcd.isPresent()){
            Log.d(TAG, "gcd is present");
        }

        List<Address> addresses = null;
        try {
            if(loc!=null){
                addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            }else{
                return "N/A";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses==null){
            return "N/A";
        }
        if (addresses.size() > 0)
        {
            address = addresses.get(0).getLocality();
        }
        else
        {
            address = "Location Name unavailable";
        }

        return address;
    }

    public Context getContext(){
        return mContext;
    }
}