package engines;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by yukir on 1/3/2017.
 */

public class LocationEngine {
    private float latitude;
    private float longitude;
    private GoogleApiClient apiClient;
    private LocationEngine locationEngine;

    private LocationEngine(){
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_CODE_ASK_PERMISSIONS);
//            return;
//        }
    }

    public LocationEngine getInstance(){
        if (locationEngine==null) locationEngine = new LocationEngine();
        return  locationEngine;
    }

}
