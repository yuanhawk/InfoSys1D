package tech.sutd.pickupgame.ui.auth.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.constant.ClickState;
import tech.sutd.pickupgame.data.ui.user.AuthResource;
import tech.sutd.pickupgame.databinding.FragmentLoginBinding;
import tech.sutd.pickupgame.models.UserProfile;
import tech.sutd.pickupgame.ui.auth.viewmodel.UserViewModel;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

import static android.telecom.DisconnectCause.ERROR;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    // TODO: Create a remember me btn

    private int clickState = ClickState.NONE;

    private FragmentLoginBinding binding;
    private UserViewModel viewModel;

    @Inject FirebaseAuth firebaseAuth;
    @Inject ViewModelProviderFactory providerFactory;
    @Inject SharedPreferences preferences;
    @Inject SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        viewModel = new ViewModelProvider(this, providerFactory).get(UserViewModel.class);

        if (preferences.getBoolean(getString(R.string.remember_me), false)) {// if checked
            subscribeObserver();
            binding.rememberMe.setChecked(true);
        }

        return binding.getRoot();
    }

    private void subscribeObserver() {
        viewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            if (users.size() > 0 && !users.get(0).getId().equalsIgnoreCase("0")) {
                binding.userId.setText(users.get(0).getEmail());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.signUp.setOnClickListener(this);
        binding.login.setOnClickListener(this);
        binding.forgotPassword.setOnClickListener(this);

        initObserver();
    }

    private void initObserver() {
        sessionManager.observeAuthState().observe(getViewLifecycleOwner(), firebaseAuthAuthResource -> {
            switch (firebaseAuthAuthResource.status) {
                case LOADING:
                    binding.progress.setVisibility(View.VISIBLE);
                    break;
                case AUTHENTICATED:
                    binding.progress.setVisibility(View.GONE);
                    if (firebaseAuthAuthResource.data != null)
                        viewModel.insertUserDb(firebaseAuthAuthResource.data);
                    if (!firebaseAuthAuthResource.data.getCurrentUser().isEmailVerified()) {
                        clickState = ClickState.NONE;
                        Toast.makeText(getContext(), "Please verify your email", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ERROR:
                    binding.progress.setVisibility(View.GONE);
                    clickState = ClickState.NONE;
                    Toast.makeText(getContext(), firebaseAuthAuthResource.message, Toast.LENGTH_SHORT).show();
                     break;
                case NOT_AUTHENTICATED:
                    binding.progress.setVisibility(View.GONE);
                    clickState = ClickState.NONE;
                    break;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.signUp.getId())
            getNavController().navigate(R.id.action_loginFragment_to_registerFragment);
        else if (id == binding.login.getId())
            login();
        else if (id == binding.forgotPassword.getId())
            getNavController().navigate(R.id.action_loginFragment_to_resetFragment);
    }

    private void login() {
        if (clickState == ClickState.CLICKED)
            return;

        clickState = ClickState.CLICKED;

        String email = String.valueOf(binding.userId.getText()).trim();
        String passwd = String.valueOf(binding.passwd.getText()).trim();

        if (TextUtils.isEmpty(email)) {
            binding.userId.setError("Email is Required");
            clickState = ClickState.NONE;
            return;
        }

        if (TextUtils.isEmpty(passwd)) {
            binding.passwd.setError("Password is Required");
            clickState = ClickState.NONE;
            return;
        }

        if (binding.rememberMe.isChecked() && !preferences.getBoolean(getString(R.string.remember_me), false)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(getString(R.string.remember_me), true);
            editor.apply();
        }

        if (!binding.rememberMe.isChecked() && preferences.getBoolean(getString(R.string.remember_me), false)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(getString(R.string.remember_me), false);
            editor.apply();
        }

        UserProfile user = new UserProfile.Builder()
                .setEmail(email)
                .setPasswd(passwd)
                .build();

        binding.setUser(user);
        viewModel.login(user);
    }

}