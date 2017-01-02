package ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import engines.LocationEngine;
import io.github.reiiyuki.findmything.R;
import io.github.reiiyuki.findmything.databinding.ActivityMainBinding;
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

}
