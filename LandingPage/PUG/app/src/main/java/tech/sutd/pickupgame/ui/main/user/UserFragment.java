package tech.sutd.pickupgame.ui.main.user;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.databinding.FragmentUserBinding;
import tech.sutd.pickupgame.ui.main.MainActivity;

public class UserFragment extends DaggerFragment {

    private FragmentUserBinding binding;

    private NavController navController;

    @Inject RequestManager requestManager;
    @Inject SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
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
        requestManager.load(R.drawable.ic_pug)
                .into(binding.profileImage);

        binding.editProfile.setOnClickListener(v -> navController.navigate(R.id.action_userFragment_to_editProfileFragment));

        binding.logout.setOnClickListener(v -> {
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null)
                sessionManager.logout(activity);
        });
    }
}