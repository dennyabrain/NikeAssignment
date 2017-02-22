package space.dennymades.nike;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import space.dennymades.nike.util.AnimatedTextView;

/**
 * Created by abrain on 2/15/17.
 */

public class MyMapFragment extends Fragment {
    private int derivedPosition;
    private int actualPosition;
    private AnimatedTextView label;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        MyCarouselItem item = (MyCarouselItem)view.findViewById(R.id.root_layout);
        item.setTag("carouselItem"+actualPosition);
        //item.setTag();

        label = (AnimatedTextView) view.findViewById(R.id.textView);
        label.setText(derivedPosition + "Fragment "+derivedPosition);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        derivedPosition = getArguments().getInt("derivedPosition");
        actualPosition = getArguments().getInt("actualPosition");
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

    public String returnTag(){
        return getTag();
    }

}
