package ui.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import adapters.ThingListAdapter;
import engines.LocationEngine;
import engines.StorageEngine;
import io.github.reiiyuki.findmything.R;
import io.github.reiiyuki.findmything.databinding.DialogAddThingBinding;
import io.github.reiiyuki.findmything.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements Observer{

    private FragmentHomeBinding binding;
    private ThingListAdapter adapter;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initDataBinding(inflater,container);
        setupRecyclerView();
        bindingToObserve();
        addListener();
        return binding.getRoot();
    }

    private void initDataBinding(LayoutInflater inflater, ViewGroup container){
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
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
            binding.currentLocationText.setText(String.format("(%f,%f)",latitude,longitude));
            adapter.notifyDataSetChanged();
        }
    }

    private void addListener(){
        binding.addThingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddThingDialogBox();
            }
        });
    }

    private void showAddThingDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final DialogAddThingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.dialog_add_thing,null,false);
        builder.setView(binding.getRoot())
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            })
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = binding.thingNameText.getText().toString();
                    StorageEngine.getInstance().addThing(name);
                    Toast.makeText(getContext(),"You have "+StorageEngine.getInstance().getThings().last().getName(),Toast.LENGTH_LONG).show();
                }
            });
        builder.create().show();
    }
    private void setupRecyclerView(){
        binding.listThing.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ThingListAdapter(getActivity(),StorageEngine.getInstance().getThings());
        binding.listThing.setAdapter(adapter);
    }
}
