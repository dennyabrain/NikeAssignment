package space.dennymades.nike;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MyFragmentPagerAdapter fragmentAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

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
