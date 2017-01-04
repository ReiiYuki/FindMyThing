package adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import engines.LocationEngine;
import engines.StorageEngine;
import io.github.reiiyuki.findmything.R;
import io.github.reiiyuki.findmything.databinding.CardThingBinding;
import io.github.reiiyuki.findmything.databinding.DialogAddThingBinding;
import io.realm.RealmResults;
import models.Thing;
import viewholders.ThingViewHolder;

/**
 * Created by yukir on 1/3/2017.
 */

public class ThingListAdapter extends RecyclerView.Adapter<ThingViewHolder>{

    private RealmResults<Thing> things;
    private Activity activity;
    private final String[] OPTIONS = {"Change Thing's name","Update location to current location","Delete Thing"};

    public ThingListAdapter(Activity activity,RealmResults<Thing> things){
        this.activity = activity;
        this.things = things;
    }

    @Override
    public ThingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardThingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_thing,parent,false);
        return new ThingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ThingViewHolder holder, final int position) {
        Thing thing = things.get(position);
        holder.getBinding().thingNameText.setText(thing.getName());
        holder.getBinding().distanceText.setText("Less than "+Math.round(LocationEngine.getInstance().calculateDistance(thing))+" m");
        holder.getBinding().currentLocationText.setText("("+thing.getLatitude()+","+thing.getLongitude()+")");
        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionBox(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return things.size();
    }

    public void showOptionBox(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(things.get(position).getName())
                .setItems(OPTIONS, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0) {
                            showChangeNameBox(position);
                            dialog.cancel();
                        }else if (which==1){

                        }else if (which==2){
                            StorageEngine.getInstance().deleteThing(position);
                        }
                    }
                });
        builder.create().show();
    }

    public void showChangeNameBox(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final DialogAddThingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity),R.layout.dialog_add_thing,null,false);
        binding.thingNameText.setText(StorageEngine.getInstance().getThings().get(position).getName());
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
                        StorageEngine.getInstance().editThingName(position,name);
                    }
                });
        builder.create().show();
    }
}
