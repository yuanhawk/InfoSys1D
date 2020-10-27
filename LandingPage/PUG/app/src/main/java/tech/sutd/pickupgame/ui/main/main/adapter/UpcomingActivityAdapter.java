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

public class UpcomingActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int LAYOUT_ONE = 0;
    public static final int LAYOUT_TWO = 1;

    private List upcomingActivities = new ArrayList();
    private RequestManager requestManager;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (getItemViewType(viewType)) {
            case LAYOUT_ONE:
                ItemlistUpcomingActivitiesBinding upcomingBind = ItemlistUpcomingActivitiesBinding.inflate(inflater, parent, false);
                return new UpcomingActViewHolder(upcomingBind);
            case LAYOUT_TWO:
                ItemlistFriendsBinding friendsBinding = ItemlistFriendsBinding.inflate(inflater, parent, false);
                return new FriendsViewHolder(friendsBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case LAYOUT_ONE:
                UpcomingActivity upcomingActivities = (UpcomingActivity) this.upcomingActivities.get(position);
                UpcomingActViewHolder holder1 = (UpcomingActViewHolder) holder;

                holder1.binding.sport.setText(upcomingActivities.getSport());
                holder1.binding.time.setText(upcomingActivities.getClock());
                holder1.binding.location.setText(upcomingActivities.getLocation());
                holder1.binding.organizer.setText(upcomingActivities.getOrganizer());

                requestManager.load(upcomingActivities.getClockImg())
                        .into(holder1.binding.clockImg);
                requestManager.load(upcomingActivities.getLocationImg())
                        .into(holder1.binding.locationImg);
                requestManager.load(upcomingActivities.getOrganizerImg())
                        .into(holder1.binding.organizerImg);
                requestManager.load(upcomingActivities.getSportImg())
                        .into(holder1.binding.sportImg);
                break;
            case LAYOUT_TWO:
                Friends newActivities = (Friends) this.upcomingActivities.get(position);
                ((FriendsViewHolder) holder).binding.friends.setText(newActivities.getName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return upcomingActivities.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (UpcomingActivity.class.isInstance(upcomingActivities.get(position)))
            return 0;
        return 1;
    }

    public void setNotifications(List<UpcomingActivity> upcomingActivities, RequestManager requestManager) {
        this.upcomingActivities = upcomingActivities;
        this.requestManager = requestManager;
        notifyDataSetChanged();
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        private ItemlistFriendsBinding binding;

        public FriendsViewHolder(@NonNull ItemlistFriendsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class UpcomingActViewHolder extends RecyclerView.ViewHolder {

        private ItemlistUpcomingActivitiesBinding binding;

        public UpcomingActViewHolder(@NonNull ItemlistUpcomingActivitiesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
