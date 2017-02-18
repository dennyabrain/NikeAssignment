package space.dennymades.nike.GooglePlayService;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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

import space.dennymades.nike.util.PermissionHelper;

/**
 * Created by abrain on 2/17/17.
 */

public class GooglePlayHelper implements ConnectionCallbacks, OnConnectionFailedListener {
    private final String TAG = this.getClass().getSimpleName();
    private GoogleApiClient mClient;

    public GooglePlayHelper(Context context){
        if(mClient==null){
            mClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
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
        boolean permission = (PermissionHelper.checkPermission(context, PermissionHelper.permissions));
        Location location;
        if(permission){
            location = LocationServices.FusedLocationApi.getLastLocation(mClient);
            return location;
        }else{
            return null;
        }
    }
}
