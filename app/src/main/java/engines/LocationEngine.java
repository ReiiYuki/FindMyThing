package engines;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by yukir on 1/3/2017.
 */

public class LocationEngine implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private Location currentLocation;
    private GoogleApiClient apiClient;
    private Activity activity;
    private LocationEngine locationEngine;

    private LocationEngine(){}

    public LocationEngine getInstance(){
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
}
