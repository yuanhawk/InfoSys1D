package tech.sutd.pickupgame.ui.main.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import tech.sutd.pickupgame.data.AuthResource;
import tech.sutd.pickupgame.databinding.ItemlistActivitiesBinding;
import tech.sutd.pickupgame.models.ui.PastActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.util.DateConverter;

public class YourActivityAdapter extends RecyclerView.Adapter<YourActivityAdapter.ViewHolder> {

    private final RequestManager requestManager;

    private int numOfViews = 10;

    private AuthResource<List<YourActivity>> source;

    @Inject
    public YourActivityAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
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
        if (source.data == null || source.data.isEmpty())
            return;

        YourActivity yourActivity = source.data.get(position);

        if (position > numOfViews) {
            holder.binding.cardView.setVisibility(View.GONE);
            return;
        }

        holder.binding.sport.setText(yourActivity.getSport());

        String dateFormat = DateConverter.clockConverter(yourActivity.getClock());
        String dateEndFormat = DateConverter.endClockConverter(yourActivity.getEndClock());

        String time = dateFormat + " - " + dateEndFormat;

        holder.binding.time.setText(time);
        holder.binding.location.setText(yourActivity.getLocation());
        holder.binding.organizer.setText(yourActivity.getOrganizer());

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
        if (source == null || source.data == null)
            return new ArrayList<>().size();
        return source.data.size();
    }

    public void setEmptySource() {
        this.source = AuthResource.loading(new ArrayList<>());
        notifyDataSetChanged();
    }

    public void setSource(List<YourActivity> data) {
        this.source = AuthResource.success(data);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemlistActivitiesBinding binding;

        public ViewHolder(@NonNull ItemlistActivitiesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
