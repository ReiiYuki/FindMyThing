package engines;

import android.location.Location;

import io.realm.Realm;
import io.realm.RealmResults;
import models.Thing;

/**
 * Created by yukir on 1/3/2017.
 */

public class StorageEngine {

    private StorageEngine engine;
    private Realm realm;

    private StorageEngine(){

    }

    public StorageEngine getInstance(){
        if (engine==null) engine = new StorageEngine();
        return  engine;
    }

    public void addThing(final String name){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Thing thing = realm.createObject(Thing.class);
                Location location = LocationEngine.getInstance().getCurrentLocation();
                thing.setName(name);
                thing.setLatitude(location.getLatitude());
                thing.setLongitude(location.getLongitude());
                thing.setId(getThings().size()+System.currentTimeMillis());
            }
        });
    }

    public RealmResults<Thing> getThings(){
        return realm.where(Thing.class).findAll();
    }
}
