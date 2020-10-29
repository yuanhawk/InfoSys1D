package tech.sutd.pickupgame.ui.main.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import tech.sutd.pickupgame.databinding.ItemlistFriendsBinding;
import tech.sutd.pickupgame.databinding.ItemlistUpcomingActivitiesBinding;
import tech.sutd.pickupgame.models.ui.Friends;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;

public class UpcomingActivityAdapter extends RecyclerView.Adapter<UpcomingActivityAdapter.ViewHolder> {

    private List<UpcomingActivity> upcomingActivities = new ArrayList<>();
    private RequestManager requestManager;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemlistUpcomingActivitiesBinding binding = ItemlistUpcomingActivitiesBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UpcomingActivity upcomingActivity = upcomingActivities.get(position);

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
    }

    @Override
    public int getItemCount() {
        return upcomingActivities.size();
    }

    public void setNotifications(List<UpcomingActivity> upcomingActivities, RequestManager requestManager) {
        this.upcomingActivities = upcomingActivities;
        this.requestManager = requestManager;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemlistUpcomingActivitiesBinding binding;

        public ViewHolder(@NonNull ItemlistUpcomingActivitiesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
