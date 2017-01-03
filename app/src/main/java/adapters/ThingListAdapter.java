package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import io.realm.RealmResults;
import models.Thing;

/**
 * Created by yukir on 1/3/2017.
 */

public class ThingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    RealmResults<Thing> things;

    public ThingListAdapter(RealmResults<Thing> things){
        this.things = things;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
