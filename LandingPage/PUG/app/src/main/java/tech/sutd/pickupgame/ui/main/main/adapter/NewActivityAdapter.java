package tech.sutd.pickupgame.ui.main.main.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.ItemlistActivitiesBinding;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.util.DateConverter;

public class NewActivityAdapter<N> extends PagedListAdapter<NewActivity, NewActivityAdapter.ViewHolder> {

    private int numOfViews = 0;

    private RequestManager requestManager;
    private Context context;

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

        holder.binding.cardView.setOnClickListener(v -> {
            Dialog dialog = setDialog(R.layout.expanded_itemlist_activities);

            MaterialTextView sportTv = dialog.findViewById(R.id.sport);
            sportTv.setText(newActivity.getSport());

            MaterialTextView timeTv = dialog.findViewById(R.id.time);
            timeTv.setText(time);

            MaterialTextView locationTv = dialog.findViewById(R.id.location);
            locationTv.setText(newActivity.getLocation());

            MaterialTextView organizerTv = dialog.findViewById(R.id.organizer);
            organizerTv.setText(newActivity.getOrganizer());

            MaterialTextView participantTv = dialog.findViewById(R.id.participant);
            participantTv.setText(newActivity.getParticipant());

            MaterialTextView notesTv = dialog.findViewById(R.id.notes);
            notesTv.setText(newActivity.getNotes());

            requestManager.load(newActivity.getClockImg())
                    .into((ImageView) dialog.findViewById(R.id.clock_img));
            requestManager.load(newActivity.getLocationImg())
                    .into((ImageView) dialog.findViewById(R.id.location_img));
            requestManager.load(newActivity.getOrganizerImg())
                    .into((ImageView) dialog.findViewById(R.id.organizer_img));
            requestManager.load(newActivity.getSportImg())
                    .into((ImageView) dialog.findViewById(R.id.sport_img));
            requestManager.load(newActivity.getParticipantImg())
                    .into((ImageView) dialog.findViewById(R.id.participant_img));
            requestManager.load(newActivity.getNotesImg())
                    .into((ImageView) dialog.findViewById(R.id.notes_img));

            dialog.findViewById(R.id.confirm_button).setOnClickListener(view -> dialog.dismiss());
        });

    }

    private Dialog setDialog(int layout) {
        Dialog dialog = new Dialog(context);

        dialog.setContentView(layout);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        return dialog;
    }

    private static final DiffUtil.ItemCallback<NewActivity> DIFF_CALLBACK = new DiffUtil.ItemCallback<NewActivity>() {
        @Override
        public boolean areItemsTheSame(@NonNull NewActivity oldItem, @NonNull NewActivity newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull NewActivity oldItem, @NonNull NewActivity newItem) {
            return oldItem.equals(newItem);
        }
    };

    public void setNotifications(Context context, RequestManager requestManager, int numOfViews) {
        this.context = context;
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
