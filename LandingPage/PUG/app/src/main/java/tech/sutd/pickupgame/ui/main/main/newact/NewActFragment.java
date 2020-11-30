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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import tech.sutd.pickupgame.data.Resource;
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

    private Observer<Resource<PagedList<NewActivity>>> observer;

    private SuccessListenerTwo successListenerTwo;

    @Inject NewActivityAdapter<NewActivity> newAdapter;
    @Inject ViewModelProviderFactory providerFactory;
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
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.newRc.setOnFlingListener(null);
    }

    private void subscribeObserver() {
        observer = pagedListResource -> {
            if (pagedListResource.status == Resource.Status.SUCCESS) {
                newAdapter.submitList(pagedListResource.data);
            }
        };

        newActViewModel.getAllNewActivitiesByClock().observe(getViewLifecycleOwner(), observer);
    }

    private void initViews() {
        binding.newRc.setAdapter(newAdapter);
        binding.newRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.newRc.setHasFixedSize(true);
        newAdapter.setNotifications(getContext(), null, this, 9999);

        new CustomSnapHelper().attachToRecyclerView(binding.newRc);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            pullNewAct();

            if (newActViewModel.getAllNewActivitiesBySport().hasActiveObservers())
                newActViewModel.getAllNewActivitiesBySport().removeObserver(observer);
            newActViewModel.getAllNewActivitiesBySport().observe(getViewLifecycleOwner(), observer);

            if (newActViewModel.getAllNewActivitiesByClock().hasActiveObservers())
                newActViewModel.getAllNewActivitiesByClock().removeObserver(observer);
            newActViewModel.getAllNewActivitiesByClock().observe(getViewLifecycleOwner(), observer);

            binding.swipeRefresh.setRefreshing(false);
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.back.getId()) {
            getNavController().popBackStack(R.id.mainFragment, false);
        } else if (id == binding.filter.getId()) {
            Dialog dialog = setDialog(R.layout.filter);

            ListView filterList = dialog.findViewById(R.id.filter_list);
            Button button = dialog.findViewById(R.id.confirm_button);

            filterList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            filterList.setAdapter(filterAdapter);

            filterList.setOnItemClickListener((parent, filterView, position, filterId) -> {
                int posImg = (int) parent.getItemAtPosition(position);

                if (posImg == R.drawable.ic_calendar) {
                    if (newActViewModel.getAllNewActivitiesBySport().hasActiveObservers())
                        newActViewModel.getAllNewActivitiesBySport().removeObserver(observer);
                    newActViewModel.getAllNewActivitiesByClock().observe(getViewLifecycleOwner(), observer);
                } else if (posImg == R.drawable.ic_star) {
                    if (newActViewModel.getAllNewActivitiesByClock().hasActiveObservers())
                        newActViewModel.getAllNewActivitiesByClock().removeObserver(observer);
                    newActViewModel.getAllNewActivitiesBySport().observe(getViewLifecycleOwner(), observer);
                } else if (posImg == R.drawable.ic_location) {
                    Toast.makeText(getContext(), "Near Me Pressed", Toast.LENGTH_SHORT).show();
                }
            });

            button.setOnClickListener(view -> dialog.dismiss());
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