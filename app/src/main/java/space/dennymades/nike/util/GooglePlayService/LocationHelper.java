package space.dennymades.nike.util.GooglePlayService;

import android.location.Location;

/**
 * Created by abrain on 2/22/17.
 */

public class LocationHelper {
    private Location mLocation;
    private String mLocality;

    public LocationHelper(Location location, String locality){
        mLocality = locality;
        mLocation = location;
    }

    public Location getLocation(){
        return mLocation;
    }

    public String getLocality(){
        return mLocality;
    }
}
