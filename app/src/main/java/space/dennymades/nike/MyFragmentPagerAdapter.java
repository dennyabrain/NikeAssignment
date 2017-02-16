package space.dennymades.nike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by abrain on 2/15/17.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int NUM_PAGES = 8;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        MyMapFragment frag = new MyMapFragment();
        Bundle args = new Bundle();
        args.putInt("name", position);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public int getCount() {
        return NUM_PAGES+2;
    }


}
