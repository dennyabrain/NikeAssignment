package space.dennymades.nike.views.LoopingCarousel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.List;

import space.dennymades.nike.HomeActivity;
import space.dennymades.nike.R;

/**
 * Created by abrain on 2/15/17.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener{
    private final String TAG = this.getClass().getSimpleName();

    private final int NUM_PAGES = 6;
    private final int DUPLICATE_PAGES = 2;
    private final int listSize = NUM_PAGES+2*DUPLICATE_PAGES; //10
    private MyMapFragment[] fragments = new MyMapFragment[listSize];
    private List<String> placeNames;
    private ViewPager viewPager;
    private FragmentManager mFragmentManager;
    private Context mContext;

    public MyFragmentPagerAdapter(HomeActivity context, FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        MyMapFragment frag = new MyMapFragment();
        Bundle args = new Bundle();

        args.putInt("actualPosition", position);
        args.putInt("derivedPosition", getPageNumber(position));

        if(placeNames!=null){
            if(!placeNames.isEmpty()){
                args.putString("placeName", placeNames.get(position));
            }
        }

        frag.setArguments(args);

        //frag.showText();
        fragments[position] = frag;
        return frag;
    }

    private int getPageNumber(int position){
        int y = (position+1-DUPLICATE_PAGES);

        if(y<=0){
            return position+NUM_PAGES-1;
        }else if(y>NUM_PAGES){
            return position-NUM_PAGES-1;
        }else{
            return y;
        }
    }


    public MyMapFragment getExistingItem(int position){
        MyMapFragment frag = (MyMapFragment) fragments[position];
        if(placeNames!=null){
            if(!placeNames.isEmpty()){
                frag.updatePlace(placeNames.get(position));
            }
        }
        return fragments[position];
    }


    @Override
    public int getCount() {
        return NUM_PAGES+2*DUPLICATE_PAGES;
    }


    @Override
    public float getPageWidth(int position) {
        return 0.75f;
        //return 1.0f;
    }

    public void setPlaces(List<String> names){
        placeNames = names;
        MyMapFragment frag = (MyMapFragment) mFragmentManager.findFragmentByTag(this.getFragmentTag(viewPager.getCurrentItem()));
        frag.updatePlace(placeNames.get(getPageNumber(viewPager.getCurrentItem())));
        frag.showText();
        frag.hideProgressBar();
    }

    public void updateTextOnPageSelect(int position){
        for(int i=0;i<NUM_PAGES+2;i++){
            MyMapFragment frag = (MyMapFragment) fragments[i];
            if(frag!=null){
                if(i==position){
                    frag.showText();
                }else{
                    frag.hideText();
                }
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        try {
            if (positionOffset >= 0f && positionOffset <= 1f) {
                MyCarouselItem cur = getRootView(position);
                MyCarouselItem next = getRootView(position + 1);

                cur.setScale(1.3f - 0.3f * positionOffset);
                next.setScale(1.0f + 0.3f * positionOffset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "page selected : "+position);
        MyMapFragment frag = (MyMapFragment) mFragmentManager.findFragmentByTag(this.getFragmentTag(viewPager.getCurrentItem()));
        if(placeNames!=null){
            frag.updatePlace(placeNames.get(getPageNumber(position)));
            frag.showText();
            frag.hideProgressBar();

            for(int i=0;i<listSize;i++){
                if(i!=position){
                    MyMapFragment fragment = (MyMapFragment) mFragmentManager.findFragmentByTag(this.getFragmentTag(i));
                    if(fragment!=null){
                        fragment.hideProgressBar();
                        //fragment.makeInvisible();
                        fragment.hideText();
                    }
                }
            }

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(state==ViewPager.SCROLL_STATE_IDLE){
            setCurrentItemWithDelay(viewPager.getCurrentItem());
        }
    }

    public void setViewPager(ViewPager vp){
        viewPager = vp;
    }

    private void setCurrentItemWithDelay(int position){
        if(position==1){
            viewPager.setCurrentItem(NUM_PAGES+1, false);
        }else if(position==NUM_PAGES+DUPLICATE_PAGES){
            viewPager.setCurrentItem(DUPLICATE_PAGES, false);
        }else{

        }
    }

    private MyCarouselItem getRootView(int position) {
        return (MyCarouselItem) mFragmentManager.findFragmentByTag(this.getFragmentTag(position))
                .getView().findViewById(R.id.root_layout);
    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + viewPager.getId() + ":" + position;
    }
}
