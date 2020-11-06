package tech.sutd.pickupgame.ui.auth.login;

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
import tech.sutd.pickupgame.databinding.FragmentLoginBinding;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.ui.auth.UserViewModel;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class LoginFragment extends DaggerFragment implements View.OnClickListener {

    // TODO: Create a remember me btn

    private int clickState = ClickState.NONE;

    private FragmentLoginBinding binding;
    private NavController navController;

    private UserViewModel viewModel;

    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        binding.signUp.setOnClickListener(this);
        binding.login.setOnClickListener(this);

        viewModel = new ViewModelProvider(this, providerFactory).get(UserViewModel.class);

        subscribeObserver();
        return binding.getRoot();
    }

    private void subscribeObserver() {
        viewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            if (!users.get(0).getEmail().equalsIgnoreCase("-1")) {
                binding.userId.setText(users.get(0).getEmail());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUp:
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
                break;
            case R.id.login:
                if (clickState == ClickState.NONE)
                    login();
                break;
        }
    }

    private void login() {
        clickState = ClickState.CLICKED;
        binding.progress.setVisibility(View.VISIBLE);
        String email = String.valueOf(binding.userId.getText()).trim();
        String passwd = String.valueOf(binding.passwd.getText()).trim();

        if (TextUtils.isEmpty(email)) {
            binding.userId.setError("Email is Required");
            loginFailed();
            return;
        }

        if (TextUtils.isEmpty(passwd)) {
            binding.passwd.setError("Password is Required");
            return;
        }

        User user = new User();
        user.setId(0);
        user.setEmail(email);
        user.setPasswd(passwd);

        viewModel.login(this, getContext(), user);
    }

    public void loginFailed() {
        clickState = ClickState.NONE;
        binding.progress.setVisibility(View.GONE);
    }
}