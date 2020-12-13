package tech.sutd.pickupgame.ui.main.main.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.databinding.ItemlistActivitiesBinding;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.ui.main.main.upcomingact.UpcomingActFragment;
import tech.sutd.pickupgame.ui.main.main.viewmodel.YourActViewModel;
import tech.sutd.pickupgame.util.DateConverter;
import tech.sutd.pickupgame.util.StringChecker;

public class YourActivityAdapter extends RecyclerView.Adapter<YourActivityAdapter.ViewHolder> {

    private final RequestManager requestManager;
    private final YourActViewModel viewModel;

    private Resource<List<YourActivity>> source;
    private UpcomingActFragment upcomingFragment;
    private Context context;

    private Dialog dialog;

    @Inject
    public YourActivityAdapter(RequestManager requestManager, YourActViewModel viewModel) {
        this.requestManager = requestManager;
        this.viewModel = viewModel;
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

        int numOfViews = 10;
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

        holder.binding.cardView.setOnClickListener(v -> {
            dialog = setDialog();

            MaterialTextView sportTv = dialog.findViewById(R.id.sport);
            sportTv.setText(yourActivity.getSport());

            MaterialTextView timeTv = dialog.findViewById(R.id.time);
            timeTv.setText(time);

            MaterialTextView locationTv = dialog.findViewById(R.id.location);
            locationTv.setText(yourActivity.getLocation());

            MaterialTextView organizerTv = dialog.findViewById(R.id.organizer);
            organizerTv.setText(yourActivity.getOrganizer());

            MaterialTextView participantTv = dialog.findViewById(R.id.participant);
            participantTv.setText(StringChecker.partHolder(yourActivity.getCount(), yourActivity.getParticipant()));

            MaterialTextView notesTv = dialog.findViewById(R.id.notes);
            notesTv.setText(yourActivity.getNotes());

            requestManager.load(yourActivity.getClockImg())
                    .into((ImageView) dialog.findViewById(R.id.clock_img));
            requestManager.load(yourActivity.getLocationImg())
                    .into((ImageView) dialog.findViewById(R.id.location_img));
            requestManager.load(yourActivity.getOrganizerImg())
                    .into((ImageView) dialog.findViewById(R.id.organizer_img));
            requestManager.load(yourActivity.getSportImg())
                    .into((ImageView) dialog.findViewById(R.id.sport_img));
            requestManager.load(yourActivity.getParticipantImg())
                    .into((ImageView) dialog.findViewById(R.id.participant_img));
            requestManager.load(yourActivity.getNotesImg())
                    .into((ImageView) dialog.findViewById(R.id.notes_img));

            dialog.findViewById(R.id.delete).setOnClickListener(view -> {

                if (upcomingFragment != null) {
                    viewModel.deleteFromDb(upcomingFragment, this, yourActivity.getId());
                }
            });
        });
    }

    public Dialog getDialog() {
        return dialog;
    }

    private Dialog setDialog() {
        Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.itemlist_upcoming_activities);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        return dialog;
    }

    public void setNotification(Context context, UpcomingActFragment upcomingFragment) {
        this.context = context;
        this.upcomingFragment = upcomingFragment;
    }

    @Override
    public int getItemCount() {
        if (source == null || source.data == null)
            return 0;
        return source.data.size();
    }

    public void setSource(List<YourActivity> data) {
        source = Resource.success(data);
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
