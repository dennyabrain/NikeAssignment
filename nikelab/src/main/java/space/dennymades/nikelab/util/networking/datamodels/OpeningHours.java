package space.dennymades.nikelab.util.networking.datamodels;

/**
 * Created by abrain on 2/18/17.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpeningHours {

    @SerializedName("exceptional_date")
    @Expose
    private List<Object> exceptionalDate = null;
    @SerializedName("open_now")
    @Expose
    private Boolean openNow;
    @SerializedName("weekday_text")
    @Expose
    private List<Object> weekdayText = null;

    public List<Object> getExceptionalDate() {
        return exceptionalDate;
    }

    public void setExceptionalDate(List<Object> exceptionalDate) {
        this.exceptionalDate = exceptionalDate;
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public List<Object> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<Object> weekdayText) {
        this.weekdayText = weekdayText;
    }

}
