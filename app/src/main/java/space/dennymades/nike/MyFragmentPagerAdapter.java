package space.dennymades.nike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by abrain on 2/15/17.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int NUM_PAGES = 7;
    private Fragment[] fragments = new Fragment[NUM_PAGES+2];
    private List<String> placeNames;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        MyMapFragment frag = new MyMapFragment();
        Bundle args = new Bundle();
        if(position==0){
            args.putInt("name", 7);
        }else if(position==8){
            args.putInt("name", 1);
        }else{
            args.putInt("name", position);
        }
        //args.putInt("name", position);

        if(placeNames!=null){
            if(!placeNames.isEmpty()){
                args.putString("placeName", placeNames.get(position));
            }
        }

        frag.setArguments(args);
        fragments[position] = frag;
        return frag;
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
        return NUM_PAGES+2;
    }


    @Override
    public float getPageWidth(int position) {
        return 0.75f;
        //return 1.0f;
    }

    public void setPlaces(List<String> names){
        placeNames = names;
    }

    public void updateText(int position){

    }
}
