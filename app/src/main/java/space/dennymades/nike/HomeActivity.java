package space.dennymades.nike;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import space.dennymades.nike.PermissionHelper;


public class HomeActivity extends AppCompatActivity{
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        boolean permissionGranted = PermissionHelper.checkPermission(this, PermissionHelper.permissions);
        if(!permissionGranted){
            PermissionHelper.seekPermission(this, PermissionHelper.permissions, PermissionHelper.PERMISSION_ALL);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "in request permisisons result");
        switch(requestCode){
            case PermissionHelper.PERMISSION_ALL:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.d(TAG, "location permission granted");
                }
                break;
        }
    }

}
