package space.dennymades.nikelab;

import android.animation.ValueAnimator;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import space.dennymades.nikelab.util.GooglePlayService.GooglePlayHelper;
import space.dennymades.nikelab.util.GooglePlayService.LocationBackgroundTask;
import space.dennymades.nikelab.util.GooglePlayService.LocationHelper;
import space.dennymades.nikelab.util.networking.GooglePlacesService;
import space.dennymades.nikelab.util.networking.RetrofitHelper;
import space.dennymades.nikelab.util.networking.datamodels.ResultItem;
import space.dennymades.nikelab.views.AnimatedTextView;
import space.dennymades.nikelab.views.LoopingCarousel.MyFragmentPagerAdapter;

/**
 * Created by abrain on 2/22/17.
 */

public class NikeFragment extends Fragment implements BackgroundTasks{
    private final String TAG = this.getClass().getSimpleName();

    private ViewPager mViewPager;
    private Button mButton;
    private TextView mTextViewMessage;
    private AnimatedTextView mTextViewResultMessage;
    private LinearLayout rootContent;

    private GooglePlayHelper mGooglePlay;
    private RetrofitHelper mRetrofitHelper;
    private GooglePlacesService mPlaceService;

    private MyFragmentPagerAdapter fragmentAdapter;

    private Location mLocation;
    private String mLocality;

    private LocationHelper mLocationHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nike, container, false);

        mTextViewMessage = (TextView)view.findViewById(R.id.tv_message);
        rootContent = (LinearLayout)view.findViewById(R.id.root_content);
        mTextViewResultMessage = (AnimatedTextView)view.findViewById(R.id.tv_result_message);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        mGooglePlay = new GooglePlayHelper(getActivity());

        fragmentAdapter = new MyFragmentPagerAdapter(getActivity(), getFragmentManager());
        mViewPager.setAdapter(fragmentAdapter);
        fragmentAdapter.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageMargin(-65);
        mViewPager.addOnPageChangeListener(fragmentAdapter);
        mViewPager.setCurrentItem(2, false);

        final BackgroundTasks task = this;

        mButton = (Button)view.findViewById(R.id.btn_tracks);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "button clicked");

                //animate textview
                ValueAnimator anim = ValueAnimator.ofFloat(1f, 0f);
                anim.setDuration(250)
                        .setInterpolator(new FastOutSlowInInterpolator());

                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mTextViewMessage.setScaleX((float)valueAnimator.getAnimatedValue());
                        mTextViewMessage.setScaleY((float)valueAnimator.getAnimatedValue());
                    }
                });
                anim.start();

                //animate viewpager
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

                //animate button
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

                //figure out latitude, longitude and locality name
                new LocationBackgroundTask(task, mTextViewResultMessage).execute(mGooglePlay);

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGooglePlay.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGooglePlay.disconnect();
    }

    public void fetchNearbyPlaces(Location location){
        mRetrofitHelper = new RetrofitHelper();
        mPlaceService = mRetrofitHelper.getPlacesService();

        String param = location.getLatitude()+","+location.getLongitude();
        //fetch nearby places from Google API
        List<String> placeNames = new ArrayList<String>();
        mPlaceService.getPlacesNearby(param)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->{
                    List<ResultItem> res = result.getResult();
                    for(int i=0;i<res.size();i++){
                        Log.d(TAG, "result "+i+" : "+res.get(i).getName());
                        placeNames.add(res.get(i).getName());
                    }
                    fragmentAdapter.setPlaces(placeNames);
                });
    }

    @Override
    public void onCompleted(Location location) {
        fetchNearbyPlaces(location);
        Log.d(TAG, "on completed signal rcvd");
    }
}
