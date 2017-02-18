package space.dennymades.nike;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import space.dennymades.nike.GooglePlayService.GooglePlayHelper;
import space.dennymades.nike.util.PermissionHelper;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private ViewPager mViewPager;
    private Button mButton;

    private GooglePlayHelper mGooglePlay;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        boolean permissionGranted = PermissionHelper.checkPermission(this, PermissionHelper.permissions);
        if(!permissionGranted){
            PermissionHelper.seekPermission(this, PermissionHelper.permissions, PermissionHelper.PERMISSION_ALL);
        }

        mTextView = (TextView)findViewById(R.id.tv_locaiton);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        final MyFragmentPagerAdapter fragmentAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentAdapter);
        //mViewPager.setPageMargin();
        mViewPager.setOffscreenPageLimit(3);
        //mViewPager.setPageMargin(-65);

//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int pageMargin = ((metrics.widthPixels / 4) * 2);
//
//        mViewPager.setPageMargin(-pageMargin);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.v(TAG, "current item : "+mViewPager.getCurrentItem());
                Log.v(TAG, "position : "+position);

                //set scale of view group
                //MyCarouselItem item = (MyCarouselItem) mViewPager.getChildAt(position);
                MyCarouselItem item = (MyCarouselItem) fragmentAdapter.getExistingItem(position).getView();
                MyCarouselItem item2 = (MyCarouselItem) fragmentAdapter.getExistingItem(position+1).getView();
                //Fragment frag = fragmentAdapter.getItem(position);
                if(item!=null){
                    item.setScale(1.3f-0.3f*positionOffset);
                    item2.setScale(1.0f+0.3f*positionOffset);
                    Log.v(TAG, "in here "+position);
                }
            }

            @Override
            public void onPageSelected(final int position) {
                Log.d(TAG, "on page"+position);
                mViewPager.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        setCurrentItemWithDelay(position);
                    }
                }, 450);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            private void setCurrentItemWithDelay(int position){
                if(position==0){
                    mViewPager.setCurrentItem(7, false);
                }else if(position==8){
                    mViewPager.setCurrentItem(1, false);
                }
            }
        });

        mViewPager.setCurrentItem(1, false);

        mButton = (Button)findViewById(R.id.btn_tracks);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGooglePlay.getLastLocation(getApplication());
                Location loc = mGooglePlay.getLastLocation(getApplicationContext());
                mTextView.setText(loc.getLatitude()+","+loc.getLongitude());

                String locality = mGooglePlay.getLocality(getApplicationContext(), loc);
                Log.d(TAG, locality);
            }
        });

        //
        mGooglePlay = new GooglePlayHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGooglePlay.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGooglePlay.disconnect();
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
