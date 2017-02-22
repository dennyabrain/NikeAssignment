package space.dennymades.nike.util.GooglePlayService;

import android.location.Location;
import android.os.AsyncTask;
import android.view.View;

import space.dennymades.nike.BackgroundTasks;
import space.dennymades.nike.HomeActivity;
import space.dennymades.nike.views.AnimatedTextView;

/**
 * Created by abrain on 2/22/17.
 */

public class LocationBackgroundTask extends AsyncTask<GooglePlayHelper, Void, LocationHelper> {
    private AnimatedTextView mTextViewResultMessage;
    private BackgroundTasks mListener;

    public LocationBackgroundTask(BackgroundTasks listener, AnimatedTextView textView){
        mTextViewResultMessage = textView;
        mListener = listener;
    }

    @Override
    protected LocationHelper doInBackground(GooglePlayHelper... playHelpers) {
        Location mLocation = playHelpers[0].getLastLocation(playHelpers[0].getContext());
        mListener.onCompleted(mLocation);
        String mLocality = playHelpers[0].getLocality(playHelpers[0].getContext(), mLocation);

        return new LocationHelper(mLocation, mLocality);
    }

    @Override
    protected void onPostExecute(LocationHelper locationHelper) {
        super.onPostExecute(locationHelper);
        mTextViewResultMessage.setText("showing running tracks around "+locationHelper.getLocality()+"\n("+roundOff(locationHelper.getLocation().getLongitude())+","+roundOff(locationHelper.getLocation().getLatitude())+")");
        mTextViewResultMessage.setVisibility(View.VISIBLE);
        mTextViewResultMessage.showText();
    }

    private float roundOff(double val){
        return Math.round(val*100)/100;
    }
}
