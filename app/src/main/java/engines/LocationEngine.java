package engines;

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

    }

    public LocationEngine getInstance(){
        if (locationEngine==null) locationEngine = new LocationEngine();
        return  locationEngine;
    }

}
