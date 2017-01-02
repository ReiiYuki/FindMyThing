package ui.fragments;


import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;
import java.util.Observer;

import engines.LocationEngine;
import io.github.reiiyuki.findmything.R;
import io.github.reiiyuki.findmything.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements Observer{

    private FragmentHomeBinding binding;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        bindingToObserve();
        return binding.getRoot();
    }

    private void bindingToObserve(){
        LocationEngine.getInstance().addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof LocationEngine) {
            Location location = LocationEngine.getInstance().getCurrentLocation();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            binding.currentLocationText.setText(String.format("Latitude = %f, Lontitude = %f",latitude,longitude));
        }
    }
}
