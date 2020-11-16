package tech.sutd.pickupgame.ui.main.main.newact;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.FragmentNewActBinding;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.ui.main.main.adapter.FilterAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.viewmodel.NewActViewModel;
import tech.sutd.pickupgame.util.CustomSnapHelper;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class NewActFragment extends BaseFragment implements View.OnClickListener {

    private FragmentNewActBinding binding;

    private NewActViewModel newActViewModel;

    private NavController navController;

    @Inject
    NewActivityAdapter<NewActivity> newAdapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    @Inject
    FilterAdapter filterAdapter;

    @Inject
    Handler handler;

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
    public void onPause() {
        super.onPause();
        navController.popBackStack(R.id.mainFragment, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.newRc.setOnFlingListener(null);
    }

    private void subscribeObserver() {
        newActViewModel.getAllNewActivitiesByClock().observe(getViewLifecycleOwner(), newActivities ->
                newAdapter.submitList(newActivities));
    }

    private void initViews() {
        newActViewModel.insert(new NewActivity(0, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600340400) * 1000), String.valueOf(Long.valueOf(1600351200) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        newActViewModel.insert(new NewActivity(1, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600254000) * 1000), String.valueOf(Long.valueOf(1600264800) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        newActViewModel.insert(new NewActivity(2, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600167600) * 1000), String.valueOf(Long.valueOf(1600178400) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        newActViewModel.insert(new NewActivity(3, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600081200) * 1000), String.valueOf(Long.valueOf(1600092000) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));

        binding.newRc.setAdapter(newAdapter);
        binding.newRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.newRc.setHasFixedSize(true);
        newAdapter.setNotifications(requestManager, 9999);

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

                filterAdapter.setRequestManager(requestManager);
                filterList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                filterList.setAdapter(filterAdapter);

                filterList.setOnItemClickListener((parent, view, position, id) -> {
                    int pos = (int) parent.getItemAtPosition(position);

                    switch (pos) {
                        case R.drawable.ic_calendar:
                            Toast.makeText(getContext(), "Date Pressed", Toast.LENGTH_SHORT).show();
                            break;
                        case R.drawable.ic_star:
                            Toast.makeText(getContext(), "Sport Pressed", Toast.LENGTH_SHORT).show();
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
}