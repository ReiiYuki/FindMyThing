package engines;

/**
 * Created by yukir on 1/3/2017.
 */

public class StorageEngine {

    private StorageEngine engine;

    private StorageEngine(){

    }

    public StorageEngine getInstance(){
        if (engine==null) engine = new StorageEngine();
        return  engine;
    }
}
