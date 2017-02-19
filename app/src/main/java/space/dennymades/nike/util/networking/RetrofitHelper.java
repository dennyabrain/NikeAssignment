package space.dennymades.nike.util.networking;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import space.dennymades.nike.util.networking.datamodels.Result;

/**
 * Created by abrain on 2/18/17.
 */

public class RetrofitHelper implements Subscriber<Result> {
    private Retrofit retrofit;

    private GooglePlacesService mPlayService;

    //Non Instantiability Helper
    public RetrofitHelper(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mPlayService = retrofit.create(GooglePlacesService.class);
    }

    public GooglePlacesService getPlacesService(){
        return mPlayService;
    }


    @Override
    public void onSubscribe(Subscription s) {

    }


    @Override
    public void onNext(Result result) {

    }


    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
