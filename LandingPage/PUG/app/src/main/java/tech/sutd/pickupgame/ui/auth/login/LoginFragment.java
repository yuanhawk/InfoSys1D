package tech.sutd.pickupgame.ui.auth.login;

import android.content.Context;
import android.content.SharedPreferences;
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
import tech.sutd.pickupgame.BaseApplication;
import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.constant.ClickState;
import tech.sutd.pickupgame.databinding.FragmentLoginBinding;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.models.UserProfile;
import tech.sutd.pickupgame.ui.auth.UserViewModel;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    // TODO: Create a remember me btn

    private int clickState = ClickState.NONE;

    private FragmentLoginBinding binding;
    private UserViewModel viewModel;

    private BaseInterface listener;

    @Inject FirebaseAuth firebaseAuth;
    @Inject ViewModelProviderFactory providerFactory;
    @Inject SharedPreferences preferences;

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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.signUp.getId())
            getNavController().navigate(R.id.action_loginFragment_to_registerFragment);
        else if (id == binding.login.getId()) {
            if (clickState == ClickState.NONE)
                login();
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
            loginFailed();
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

        viewModel.login(this, getContext(), user);
    }

    public void loginFailed() {
        clickState = ClickState.NONE;
        binding.progress.setVisibility(View.GONE);
    }

    public BaseInterface getListener() {
        return listener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (BaseInterface) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

    }
}