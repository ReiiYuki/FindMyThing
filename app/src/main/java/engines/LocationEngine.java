package engines;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Observable;

import models.Thing;

/**
 * Created by yukir on 1/3/2017.
 */

public class LocationEngine extends Observable implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private Location currentLocation;
    private GoogleApiClient apiClient;
    private Activity activity;
    private static LocationEngine locationEngine;
    private final double RADIUS_OF_EARTH = 6371;

    private LocationEngine(){}

    public static LocationEngine getInstance(){
        if (locationEngine==null) locationEngine = new LocationEngine();
        return  locationEngine;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void buildApi(){
        apiClient = new GoogleApiClient.Builder(activity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(activity, "Location Service is connected!", Toast.LENGTH_SHORT).show();
        startLocationAvailability();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(activity, "Location Service is suspended!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(activity, "Location Service is failed!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        setChanged();
        notifyObservers();
    }

    public Location getCurrentLocation(){
        return currentLocation;
    }

    public void connect(){
        if (apiClient!=null){
            apiClient.connect();
        }
    }

    public void disconnect(){
        if (apiClient!=null&&apiClient.isConnected()){
            apiClient.disconnect();
        }
    }

    private void startLocationAvailability(){
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    123);
            return;
        }
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(apiClient);
        if (locationAvailability.isLocationAvailable()) {
            LocationRequest locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000);
            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locationRequest, this);
        } else {
            Toast.makeText(activity, "Location Service is not available!", Toast.LENGTH_LONG).show();
        }
    }

    public double calculateDistance(Thing thing){
        Location location = getCurrentLocation();
        if (location==null) return -99;
        double deltaLatitude = Math.abs(Math.toRadians(location.getLatitude()-thing.getLatitude()));
        double deltaLongitude = Math.abs(Math.toRadians(location.getLongitude()-thing.getLongitude()));
        double a = Math.pow(Math.sin(deltaLatitude/2),2)+Math.cos(Math.toRadians(location.getLatitude())*Math.cos(Math.toRadians(thing.getLatitude())))*Math.pow(Math.sin(deltaLongitude/2),2);
        double c = 2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        double d = RADIUS_OF_EARTH*c;
        return d*100;
    }
}
