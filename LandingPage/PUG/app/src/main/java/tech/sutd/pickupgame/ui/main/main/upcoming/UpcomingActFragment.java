package tech.sutd.pickupgame.ui.main.main.upcoming;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.R;
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

    @Inject UpcomingActivityAdapter upcomingAdapter;

    @Inject YourActivityAdapter yourAdapter;

    @Inject PastActivityAdapter pastAdapter;

    @Inject ViewModelProviderFactory providerFactory;

    @Inject RequestManager requestManager;

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
    }

    private void subscribeObserver() {
        upcomingActViewModel.getUpcomingActivities().observe(getViewLifecycleOwner(), upcomingActivities ->
                upcomingAdapter.setNotifications(upcomingActivities, requestManager, 9999));

        yourActViewModel.getYourActivitiesByClock().observe(getViewLifecycleOwner(), yourAdapter::submitList);

        pastActViewModel.getPastActivities().observe(getViewLifecycleOwner(), pastActivities ->
                pastAdapter.setNotifications(pastActivities, requestManager, 9999));
    }

    private void initViews() {

        upcomingActViewModel.insert(new UpcomingActivity(0, "Cycling", R.drawable.ic_clock,"14 Sep, 7pm - 10pm", R.drawable.ic_location,
                "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        upcomingActViewModel.insert(new UpcomingActivity(1, "Cycling", R.drawable.ic_clock,"14 Sep, 7pm - 10pm", R.drawable.ic_location,
                "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        upcomingActViewModel.insert(new UpcomingActivity(2, "Cycling", R.drawable.ic_clock,"14 Sep, 7pm - 10pm", R.drawable.ic_location,
                "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        upcomingActViewModel.insert(new UpcomingActivity(3, "Cycling", R.drawable.ic_clock,"14 Sep, 7pm - 10pm", R.drawable.ic_location,
                "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));

        binding.upcomingRc.setAdapter(upcomingAdapter);
        binding.upcomingRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.upcomingRc.setHasFixedSize(true);

        yourActViewModel.insert(new YourActivity(0, "Cycling", R.drawable.ic_clock,"17 Sep, 7pm - 10pm", R.drawable.ic_location,
                "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        yourActViewModel.insert(new YourActivity(1, "Cycling", R.drawable.ic_clock,"16 Sep, 7pm - 10pm", R.drawable.ic_location,
                "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        yourActViewModel.insert(new YourActivity(2, "Cycling", R.drawable.ic_clock,"15 Sep, 7pm - 10pm", R.drawable.ic_location,
                "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        yourActViewModel.insert(new YourActivity(3, "Cycling", R.drawable.ic_clock,"14 Sep, 7pm - 10pm", R.drawable.ic_location,
                "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));

        yourAdapter = new YourActivityAdapter();
        binding.eventsRc.setAdapter(yourAdapter);
        binding.eventsRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.eventsRc.setHasFixedSize(true);
        yourAdapter.setNotifications(requestManager, 9999);

        pastActViewModel.insert(new PastActivity(0, "Cycling", R.drawable.ic_clock,"14 Sep, 7pm - 10pm", R.drawable.ic_location,
                "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        pastActViewModel.insert(new PastActivity(1, "Cycling", R.drawable.ic_clock,"14 Sep, 7pm - 10pm", R.drawable.ic_location,
                "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        pastActViewModel.insert(new PastActivity(2, "Cycling", R.drawable.ic_clock,"14 Sep, 7pm - 10pm", R.drawable.ic_location,
                "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        pastActViewModel.insert(new PastActivity(3, "Cycling", R.drawable.ic_clock,"14 Sep, 7pm - 10pm", R.drawable.ic_location,
                "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));

        pastAdapter = new PastActivityAdapter();
        binding.pastRc.setAdapter(pastAdapter);
        binding.pastRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.pastRc.setHasFixedSize(true);


        new CustomSnapHelper().attachToRecyclerView(binding.upcomingRc);
        new CustomSnapHelper().attachToRecyclerView(binding.eventsRc);
        new CustomSnapHelper().attachToRecyclerView(binding.pastRc);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.upcomingRc.setOnFlingListener(null);
        binding.eventsRc.setOnFlingListener(null);
        binding.pastRc.setOnFlingListener(null);
    }
}