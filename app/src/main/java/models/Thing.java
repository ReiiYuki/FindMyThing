package models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yukir on 1/3/2017.
 */

public class Thing extends RealmObject{
    @PrimaryKey
    private long id;
    private String name;
    private double latitude;
    private double longitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
