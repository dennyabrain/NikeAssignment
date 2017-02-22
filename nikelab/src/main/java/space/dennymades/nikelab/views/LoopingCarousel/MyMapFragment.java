package space.dennymades.nikelab.views.LoopingCarousel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import space.dennymades.nikelab.R;
import space.dennymades.nikelab.views.AnimatedTextView;
import space.dennymades.nikelab.views.LoopingCarousel.MyCarouselItem;

/**
 * Created by abrain on 2/15/17.
 */

public class MyMapFragment extends Fragment {
    private int derivedPosition;
    private int actualPosition;
    private AnimatedTextView label;
    private String placeName;
    private ProgressBar progressBar;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        MyCarouselItem item = (MyCarouselItem)view.findViewById(R.id.root_layout);
        item.setTag("carouselItem"+actualPosition);

        label = (AnimatedTextView) view.findViewById(R.id.textView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        //label.setText(derivedPosition + "Fragment "+derivedPosition);
        //label.setText(placeName);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        derivedPosition = getArguments().getInt("derivedPosition");
        actualPosition = getArguments().getInt("actualPosition");
        placeName = getArguments().getString("placeName");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void updatePlace(String place){
        TextView label = (TextView) this.getView().findViewById(R.id.textView);
        label.setText(place);
    }

    public void showText(){
        label.showText();
    }

    public void hideText(){
        label.hideText();
    }

    public boolean isAlphaOne(){
        return label.isVisible();
    }

    public void makeInvisible(){
        label.setAlpha(0);
    }

    public String returnTag(){
        return getTag();
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }
}
