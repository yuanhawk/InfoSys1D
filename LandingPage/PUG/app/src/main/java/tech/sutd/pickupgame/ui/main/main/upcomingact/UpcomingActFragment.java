package tech.sutd.pickupgame.ui.main.main.upcomingact;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.worker.NewActivitiesWorker;
import tech.sutd.pickupgame.databinding.FragmentUpcomingActBinding;
import tech.sutd.pickupgame.models.ui.PastActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.ui.main.main.adapter.PastActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.YourActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.viewmodel.PastActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.YourActViewModel;
import tech.sutd.pickupgame.util.CustomSnapHelper;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class UpcomingActFragment extends DaggerFragment {

    private FragmentUpcomingActBinding binding;
    private NavController navController;

    private UpcomingActViewModel upcomingActViewModel;
    private YourActViewModel yourActViewModel;
    private PastActViewModel pastActViewModel;

    @Inject UpcomingActivityAdapter<UpcomingActivity> upcomingAdapter;
    @Inject YourActivityAdapter yourAdapter;
    @Inject PastActivityAdapter pastAdapter;
    @Inject ViewModelProviderFactory providerFactory;

    @Inject Constraints constraints;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUpcomingActBinding.inflate(inflater, container, false);

        upcomingActViewModel = new ViewModelProvider(this, providerFactory).get(UpcomingActViewModel.class);
        yourActViewModel = new ViewModelProvider(this, providerFactory).get(YourActViewModel.class);
        pastActViewModel = new ViewModelProvider(this, providerFactory).get(PastActViewModel.class);
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
        binding.back.setOnClickListener(v -> navController.popBackStack(R.id.mainFragment, false));
    }

    @Override
    public void onResume() {
        super.onResume();

        OneTimeWorkRequest singleRequest = new OneTimeWorkRequest.Builder(NewActivitiesWorker.class)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(requireActivity().getApplicationContext()).enqueue(singleRequest);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.upcomingRc.setOnFlingListener(null);
        binding.eventsRc.setOnFlingListener(null);
        binding.pastRc.setOnFlingListener(null);
    }

    private void subscribeObserver() {
        upcomingActViewModel.getAllUpcomingActivitiesByClock().observe(getViewLifecycleOwner(), upcomingActivities ->
                upcomingAdapter.submitList(upcomingActivities));

        yourActViewModel.getYourActivities().observe(getViewLifecycleOwner(), yourActivities ->
                yourAdapter.setNotifications(yourActivities, 9999));

        pastActViewModel.getPastActivities().observe(getViewLifecycleOwner(), pastActivities ->
                pastAdapter.setNotifications(pastActivities, 9999));
    }

    private void initViews() {

        binding.upcomingRc.setAdapter(upcomingAdapter);
        binding.upcomingRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.upcomingRc.setHasFixedSize(true);
        upcomingAdapter.setNotifications(9999);

        yourActViewModel.insert(new YourActivity(0, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600340400) * 1000), String.valueOf(Long.valueOf(1600351200) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        yourActViewModel.insert(new YourActivity(1, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600254000) * 1000), String.valueOf(Long.valueOf(1600264800) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        yourActViewModel.insert(new YourActivity(2, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600167600) * 1000), String.valueOf(Long.valueOf(1600178400) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        yourActViewModel.insert(new YourActivity(3, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600081200) * 1000), String.valueOf(Long.valueOf(1600092000) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));

        binding.eventsRc.setAdapter(yourAdapter);
        binding.eventsRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.eventsRc.setHasFixedSize(true);

        pastActViewModel.insert(new PastActivity(0, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600340400) * 1000), String.valueOf(Long.valueOf(1600351200) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        pastActViewModel.insert(new PastActivity(1, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600254000) * 1000), String.valueOf(Long.valueOf(1600264800) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        pastActViewModel.insert(new PastActivity(2, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600167600) * 1000), String.valueOf(Long.valueOf(1600178400) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        pastActViewModel.insert(new PastActivity(3, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600081200) * 1000), String.valueOf(Long.valueOf(1600092000) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));

        binding.pastRc.setAdapter(pastAdapter);
        binding.pastRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.pastRc.setHasFixedSize(true);

        new CustomSnapHelper().attachToRecyclerView(binding.upcomingRc);
        new CustomSnapHelper().attachToRecyclerView(binding.eventsRc);
        new CustomSnapHelper().attachToRecyclerView(binding.pastRc);
    }
}