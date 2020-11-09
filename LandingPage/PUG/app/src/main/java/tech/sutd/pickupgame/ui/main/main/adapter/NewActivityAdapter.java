package tech.sutd.pickupgame.ui.main.main.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.util.DateConverter;

public class NewActivityAdapter<N> extends PagedListAdapter<NewActivity, NewActivityAdapter.ViewHolder> {

    private RequestManager requestManager;

    private int numOfViews = 0;

    public NewActivityAdapter() {
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
        NewActivity newActivity = getItem(position);

        if (position > numOfViews) {
            holder.binding.cardView.setVisibility(View.GONE);
            return;
        }

        assert newActivity != null;
        holder.binding.sport.setText(newActivity.getSport());

        String dateFormat = DateConverter.clockConverter(newActivity.getClock());
        String dateEndFormat = DateConverter.endClockConverter(newActivity.getEndClock());

        String time = dateFormat + " - " + dateEndFormat;

        holder.binding.time.setText(time);
        holder.binding.location.setText(newActivity.getLocation());
        holder.binding.organizer.setText(newActivity.getOrganizer());

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

    private static final DiffUtil.ItemCallback<NewActivity> DIFF_CALLBACK = new DiffUtil.ItemCallback<NewActivity>() {
        @Override
        public boolean areItemsTheSame(@NonNull NewActivity oldItem, @NonNull NewActivity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull NewActivity oldItem, @NonNull NewActivity newItem) {
            return oldItem.equals(newItem);
        }
    };

    public void setNotifications(RequestManager requestManager, int numOfViews) {
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
