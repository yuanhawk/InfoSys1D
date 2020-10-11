package tech.sutd.pickupgame.ui.auth.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import tech.sutd.pickupgame.databinding.FragmentRegisterBinding;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class RegisterFragment extends DaggerFragment implements View.OnClickListener {

    private FragmentRegisterBinding binding;
    private NavController navController;

    private RegisterViewModel viewModel;

    @Inject
    FirebaseAuth fAuth;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        binding.signIn.setOnClickListener(this);
        binding.registerCV.setOnClickListener(this);

        viewModel = new ViewModelProvider(this, providerFactory).get(RegisterViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signIn:
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
                break;
            case R.id.registerCV:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = String.valueOf(binding.userId.getText()).trim();
        String passwd = String.valueOf(binding.passwd.getText()).trim();

        if (TextUtils.isEmpty(email)) {
            binding.userId.setError("Email is Required");
            return;
        }

        if (TextUtils.isEmpty(passwd)) {
            binding.passwd.setError("Password is Required");
            return;
        }

        if (passwd.length() < 6) {
            binding.passwd.setError("Password must be longer than 6 Characters");
            return;
        }

        user.setEmail(email);
        user.setPasswd(passwd);

        binding.progress.setVisibility(View.VISIBLE);
        viewModel.register(getContext(), binding);
    }
}