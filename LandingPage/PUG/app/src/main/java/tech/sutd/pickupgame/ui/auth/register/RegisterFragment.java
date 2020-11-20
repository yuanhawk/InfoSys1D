package tech.sutd.pickupgame.ui.auth.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.constant.ClickState;
import tech.sutd.pickupgame.databinding.FragmentRegisterBinding;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.models.UserProfile;
import tech.sutd.pickupgame.ui.auth.UserViewModel;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class RegisterFragment extends DaggerFragment {

    private int clickState = ClickState.NONE;

    private FragmentRegisterBinding binding;
    private NavController navController;

    private UserViewModel viewModel;

    @Inject FirebaseAuth fAuth;
    @Inject ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        binding.signIn.setOnClickListener(v -> navController.popBackStack(R.id.loginFragment, false));
        binding.registerCV.setOnClickListener(v -> {
            if (clickState == ClickState.NONE)
                registerUser();
        });

        viewModel = new ViewModelProvider(this, providerFactory).get(UserViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void registerUser() {
        clickState = ClickState.CLICKED;
        binding.progress.setVisibility(View.VISIBLE);
        String name = String.valueOf(binding.name.getText()).trim();
        String email = String.valueOf(binding.userId.getText()).trim();
        String passwd = String.valueOf(binding.passwd.getText()).trim();
        String confirmPasswd = String.valueOf(binding.confirmPasswd.getText()).trim();

        if (TextUtils.isEmpty(name)) {
            binding.name.setError("Name is Required");
            registerFailed();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            binding.userId.setError("Email is Required");
            registerFailed();
            return;
        }

        if (TextUtils.isEmpty(passwd)) {
            binding.passwd.setError("Password is Required");
            registerFailed();
            return;
        }

        if (passwd.length() < 6) {
            binding.passwd.setError("Password must be longer than 6 Characters");
            registerFailed();
            return;
        }

        if (!confirmPasswd.equals(passwd)) {
            binding.confirmPasswd.setError("Please reconfirm your password, your password is not the same");
            registerFailed();
            return;
        }

        UserProfile user = new UserProfile.Builder()
                .setName(name)
                .setEmail(email)
                .setPasswd(passwd)
                .build();

        viewModel.register(this, getContext(), navController, user);
    }

    public void registerFailed() {
        clickState = ClickState.NONE;
        binding.progress.setVisibility(View.GONE);
    }
}