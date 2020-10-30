package tech.sutd.pickupgame.ui.main.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import tech.sutd.pickupgame.databinding.ItemlistActivitiesBinding;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;

public class UpcomingActivityAdapter extends RecyclerView.Adapter<UpcomingActivityAdapter.ViewHolder> {

    private List<UpcomingActivity> upcomingActivities = new ArrayList<>();
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
        UpcomingActivity upcomingActivity = upcomingActivities.get(position);

        // Do not populate beyond numOfViews
        if (position > numOfViews) {
            holder.binding.cardView.setVisibility(View.GONE);
            return;
        }

        holder.binding.sport.setText(upcomingActivity.getSport());
        holder.binding.time.setText(upcomingActivity.getClock());
        holder.binding.location.setText(upcomingActivity.getLocation());
        holder.binding.organizer.setText(upcomingActivity.getOrganizer());

        requestManager.load(upcomingActivity.getClockImg())
                .into(holder.binding.clockImg);
        requestManager.load(upcomingActivity.getLocationImg())
                .into(holder.binding.locationImg);
        requestManager.load(upcomingActivity.getOrganizerImg())
                .into(holder.binding.organizerImg);
        requestManager.load(upcomingActivity.getSportImg())
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
        return upcomingActivities.size();
    }

    public void setNotifications(List<UpcomingActivity> upcomingActivities, RequestManager requestManager, int numOfViews) {
        this.upcomingActivities = upcomingActivities;
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
