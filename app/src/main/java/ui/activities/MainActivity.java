package ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import engines.LocationEngine;
import io.github.reiiyuki.findmything.R;
import io.github.reiiyuki.findmything.databinding.ActivityMainBinding;
import io.realm.Realm;
import ui.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;
    private Fragment homeFragment;
    private Intent intent;
    private FragmentTransaction transaction;
    private LocationEngine locationEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initRealm();
        setupLocationEngine();
        initIntent();
        initFragment();
        initTransaction();
        beginHome();
    }

    private void initDataBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    private void initIntent(){
        intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
    }

    private void initFragment(){
        homeFragment = new HomeFragment();
    }

    private void initTransaction(){
        transaction = getSupportFragmentManager().beginTransaction();
    }

    private void beginHome(){
        transaction.replace(R.id.fragment_frame,homeFragment);
        transaction.commit();
    }

    private void setupLocationEngine(){
        locationEngine = LocationEngine.getInstance();
        locationEngine.setActivity(this);
        locationEngine.buildApi();
    }

    private void initRealm(){
        Realm.init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationEngine.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationEngine.disconnect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 123: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationEngine.getInstance().startLocationAvailability();

                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
