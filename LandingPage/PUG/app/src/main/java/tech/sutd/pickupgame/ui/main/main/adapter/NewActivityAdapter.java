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
import tech.sutd.pickupgame.models.ui.NewActivity;

public class NewActivityAdapter extends RecyclerView.Adapter<NewActivityAdapter.ViewHolder> {

    private List<NewActivity> newActivities = new ArrayList<>();
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
        NewActivity newActivity = newActivities.get(position);

        if (position > numOfViews) {
            holder.binding.cardView.setVisibility(View.GONE);
            return;
        }

        holder.binding.sport.setText(newActivity.getSport());
        holder.binding.time.setText(newActivity.getClock());
        holder.binding.location.setText(newActivity.getLocation());
        holder.binding.organizer.setText(newActivity.getOrganizer());

        holder.binding.clockImg.setImageResource(newActivity.getClockImg());
        holder.binding.locationImg.setImageResource(newActivity.getLocationImg());
        holder.binding.organizerImg.setImageResource(newActivity.getOrganizerImg());
        holder.binding.sportImg.setImageResource(newActivity.getSportImg());

        requestManager.load(newActivity.getClockImg())
                .into(holder.binding.clockImg);
        requestManager.load(newActivity.getLocationImg())
                .into(holder.binding.locationImg);
        requestManager.load(newActivity.getOrganizerImg())
                .into(holder.binding.organizerImg);
        requestManager.load(newActivity.getSportImg())
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
        return newActivities.size();
    }

    public void setNotifications(List<NewActivity> newActivities, RequestManager requestManager, int numOfViews) {
        this.newActivities = newActivities;
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
