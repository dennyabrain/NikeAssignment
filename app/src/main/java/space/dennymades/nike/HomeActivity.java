package space.dennymades.nike;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import space.dennymades.nike.util.GooglePlayService.GooglePlayHelper;
import space.dennymades.nike.views.AnimatedTextView;
import space.dennymades.nike.util.PermissionHelper;
import space.dennymades.nike.util.networking.GooglePlacesService;
import space.dennymades.nike.util.networking.RetrofitHelper;
import space.dennymades.nike.util.networking.datamodels.ResultItem;
import space.dennymades.nike.views.LoopingCarousel.MyFragmentPagerAdapter;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private ViewPager mViewPager;
    private Button mButton;
    private TextView mTextViewMessage;
    private AnimatedTextView mTextViewResultMessage;
    private LinearLayout rootContent;

    private GooglePlayHelper mGooglePlay;
    private RetrofitHelper mRetrofitHelper;
    private GooglePlacesService mPlayService;

    private MyFragmentPagerAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        boolean permissionGranted = PermissionHelper.checkPermission(this, PermissionHelper.permissions);
        if(!permissionGranted){
            PermissionHelper.seekPermission(this, PermissionHelper.permissions, PermissionHelper.PERMISSION_ALL);
        }

        mTextViewMessage = (TextView)findViewById(R.id.tv_message);
        rootContent = (LinearLayout)findViewById(R.id.root_content);
        mTextViewResultMessage = (AnimatedTextView)findViewById(R.id.tv_result_message);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mRetrofitHelper = new RetrofitHelper();
        mPlayService = mRetrofitHelper.getPlacesService();
        mGooglePlay = new GooglePlayHelper(this);

        fragmentAdapter = new MyFragmentPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(fragmentAdapter);
        fragmentAdapter.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageMargin(-65);
        mViewPager.addOnPageChangeListener(fragmentAdapter);
        mViewPager.setCurrentItem(2, false);

        mButton = (Button)findViewById(R.id.btn_tracks);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "button clicked");

                ValueAnimator anim = ValueAnimator.ofFloat(1f, 0f);
                anim.setDuration(250);
                anim.setInterpolator(new FastOutSlowInInterpolator());

                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        //Log.d(TAG, "text view should update now");
                        //mTextViewMessage.setTranslationY((float)valueAnimator.getAnimatedValue()*height);

                        mTextViewMessage.setScaleX((float)valueAnimator.getAnimatedValue());
                        mTextViewMessage.setScaleY((float)valueAnimator.getAnimatedValue());

//                        String text = mTextViewMessage.getText().toString();
//                        if(text.length()!=0){
//                            mTextViewMessage.setText(text.subSequence(0, text.length()-1));
//                        }
                    }
                });

                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        //mTextViewMessage.setText("showing running tracks around "+locality+"\n ("+loc.getLatitude()+", "+loc.getLongitude()+")");
                        //mTextViewMessage.setText("running tracks around ");
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

                anim.start();

                //.doOnNext(v->{Log.d(TAG, ""+v);})

                List<String> placeNames = new ArrayList<String>();

                mPlayService.getPlacesNearby()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result->{
                            //Log.d(TAG, "result : "+result);
                            List<ResultItem> res = result.getResult();
                            for(int i=0;i<res.size();i++){
                                Log.d(TAG, "result "+i+" : "+res.get(i).getName());
                                placeNames.add(res.get(i).getName());
                            }
                            fragmentAdapter.setPlaces(placeNames);
                        });

                //make text animate to   top

                mViewPager.setAlpha(1);
                mViewPager.setVisibility(View.VISIBLE);
                ValueAnimator anim2 = ValueAnimator.ofFloat(0f, 1f);
                anim2.setDuration(1000);
                anim2.setInterpolator(new OvershootInterpolator());
                anim2.start();
                anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        //Log.d(TAG, "text view should update now");
                        mViewPager.setAlpha((float)valueAnimator.getAnimatedValue());
                        mViewPager.setScaleX((float)valueAnimator.getAnimatedValue());
                        mViewPager.setScaleY((float)valueAnimator.getAnimatedValue());
                    }
                });


                int btnWidth = mButton.getWidth();
                ValueAnimator anim3 = ValueAnimator.ofFloat(btnWidth, 0);
                anim3.setDuration(1000);
                anim3.setInterpolator(new DecelerateInterpolator());
                anim3.start();

                mButton.setText("OK");
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        mButton.setWidth((int)(float)valueAnimator.getAnimatedValue());
                    }
                });

                anim3.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });


                doLocationStuff();

            }
        });
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

    private void doLocationStuff(){
        //replace with rxjvaa
        Location loc = mGooglePlay.getLastLocation(getApplicationContext());
        String locality = mGooglePlay.getLocality(getApplicationContext(), loc);

        mTextViewResultMessage.setText("showing running tracks around "+locality+"\n("+roundOff(loc.getLongitude())+","+roundOff(loc.getLatitude())+")");
        mTextViewResultMessage.setVisibility(View.VISIBLE);
        mTextViewResultMessage.showText();
    }

    private float roundOff(double val){
        return Math.round(val*100)/100;
    }

}
