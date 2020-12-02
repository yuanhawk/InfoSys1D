package tech.sutd.pickupgame.ui.main.main.adapter;

import android.annotation.SuppressLint;
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
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.google.android.material.textview.MaterialTextView;

import javax.inject.Inject;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.ItemlistActivitiesBinding;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.ui.main.main.MainFragment;
import tech.sutd.pickupgame.ui.main.main.upcomingact.UpcomingActFragment;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;
import tech.sutd.pickupgame.util.DateConverter;
import tech.sutd.pickupgame.util.StringChecker;

public class UpcomingActivityAdapter<U> extends PagedListAdapter<UpcomingActivity, UpcomingActivityAdapter.ViewHolder> {

    private final RequestManager requestManager;
    private final UpcomingActViewModel viewModel;

    private MainFragment mainFragment;
    private UpcomingActFragment upcomingFragment;

    private Dialog dialog;

    private Context context;
    private int numOfViews = 0;

    @Inject
    public UpcomingActivityAdapter(RequestManager requestManager, UpcomingActViewModel viewModel) {
        super(DIFF_CALLBACK);
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
        UpcomingActivity upcomingActivity = getItem(position);

        if (position > numOfViews) {
            holder.binding.cardView.setVisibility(View.GONE);
            return;
        }

        assert upcomingActivity != null;
        holder.binding.sport.setText(upcomingActivity.getSport());

        String dateFormat = DateConverter.clockConverter(upcomingActivity.getClock());
        String dateEndFormat = DateConverter.endClockConverter(upcomingActivity.getEndClock());

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

        holder.binding.cardView.setOnClickListener(v -> {
            dialog = setDialog();

            MaterialTextView sportTv = dialog.findViewById(R.id.sport);
            sportTv.setText(upcomingActivity.getSport());

            MaterialTextView timeTv = dialog.findViewById(R.id.time);
            timeTv.setText(time);

            MaterialTextView locationTv = dialog.findViewById(R.id.location);
            locationTv.setText(upcomingActivity.getLocation());

            MaterialTextView organizerTv = dialog.findViewById(R.id.organizer);
            organizerTv.setText(upcomingActivity.getOrganizer());

            MaterialTextView participantTv = dialog.findViewById(R.id.participant);
            participantTv.setText(StringChecker.partHolder(upcomingActivity.getCount(), upcomingActivity.getParticipant()));

            MaterialTextView notesTv = dialog.findViewById(R.id.notes);
            notesTv.setText(upcomingActivity.getNotes());

            requestManager.load(upcomingActivity.getClockImg())
                    .into((ImageView) dialog.findViewById(R.id.clock_img));
            requestManager.load(upcomingActivity.getLocationImg())
                    .into((ImageView) dialog.findViewById(R.id.location_img));
            requestManager.load(upcomingActivity.getOrganizerImg())
                    .into((ImageView) dialog.findViewById(R.id.organizer_img));
            requestManager.load(upcomingActivity.getSportImg())
                    .into((ImageView) dialog.findViewById(R.id.sport_img));
            requestManager.load(upcomingActivity.getParticipantImg())
                    .into((ImageView) dialog.findViewById(R.id.participant_img));
            requestManager.load(upcomingActivity.getNotesImg())
                    .into((ImageView) dialog.findViewById(R.id.notes_img));

            dialog.findViewById(R.id.delete).setOnClickListener(view -> {

                if (mainFragment != null)
                    viewModel.deleteFromDb(mainFragment, null, this, upcomingActivity);
                else if (upcomingFragment != null)
                    viewModel.deleteFromDb(null, upcomingFragment, this, upcomingActivity);

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

    public void setNotifications(Context context, MainFragment mainFragment,
                                 UpcomingActFragment upcomingFragment, int numOfViews) {
        this.context = context;
        this.numOfViews = numOfViews;
        this.mainFragment = mainFragment;
        this.upcomingFragment = upcomingFragment;
        notifyDataSetChanged();
    }

    private static final DiffUtil.ItemCallback<UpcomingActivity> DIFF_CALLBACK =  new DiffUtil.ItemCallback<UpcomingActivity>() {
        @Override
        public boolean areItemsTheSame(@NonNull UpcomingActivity oldItem, @NonNull UpcomingActivity newItem) {
            return oldItem.getId().equals(newItem.getId());
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
