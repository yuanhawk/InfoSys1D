package tech.sutd.pickupgame.ui.main.user;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.databinding.FragmentUserBinding;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.PastActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.ui.auth.viewmodel.UserViewModel;
import tech.sutd.pickupgame.ui.main.MainActivity;
import tech.sutd.pickupgame.ui.main.main.viewmodel.PastActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.YourActViewModel;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class UserFragment extends BaseFragment implements View.OnClickListener {

    private int joined, organized, tried;
    private List<String> sports = new LinkedList<>();

    private FragmentUserBinding binding;

    @Inject RequestManager requestManager;
    @Inject SessionManager sessionManager;

    @Inject UserViewModel userViewModel;

    @Inject UpcomingActViewModel upcomingActViewModel;
    @Inject PastActViewModel pastActViewModel;
    @Inject YourActViewModel yourActViewModel;

    @Inject ViewModelProviderFactory providerFactory;

    @Inject Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);

        subscribeObservers();

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.editProfile.setOnClickListener(this);
        binding.logout.setOnClickListener(this);
    }

    private void subscribeObservers() {
        userViewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            if (users.size() > 0 && !users.get(0).getId().equalsIgnoreCase("0")) {
                binding.userName.setText(users.get(0).getName());

                requestManager.load(Uri.parse(users.get(0).getImg()))
                        .into(binding.profileImage);
            }
        });

        upcomingActViewModel.getAllUpcomingActivitiesByClock().observe(getViewLifecycleOwner(), pagedListResource -> {
            if (pagedListResource.data != null && pagedListResource.data.size() > 0) {
                joined += pagedListResource.data.size();
                binding.numActJoined.setText(String.valueOf(joined));


                handler.post(() -> {
                    for (UpcomingActivity act: pagedListResource.data) {
                        if (!sports.contains(act.getSport())) {
                            sports.add(act.getSport());
                            tried += 1;
                        }
                    }

                    binding.numActTried.setText(String.valueOf(tried));
                });
            }
        });

        pastActViewModel.getPastActivities().observe(getViewLifecycleOwner(), listResource -> {
            if (listResource.data != null && listResource.data.size() > 0) {
                joined += listResource.data.size();
                binding.numActJoined.setText(String.valueOf(joined));

                handler.post(() -> {
                    for (PastActivity act: listResource.data) {
                        if (!sports.contains(act.getSport())) {
                            sports.add(act.getSport());
                            tried += 1;
                        }
                    }

                    binding.numActTried.setText(String.valueOf(tried));
                });
            }
        });

        yourActViewModel.getAllYourActivitiesByClock().observe(getViewLifecycleOwner(), listResource -> {
           if (listResource.data != null && listResource.data.size() > 0) {
               organized += listResource.data.size();
               binding.numActOrganized.setText(String.valueOf(organized));

               handler.post(() -> {
                   for (YourActivity act: listResource.data) {
                       if (!sports.contains(act.getSport())) {
                           sports.add(act.getSport());
                           tried += 1;
                       }
                   }

                   binding.numActTried.setText(String.valueOf(tried));
               });
           }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        joined = organized = 0;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == binding.editProfile.getId())
            getNavController().navigate(R.id.action_userFragment_to_editProfileFragment);
        else if (id == binding.logout.getId()) {
            sessionManager.logout();
        }
    }


}