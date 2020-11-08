package tech.sutd.pickupgame.ui.main.main.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tech.sutd.pickupgame.databinding.ItemlistActivitiesBinding;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;

public class UpcomingActivityAdapter<U> extends PagedListAdapter<UpcomingActivity, UpcomingActivityAdapter.ViewHolder> {

    private RequestManager requestManager;

    private int numOfViews = 0;

    public UpcomingActivityAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemlistActivitiesBinding binding = ItemlistActivitiesBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UpcomingActivity upcomingActivity = getItem(position);

        if (position > numOfViews) {
            holder.binding.cardView.setVisibility(View.GONE);
            return;
        }

        assert upcomingActivity != null;
        holder.binding.sport.setText(upcomingActivity.getSport());

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, ha", Locale.getDefault());
        String dateFormat = sdf.format(new Date(Long.parseLong(upcomingActivity.getClock())));

        SimpleDateFormat sdfEnd = new SimpleDateFormat("ha", Locale.getDefault());
        String dateEndFormat = sdfEnd.format(new Date(Long.parseLong(upcomingActivity.getEndClock())));

        String time = dateFormat + " - " + dateEndFormat;

        holder.binding.time.setText(time);
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

    public void setNotifications(RequestManager requestManager, int numOfViews) {
        this.requestManager = requestManager;
        this.numOfViews = numOfViews;
        notifyDataSetChanged();
    }

    private static final DiffUtil.ItemCallback<UpcomingActivity> DIFF_CALLBACK =  new DiffUtil.ItemCallback<UpcomingActivity>() {
        @Override
        public boolean areItemsTheSame(@NonNull UpcomingActivity oldItem, @NonNull UpcomingActivity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull UpcomingActivity oldItem, @NonNull UpcomingActivity newItem) {
            return oldItem.equals(newItem);
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemlistActivitiesBinding binding;

        public ViewHolder(@NonNull ItemlistActivitiesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
