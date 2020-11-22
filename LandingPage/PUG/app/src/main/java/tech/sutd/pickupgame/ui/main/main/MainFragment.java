package tech.sutd.pickupgame.ui.main.main;

import android.content.Context;
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

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.worker.NewActivitiesWorker;
import tech.sutd.pickupgame.databinding.FragmentMainBinding;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.ui.main.SuccessListener;
import tech.sutd.pickupgame.ui.main.SuccessListenerTwo;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.viewmodel.NewActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class MainFragment extends DaggerFragment {

    private static final String TAG = "MainFragment";

    private FragmentMainBinding binding;
    private NavController navController;

    private UpcomingActViewModel upcomingActViewModel;
    private NewActViewModel newActViewModel;

    private BaseInterface listener;
    private SuccessListener successListener;
    private SuccessListenerTwo successListenerTwo;

    @Inject UpcomingActivityAdapter<UpcomingActivity> adapter;
    @Inject NewActivityAdapter<NewActivity> newAdapter;
    @Inject ViewModelProviderFactory providerFactory;

    @Inject Constraints constraints;
    @Inject Handler handler;

    public BaseInterface getListener() {
        return listener;
    }

    public SuccessListenerTwo getSuccessListenerTwo() {
        return successListenerTwo;
    }

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

    @Override
    public void onResume() {
        super.onResume();
        newActViewModel.delete(String.valueOf(Calendar.getInstance().getTimeInMillis()));

        OneTimeWorkRequest singleRequest = new OneTimeWorkRequest.Builder(NewActivitiesWorker.class)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(requireActivity().getApplicationContext()).enqueue(singleRequest);

        WorkManager.getInstance(requireActivity().getApplicationContext()).getWorkInfoByIdLiveData(singleRequest.getId())
                .observe(getViewLifecycleOwner(), workInfo -> {
                    if (workInfo != null) {
                        if (workInfo.getState().isFinished()) {
                            Data data = workInfo.getOutputData();

                            String output = data.getString(NewActivitiesWorker.KEY_TASK_OUTPUT);
                            Log.d(TAG, "onChanged: " + output);

                        }

                        String status = workInfo.getState().name();
                        Log.d(TAG, "onChanged: " + status);
                    }
                });
    }

    private void subscribeObserver() {
        upcomingActViewModel.getUpcomingActivitiesByClock2().observe(getViewLifecycleOwner(), upcomingActivities ->
            adapter.submitList(upcomingActivities));

        newActViewModel.getNewActivitiesByClock2().observe(getViewLifecycleOwner(), newActivities ->
            newAdapter.submitList(newActivities));
    }

    private void initViews() {
        upcomingActViewModel.pull();

        binding.upcomingRc.setAdapter(adapter);
        binding.upcomingRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.upcomingRc.setHasFixedSize(true);
        adapter.setNotifications(1);


        binding.newRc.setAdapter(newAdapter);
        binding.newRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.newRc.setHasFixedSize(true);
        newAdapter.setNotifications(getContext(), this, null, 1);

        binding.upcomingAct.setOnClickListener(v -> navController.navigate(R.id.action_mainFragment_to_upcomingActFragment));
        binding.newAct.setOnClickListener(v -> navController.navigate(R.id.action_mainFragment_to_newActFragment));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (BaseInterface) context;
            successListener = (SuccessListener) context;
            successListenerTwo = (SuccessListenerTwo) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}