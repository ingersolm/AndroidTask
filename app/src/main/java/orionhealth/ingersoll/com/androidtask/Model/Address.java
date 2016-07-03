package orionhealth.ingersoll.com.androidtask.Model;

import java.io.Serializable;

/**
 * Created by mohan on 1/7/16.
 */
public class Address implements Serializable{
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
