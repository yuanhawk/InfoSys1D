package tech.sutd.pickupgame.ui.main.main.newact;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;

import java.util.Calendar;

import javax.inject.Inject;

import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.worker.NewActivitiesWorker;
import tech.sutd.pickupgame.data.worker.UpcomingActivitiesWorker;
import tech.sutd.pickupgame.databinding.FragmentNewActBinding;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.ui.main.SuccessListener;
import tech.sutd.pickupgame.ui.main.SuccessListenerTwo;
import tech.sutd.pickupgame.ui.main.main.adapter.FilterAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.viewmodel.NewActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;
import tech.sutd.pickupgame.util.CustomSnapHelper;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class NewActFragment extends BaseFragment implements View.OnClickListener {

    private FragmentNewActBinding binding;

    private NewActViewModel newActViewModel;

    private NavController navController;

    private Observer<PagedList<NewActivity>> observer;

    private SuccessListenerTwo successListenerTwo;

    @Inject NewActivityAdapter<NewActivity> newAdapter;
    @Inject ViewModelProviderFactory providerFactory;
    @Inject RequestManager requestManager;
    @Inject FilterAdapter filterAdapter;
    @Inject Handler handler;

    @Inject Constraints constraints;

    public SuccessListenerTwo getSuccessListenerTwo() {
        return successListenerTwo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewActBinding.inflate(inflater, container, false);

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
        binding.back.setOnClickListener(this);
        binding.filter.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        newActViewModel.delete(String.valueOf(Calendar.getInstance().getTimeInMillis()));

        OneTimeWorkRequest upcomingActRequest = new OneTimeWorkRequest.Builder(UpcomingActivitiesWorker.class)
                .setConstraints(constraints)
                .build();

        OneTimeWorkRequest newActRequest = new OneTimeWorkRequest.Builder(NewActivitiesWorker.class)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(requireActivity().getApplicationContext())
                .beginWith(upcomingActRequest)
                .then(newActRequest)
                .enqueue();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.newRc.setOnFlingListener(null);
    }

    private void subscribeObserver() {
        observer = newActivities -> newAdapter.submitList(newActivities);

        newActViewModel.getAllNewActivitiesByClock().observe(getViewLifecycleOwner(), observer);
    }

    private void initViews() {
        binding.newRc.setAdapter(newAdapter);
        binding.newRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.newRc.setHasFixedSize(true);
        newAdapter.setNotifications(getContext(), null, this, 9999);

        new CustomSnapHelper().attachToRecyclerView(binding.newRc);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                navController.popBackStack(R.id.mainFragment, false);
                break;
            case R.id.filter:
                Dialog dialog = setDialog(R.layout.filter);

                ListView filterList = dialog.findViewById(R.id.filter_list);
                Button button = dialog.findViewById(R.id.confirm_button);

                filterList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                filterList.setAdapter(filterAdapter);

                filterList.setOnItemClickListener((parent, filterView, position, id) -> {
                    int pos = (int) parent.getItemAtPosition(position);

                    switch (pos) {
                        case R.drawable.ic_calendar:
                            if (newActViewModel.getAllNewActivitiesBySport().hasActiveObservers())
                                newActViewModel.getAllNewActivitiesBySport().removeObserver(observer);
                            newActViewModel.getAllNewActivitiesByClock().observe(getViewLifecycleOwner(), observer);
                            break;
                        case R.drawable.ic_star:
                            if (newActViewModel.getAllNewActivitiesByClock().hasActiveObservers())
                                newActViewModel.getAllNewActivitiesByClock().removeObserver(observer);
                            newActViewModel.getAllNewActivitiesBySport().observe(getViewLifecycleOwner(), observer);
                            break;
                        case R.drawable.ic_location:
                            Toast.makeText(getContext(), "Near Me Pressed", Toast.LENGTH_SHORT).show();
                            break;
                    }
                });

                button.setOnClickListener(view -> dialog.dismiss());
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            successListenerTwo = (SuccessListenerTwo) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}