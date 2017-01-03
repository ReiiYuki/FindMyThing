package adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.github.reiiyuki.findmything.R;
import io.github.reiiyuki.findmything.databinding.CardThingBinding;
import io.realm.RealmResults;
import models.Thing;
import viewholders.ThingViewHolder;

/**
 * Created by yukir on 1/3/2017.
 */

public class ThingListAdapter extends RecyclerView.Adapter<ThingViewHolder>{

    RealmResults<Thing> things;

    public ThingListAdapter(RealmResults<Thing> things){
        this.things = things;
    }

    @Override
    public ThingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardThingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_thing,parent,false);
        return new ThingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ThingViewHolder holder, int position) {
        holder.getBinding().thingNameText.setText(things.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return things.size();
    }
}
