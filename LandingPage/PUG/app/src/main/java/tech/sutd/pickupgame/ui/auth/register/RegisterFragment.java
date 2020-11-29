package tech.sutd.pickupgame.ui.auth.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
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
import tech.sutd.pickupgame.constant.ClickState;
import tech.sutd.pickupgame.databinding.FragmentRegisterBinding;
import tech.sutd.pickupgame.models.UserProfile;
import tech.sutd.pickupgame.ui.auth.viewmodel.UserViewModel;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {

    private int clickState = ClickState.NONE;

    private FragmentRegisterBinding binding;

    private UserViewModel viewModel;

    @Inject FirebaseAuth fAuth;
    @Inject ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        binding.signIn.setOnClickListener(this);
        binding.registerCV.setOnClickListener(this);

        viewModel = new ViewModelProvider(this, providerFactory).get(UserViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == binding.signIn.getId())
            getNavController().popBackStack(R.id.loginFragment, false);
        else if (id == binding.registerCV.getId()) {
            if (clickState == ClickState.NONE)
                registerUser();
        }
    }

    private void registerUser() {
        clickState = ClickState.CLICKED;

        String name = String.valueOf(binding.name.getText()).trim();
        String email = String.valueOf(binding.userId.getText()).trim();
        String passwd = String.valueOf(binding.passwd.getText()).trim();
        String confirmPasswd = String.valueOf(binding.confirmPasswd.getText()).trim();

        if (TextUtils.isEmpty(name)) {
            binding.name.setError("Name is Required");
            registerFailed("Name is Required");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            binding.userId.setError("Email is Required");
            registerFailed("Email is Required");
            return;
        }

        if (TextUtils.isEmpty(passwd)) {
            binding.passwd.setError("Password is Required");
            registerFailed("Password is Required");
            return;
        }

        if (passwd.length() < 6) {
            binding.passwd.setError("Password must be longer than 6 Characters");
            registerFailed("Password must be longer than 6 Characters");
            return;
        }

        if (!confirmPasswd.equals(passwd)) {
            binding.confirmPasswd.setError("Please reconfirm your password, your password is not the same");
            registerFailed("Please reconfirm your password, your password is not the same");
            return;
        }

        UserProfile user = new UserProfile.Builder()
                .setName(name)
                .setEmail(email)
                .setPasswd(passwd)
                .build();

        binding.setUser(user);

        viewModel.register(user).observe(getViewLifecycleOwner(), userProfileAuthResource -> {
            switch (userProfileAuthResource.status) {
                case LOADING:
                    showProgressBar(true);
                    break;
                case REGISTERED:
                    if (userProfileAuthResource.data != null) {
                        Toast.makeText(getContext(), userProfileAuthResource.data.getName() + " Created", Toast.LENGTH_SHORT).show();
                    }
                    getNavController().popBackStack(R.id.loginFragment, false);
                    break;
                case ERROR:
                    registerFailed(userProfileAuthResource.message);
                    break;

            }
        });
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible)
            binding.progress.setVisibility(View.VISIBLE);
        else
            binding.progress.setVisibility(View.GONE);
    }


    public void registerFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        clickState = ClickState.NONE;
        showProgressBar(false);
    }
}