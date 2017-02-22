package space.dennymades.nikelab.util.networking.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abrain on 2/18/17.
 */

public class Result {
    @SerializedName("results")
    @Expose
    private List<ResultItem> item;

    public List<ResultItem> getResult(){
        return item;
    }
}
