package space.dennymades.nike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.List;

/**
 * Created by abrain on 2/15/17.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener{
    private final String TAG = this.getClass().getSimpleName();

    private final int NUM_PAGES = 6;
    private final int DUPLICATE_PAGES = 2;
    private final int listSize = NUM_PAGES+2*DUPLICATE_PAGES; //10
    private Fragment[] fragments = new Fragment[listSize];
    private List<String> placeNames;
    private ViewPager viewPager;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        MyMapFragment frag = new MyMapFragment();
        Bundle args = new Bundle();

        args.putInt("name", getPageNumber(position));

        if(placeNames!=null){
            if(!placeNames.isEmpty()){
                args.putString("placeName", placeNames.get(position));
            }
        }

        frag.setArguments(args);
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


    public Fragment getExistingItem(int position){
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
//        MyCarouselItem item = (MyCarouselItem) getExistingItem(getPageNumber(position)).getView();
//        MyCarouselItem item2 = (MyCarouselItem) getExistingItem(getPageNumber(position)+1).getView();
//
//        if(item!=null){
//            item.setScale(1.3f-0.3f*positionOffset);
//            item2.setScale(1.0f+0.3f*positionOffset);
//        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(state==ViewPager.SCROLL_STATE_IDLE){
            Log.d(TAG, "idle hua");
            setCurrentItemWithDelay(viewPager.getCurrentItem());
        }
    }

    public void setViewPager(ViewPager vp){
        viewPager = vp;
    }

    private void setCurrentItemWithDelay(int position){
        if(position==1){
            viewPager.setCurrentItem(7, false);
        }else if(position==NUM_PAGES+DUPLICATE_PAGES){
            viewPager.setCurrentItem(2, false);
        }
    }
}
