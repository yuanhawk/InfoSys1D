package tech.sutd.pickupgame.ui.main.main;

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
import tech.sutd.pickupgame.databinding.FragmentMainBinding;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.viewmodel.NewActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class MainFragment extends DaggerFragment {

    private FragmentMainBinding binding;
    private NavController navController;

    private UpcomingActViewModel upcomingActViewModel;
    private NewActViewModel newActViewModel;

    private NewActivityAdapter newAdapter;
    private UpcomingActivityAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        upcomingActViewModel = new ViewModelProvider(this, providerFactory).get(UpcomingActViewModel.class);
        newActViewModel = new ViewModelProvider(this, providerFactory).get(NewActViewModel.class);

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
            adapter.setNotifications(upcomingActivities, requestManager));

        newActViewModel.getNewActivities().observe(getViewLifecycleOwner(), newActivities ->
            newAdapter.setNotifications(newActivities, requestManager));
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

        adapter = new UpcomingActivityAdapter();
        binding.upcomingRc.setAdapter(adapter);
        binding.upcomingRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.upcomingRc.setHasFixedSize(true);


        newActViewModel.insert(new NewActivity(0, "Cycling", R.drawable.clock,"14 Sep, 7pm - 10pm", R.drawable.location,
                "S123456, East Coast Park", R.drawable.profile, "John Doe", R.drawable.badminton));
        newActViewModel.insert(new NewActivity(1, "Cycling", R.drawable.clock,"14 Sep, 7pm - 10pm", R.drawable.location,
                "S123456, East Coast Park", R.drawable.profile, "John Doe", R.drawable.badminton));
        newActViewModel.insert(new NewActivity(2, "Cycling", R.drawable.clock,"14 Sep, 7pm - 10pm", R.drawable.location,
                "S123456, East Coast Park", R.drawable.profile, "John Doe", R.drawable.badminton));
        newActViewModel.insert(new NewActivity(3, "Cycling", R.drawable.clock,"14 Sep, 7pm - 10pm", R.drawable.location,
                "S123456, East Coast Park", R.drawable.profile, "John Doe", R.drawable.badminton));


        newAdapter = new NewActivityAdapter();
        binding.newRc.setAdapter(newAdapter);
        binding.newRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.newRc.setHasFixedSize(true);
    }
}