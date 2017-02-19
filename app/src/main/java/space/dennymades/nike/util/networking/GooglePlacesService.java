package space.dennymades.nike.util.networking;

import org.reactivestreams.Subscriber;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import space.dennymades.nike.util.networking.datamodels.Result;
import space.dennymades.nike.util.networking.datamodels.ResultItem;

/**
 * Created by abrain on 2/18/17.
 */

public interface GooglePlacesService {
    @GET("maps/api/place/nearbysearch/json?location=40.7348671,-74.0579644&radius=2000&rankby=prominence&type=art_gallery&key=AIzaSyAENGgf-HPf4xJJjMVLeDpw9oJ4L2EiRiI")
    Observable<Result> getPlacesNearby();
}