package tech.sutd.pickupgame.ui.main.main.upcoming;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.FragmentUpcomingActBinding;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;
import tech.sutd.pickupgame.util.CustomSnapHelper;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class UpcomingActFragment extends DaggerFragment {

    private CustomSnapHelper helper = new CustomSnapHelper();

    private FragmentUpcomingActBinding binding;
    private NavController navController;

    private UpcomingActViewModel upcomingActViewModel;

    private UpcomingActivityAdapter upcomingAdapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUpcomingActBinding.inflate(inflater, container, false);

        upcomingActViewModel = new ViewModelProvider(this, providerFactory).get(UpcomingActViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
        subscribeObserver();
    }

    private void subscribeObserver() {
        upcomingActViewModel.getUpcomingActivities().observe(getViewLifecycleOwner(), upcomingActivities ->
                upcomingAdapter.setNotifications(upcomingActivities, requestManager, 9999));
    }

    private void initViews() {

        upcomingActViewModel.insert(new UpcomingActivity(0, "Cycling", R.drawable.clock,"14 Sep, 7pm - 10pm", R.drawable.location,
                "S123456, East Coast Park", R.drawable.profile, "John Doe", R.drawable.cycling));
        upcomingActViewModel.insert(new UpcomingActivity(1, "Cycling", R.drawable.clock,"14 Sep, 7pm - 10pm", R.drawable.location,
                "S123456, East Coast Park", R.drawable.profile, "John Doe", R.drawable.cycling));
        upcomingActViewModel.insert(new UpcomingActivity(2, "Cycling", R.drawable.clock,"14 Sep, 7pm - 10pm", R.drawable.location,
                "S123456, East Coast Park", R.drawable.profile, "John Doe", R.drawable.cycling));
        upcomingActViewModel.insert(new UpcomingActivity(3, "Cycling", R.drawable.clock,"14 Sep, 7pm - 10pm", R.drawable.location,
                "S123456, East Coast Park", R.drawable.profile, "John Doe", R.drawable.cycling));

        upcomingAdapter = new UpcomingActivityAdapter();
        binding.upcomingRc.setAdapter(upcomingAdapter);
        binding.upcomingRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.upcomingRc.setHasFixedSize(true);

        helper.attachToRecyclerView(binding.upcomingRc);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.upcomingRc.setOnFlingListener(null);
    }
}