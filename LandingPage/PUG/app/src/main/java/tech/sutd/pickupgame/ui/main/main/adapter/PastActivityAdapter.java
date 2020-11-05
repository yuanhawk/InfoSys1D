package tech.sutd.pickupgame.ui.main.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import tech.sutd.pickupgame.databinding.ItemlistActivitiesBinding;
import tech.sutd.pickupgame.models.ui.PastActivity;

public class PastActivityAdapter extends RecyclerView.Adapter<PastActivityAdapter.ViewHolder> {

    private List<PastActivity> pastActivities = new ArrayList<>();
    private RequestManager requestManager;

    private int numOfViews = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemlistActivitiesBinding binding = ItemlistActivitiesBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PastActivity pastActivity = pastActivities.get(position);

        if (position > numOfViews) {
            holder.binding.cardView.setVisibility(View.GONE);
            return;
        }

        holder.binding.sport.setText(pastActivity.getSport());
        holder.binding.time.setText(pastActivity.getClock());
        holder.binding.location.setText(pastActivity.getLocation());
        holder.binding.organizer.setText(pastActivity.getOrganizer());

        holder.binding.clockImg.setImageResource(pastActivity.getClockImg());
        holder.binding.locationImg.setImageResource(pastActivity.getLocationImg());
        holder.binding.organizerImg.setImageResource(pastActivity.getOrganizerImg());
        holder.binding.sportImg.setImageResource(pastActivity.getSportImg());

        requestManager.load(pastActivity.getClockImg())
                .into(holder.binding.clockImg);
        requestManager.load(pastActivity.getLocationImg())
                .into(holder.binding.locationImg);
        requestManager.load(pastActivity.getOrganizerImg())
                .into(holder.binding.organizerImg);
        requestManager.load(pastActivity.getSportImg())
                .into(holder.binding.sportImg);

        // add margins to left, right & bottom if lesser numOfViews
        if (position < numOfViews) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.binding.layout.getLayoutParams();
            lp.setMargins(20, 0, 20, 20);
        }

        // add margins to left & right if numOfViews reached
        if (position == numOfViews) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.binding.layout.getLayoutParams();
            lp.setMargins(20, 0, 20, 0);
        }
    }

    @Override
    public int getItemCount() {
        return pastActivities.size();
    }

    public void setNotifications(List<PastActivity> pastActivities, RequestManager requestManager, int numOfViews) {
        this.pastActivities = pastActivities;
        this.requestManager = requestManager;
        this.numOfViews = numOfViews;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemlistActivitiesBinding binding;

        public ViewHolder(@NonNull ItemlistActivitiesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
