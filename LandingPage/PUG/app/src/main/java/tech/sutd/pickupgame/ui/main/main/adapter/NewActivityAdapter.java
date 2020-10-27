package tech.sutd.pickupgame.ui.main.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech.sutd.pickupgame.databinding.ItemlistNewActivitiesBinding;
import tech.sutd.pickupgame.models.ui.NewActivity;

public class NewActivityAdapter extends RecyclerView.Adapter<NewActivityAdapter.ViewHolder> {

    private List<NewActivity> newActivities = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemlistNewActivitiesBinding binding = ItemlistNewActivitiesBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewActivity newActivity = newActivities.get(position);

        holder.binding.sport.setText(newActivity.getSport());
        holder.binding.time.setText(newActivity.getClock());
        holder.binding.location.setText(newActivity.getLocation());
        holder.binding.organizer.setText(newActivity.getOrganizer());

        holder.binding.clockImg.setImageResource(newActivity.getClockImg());
        holder.binding.locationImg.setImageResource(newActivity.getLocationImg());
        holder.binding.organizerImg.setImageResource(newActivity.getOrganizerImg());
        holder.binding.sportImg.setImageResource(newActivity.getSportImg());
    }

    @Override
    public int getItemCount() {
        return newActivities.size();
    }

    public void setNotifications(List<NewActivity> newActivities) {
        this.newActivities = newActivities;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemlistNewActivitiesBinding binding;

        public ViewHolder(@NonNull ItemlistNewActivitiesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
