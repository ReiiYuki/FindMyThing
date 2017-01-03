package viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.reiiyuki.findmything.databinding.CardThingBinding;

/**
 * Created by yukir on 1/3/2017.
 */

public class ThingViewHolder extends RecyclerView.ViewHolder {
    private CardThingBinding binding;
    public ThingViewHolder(CardThingBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public CardThingBinding getBinding() {
        return binding;
    }
}
