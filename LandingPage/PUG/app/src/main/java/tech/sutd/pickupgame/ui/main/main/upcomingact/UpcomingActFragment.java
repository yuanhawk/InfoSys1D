package tech.sutd.pickupgame.ui.main.main.upcomingact;

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
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.WorkManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.databinding.FragmentUpcomingActBinding;
import tech.sutd.pickupgame.models.ui.PastActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.ui.main.main.adapter.PastActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.YourActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.viewmodel.PastActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.YourActViewModel;
import tech.sutd.pickupgame.util.CustomSnapHelper;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class UpcomingActFragment extends BaseFragment implements BaseInterface.RefreshListener {

    private static final String TAG = "UpcomingActFragment";
    
    private FragmentUpcomingActBinding binding;
    private NavController navController;

    private UpcomingActViewModel upcomingActViewModel;
    private YourActViewModel yourActViewModel;
    private PastActViewModel pastActViewModel;

    private BaseInterface.UpcomingActDeleteListener upcomingActDeleteListener;

    private Observer<Resource<PagedList<UpcomingActivity>>> upcomingActObserver;
    private Observer<Resource<List<YourActivity>>> yourActObserver;
    private Observer<Resource<List<PastActivity>>> pastActObserver;

    @Inject UpcomingActivityAdapter<UpcomingActivity> upcomingAdapter;
    @Inject YourActivityAdapter yourAdapter;
    @Inject PastActivityAdapter pastAdapter;
    @Inject ViewModelProviderFactory providerFactory;

    @Inject Constraints constraints;
    @Inject Handler handler;

    public BaseInterface.UpcomingActDeleteListener getUpcomingActDeleteListener() {
        return upcomingActDeleteListener;
    }

    @Override
    public void refreshObserver() {
        if (upcomingActViewModel.getAllUpcomingActivitiesByClock().hasActiveObservers())
            upcomingActViewModel.getAllUpcomingActivitiesByClock().removeObserver(upcomingActObserver);
        upcomingActViewModel.getAllUpcomingActivitiesByClock().observe(getViewLifecycleOwner(), upcomingActObserver);

        if (yourActViewModel.getAllYourActivitiesByClockLimit10().hasActiveObservers())
            yourActViewModel.getAllYourActivitiesByClockLimit10().removeObserver(yourActObserver);
        yourActViewModel.getAllYourActivitiesByClockLimit10().observe(getViewLifecycleOwner(), yourActObserver);

        if (pastActViewModel.getPastActivities().hasActiveObservers())
            pastActViewModel.getPastActivities().removeObserver(pastActObserver);
        pastActViewModel.getPastActivities().observe(getViewLifecycleOwner(), pastActObserver);
    }

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

        binding.swipeRefresh.setOnRefreshListener(() -> {
            pullUpcomingAct();

            refreshObserver();

            binding.swipeRefresh.setRefreshing(false);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.upcomingRc.setOnFlingListener(null);
        binding.yourRc.setOnFlingListener(null);
        binding.pastRc.setOnFlingListener(null);
    }

    private void subscribeObserver() {

        upcomingActObserver = pagedListResource -> {
            if (pagedListResource.status == Resource.Status.SUCCESS) {
                assert pagedListResource.data != null;
                updateUpcomingView(pagedListResource.data.size());
                upcomingAdapter.submitList(pagedListResource.data);
            }
        };

        yourActObserver = listResource -> {
            if (listResource != null) {
                if (listResource.status == Resource.Status.SUCCESS) {
                    updateYourView(listResource);
                }
            }
        };

        pastActObserver = listResource -> {
            if (listResource.status == Resource.Status.SUCCESS) {
                updatePastView(listResource);
                pastAdapter.setSource(listResource.data);
            }
        };

        upcomingActViewModel.getAllUpcomingActivitiesByClock().observe(getViewLifecycleOwner(), upcomingActObserver);
        yourActViewModel.getAllYourActivitiesByClockLimit10().observe(getViewLifecycleOwner(), yourActObserver);
        pastActViewModel.getPastActivities().observe(getViewLifecycleOwner(),pastActObserver);
    }

    private void updateUpcomingView(int size) {
        if (size > 0) {
            binding.upcomingRc.setVisibility(View.VISIBLE);
            binding.upcomingEmpty.setVisibility(View.GONE);
        } else {
            binding.upcomingRc.setVisibility(View.GONE);
            binding.upcomingEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void updateYourView(Resource<List<YourActivity>> listResource) {
        assert listResource.data != null;
        if (listResource.data.size() > 0) {
            binding.yourRc.setVisibility(View.VISIBLE);
            binding.yourEmpty.setVisibility(View.GONE);
            yourAdapter.setSource(listResource.data);
        } else {
            binding.yourRc.setVisibility(View.GONE);
            binding.yourEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void updatePastView(Resource<List<PastActivity>> listResource) {
        assert listResource.data != null;
        if (listResource.data.size() > 0) {
            binding.pastRc.setVisibility(View.VISIBLE);
            binding.pastEmpty.setVisibility(View.GONE);
            pastAdapter.setSource(listResource.data);
        } else {
            binding.pastRc.setVisibility(View.GONE);
            binding.pastEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void initViews() {

        binding.upcomingRc.setAdapter(upcomingAdapter);
        binding.upcomingRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.upcomingRc.setHasFixedSize(true);
        upcomingAdapter.setNotifications(getContext(), null, this, 9999);

        binding.yourRc.setAdapter(yourAdapter);
        binding.yourRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.yourRc.setHasFixedSize(true);
        yourAdapter.setNotification(getContext(), this);

        binding.pastRc.setAdapter(pastAdapter);
        binding.pastRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.pastRc.setHasFixedSize(true);

        new CustomSnapHelper().attachToRecyclerView(binding.upcomingRc);
        new CustomSnapHelper().attachToRecyclerView(binding.yourRc);
        new CustomSnapHelper().attachToRecyclerView(binding.pastRc);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            upcomingActDeleteListener = (BaseInterface.UpcomingActDeleteListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}