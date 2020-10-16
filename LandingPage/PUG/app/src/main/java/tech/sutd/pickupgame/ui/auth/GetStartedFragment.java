package tech.sutd.pickupgame.ui.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.FragmentGetStartedBinding;

public class GetStartedFragment extends DaggerFragment implements View.OnClickListener {

    private FragmentGetStartedBinding binding;
    private NavController navController;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGetStartedBinding.inflate(inflater, container, false);
        setLogo();

        return binding.getRoot();
    }

    private void setLogo() {
        requestManager.load(logo)
                .into(binding.loginImg);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.getStarted.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        navController.navigate(R.id.action_getStartedFragment_to_loginFragment);
    }
}
