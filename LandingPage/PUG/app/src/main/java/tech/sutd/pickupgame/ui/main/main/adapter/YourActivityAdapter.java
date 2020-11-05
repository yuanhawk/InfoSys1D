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
import tech.sutd.pickupgame.models.ui.YourActivity;

public class YourActivityAdapter extends RecyclerView.Adapter<YourActivityAdapter.ViewHolder> {

    private List<YourActivity> yourActivities = new ArrayList<>();
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
        YourActivity yourActivity = yourActivities.get(position);

        if (position > numOfViews) {
            holder.binding.cardView.setVisibility(View.GONE);
            return;
        }

        holder.binding.sport.setText(yourActivity.getSport());
        holder.binding.time.setText(yourActivity.getClock());
        holder.binding.location.setText(yourActivity.getLocation());
        holder.binding.organizer.setText(yourActivity.getOrganizer());

        holder.binding.clockImg.setImageResource(yourActivity.getClockImg());
        holder.binding.locationImg.setImageResource(yourActivity.getLocationImg());
        holder.binding.organizerImg.setImageResource(yourActivity.getOrganizerImg());
        holder.binding.sportImg.setImageResource(yourActivity.getSportImg());

        requestManager.load(yourActivity.getClockImg())
                .into(holder.binding.clockImg);
        requestManager.load(yourActivity.getLocationImg())
                .into(holder.binding.locationImg);
        requestManager.load(yourActivity.getOrganizerImg())
                .into(holder.binding.organizerImg);
        requestManager.load(yourActivity.getSportImg())
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
        return yourActivities.size();
    }

    public void setNotifications(List<YourActivity> yourActivities, RequestManager requestManager, int numOfViews) {
        this.yourActivities = yourActivities;
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
