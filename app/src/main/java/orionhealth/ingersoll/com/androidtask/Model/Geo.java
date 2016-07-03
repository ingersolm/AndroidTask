package orionhealth.ingersoll.com.androidtask.Model;

import java.io.Serializable;

/**
 * Created by mohan on 1/7/16.
 */
public class Geo implements Serializable{

    private String lat;
    private String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
