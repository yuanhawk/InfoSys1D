package tech.sutd.pickupgame.ui.main.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.databinding.FragmentMainBinding;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.viewmodel.NewActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class MainFragment extends BaseFragment implements View.OnClickListener, BaseInterface.RefreshListener {

    private FragmentMainBinding binding;

    private UpcomingActViewModel upcomingActViewModel;
    private NewActViewModel newActViewModel;

    private BaseInterface.BookingActListener bookingActListener;
    private BaseInterface.UpcomingActDeleteListener upcomingActDeleteListener;

    private Observer<Resource<PagedList<UpcomingActivity>>> upcomingActObserver;
    private Observer<Resource<PagedList<NewActivity>>> newActObserver;

    @Inject UpcomingActivityAdapter<UpcomingActivity> adapter;
    @Inject NewActivityAdapter<NewActivity> newAdapter;
    @Inject ViewModelProviderFactory providerFactory;

    @Inject Handler handler;

    public BaseInterface.BookingActListener getBookingActListener() {
        return bookingActListener;
    }

    public BaseInterface.UpcomingActDeleteListener getUpcomingActDeleteListener() {
        return upcomingActDeleteListener;
    }

    @Override
    public void refreshObserver() {
        if (upcomingActViewModel.getUpcomingActivitiesByClock2().hasActiveObservers())
            upcomingActViewModel.getUpcomingActivitiesByClock2().removeObserver(upcomingActObserver);
        upcomingActViewModel.getUpcomingActivitiesByClock2().observe(getViewLifecycleOwner(), upcomingActObserver);

        if (newActViewModel.getNewActivitiesByClock2().hasActiveObservers())
            newActViewModel.getNewActivitiesByClock2().removeObserver(newActObserver);
        newActViewModel.getNewActivitiesByClock2().observe(getViewLifecycleOwner(), newActObserver);
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
    public void onStart() {
        super.onStart();
        initViews();
        subscribeObserver();
    }

    private void subscribeObserver() {
        upcomingActObserver = pagedListResource -> {
            if (pagedListResource.status == Resource.Status.SUCCESS) {
                updateUpcomingView(pagedListResource);
            }
        };

        newActObserver = pagedListResource -> {
            if (pagedListResource.status == Resource.Status.SUCCESS) {
                updateNewView(pagedListResource);
            }
        };

        upcomingActViewModel.getUpcomingActivitiesByClock2().observe(getViewLifecycleOwner(), upcomingActObserver);

        newActViewModel.getNewActivitiesByClock2().observe(getViewLifecycleOwner(), newActObserver);
    }

    private void updateUpcomingView(Resource<PagedList<UpcomingActivity>> pagedListResource) {
        if (pagedListResource.data.size() > 0) {
            binding.upcomingRc.setVisibility(View.VISIBLE);
            binding.upcomingEmpty.setVisibility(View.GONE);
            adapter.submitList(pagedListResource.data);
        } else {
            binding.upcomingRc.setVisibility(View.GONE);
            binding.upcomingEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void updateNewView(Resource<PagedList<NewActivity>> pagedListResource) {
        if (pagedListResource.data.size() > 0) {
            binding.newRc.setVisibility(View.VISIBLE);
            binding.newEmpty.setVisibility(View.GONE);
            newAdapter.submitList(pagedListResource.data);
        } else {
            binding.newRc.setVisibility(View.GONE);
            binding.newEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void initViews() {

        binding.upcomingRc.setAdapter(adapter);
        binding.upcomingRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.upcomingRc.setHasFixedSize(true);
        adapter.setNotifications(getContext(), this, null, 1);


        binding.newRc.setAdapter(newAdapter);
        binding.newRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.newRc.setHasFixedSize(true);
        newAdapter.setNotifications(getContext(), this, null, 1);

        binding.upcomingAct.setOnClickListener(this);
        binding.newAct.setOnClickListener(this);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            pullMainAct();

            refreshObserver();

            binding.swipeRefresh.setRefreshing(false);
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.upcomingAct.getId())
            getNavController().navigate(R.id.action_mainFragment_to_upcomingActFragment);
        else if (id == binding.newAct.getId())
            getNavController().navigate(R.id.action_mainFragment_to_newActFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            bookingActListener = (BaseInterface.BookingActListener) context;
            upcomingActDeleteListener = (BaseInterface.UpcomingActDeleteListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}