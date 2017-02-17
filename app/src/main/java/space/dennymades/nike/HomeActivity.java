package space.dennymades.nike;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        final MyFragmentPagerAdapter fragmentAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentAdapter);
        //mViewPager.setPageMargin();
        mViewPager.setOffscreenPageLimit(3);

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
                    item.setScale(1.0f-0.3f*positionOffset);
                    item2.setScale(0.7f+0.3f*positionOffset);
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
                }, 300);
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

    }
}
