package models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yukir on 1/3/2017.
 */

public class Thing extends RealmObject{
    @PrimaryKey
    private int id;
    private String name;
    private String description;
    private float latitude;
    private float longitude;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
