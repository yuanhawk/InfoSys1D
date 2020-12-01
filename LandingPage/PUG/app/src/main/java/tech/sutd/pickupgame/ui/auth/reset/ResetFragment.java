package tech.sutd.pickupgame.ui.auth.reset;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.ui.user.AuthResource;
import tech.sutd.pickupgame.databinding.FragmentResetBinding;
import tech.sutd.pickupgame.models.UserProfile;
import tech.sutd.pickupgame.ui.auth.viewmodel.UserViewModel;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class ResetFragment extends BaseFragment {

    private FragmentResetBinding binding;

    @Inject FirebaseAuth fAuth;
    @Inject UserViewModel viewModel;
    @Inject ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentResetBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this, providerFactory).get(UserViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.resetBtn.setOnClickListener(v -> {
            String email = String.valueOf(binding.email.getText()).trim();

            viewModel.reset(new UserProfile.Builder()
                    .setEmail(email)
                    .build()
            ).observe(getViewLifecycleOwner(), userProfileAuthResource -> {
                switch (userProfileAuthResource.status) {
                    case LOADING:
                        binding.progress.setVisibility(View.VISIBLE);
                        break;
                    case RESET:
                        binding.progress.setVisibility(View.GONE);
                        getNavController().popBackStack(R.id.loginFragment, false);
                        Toast.makeText(getActivity(), "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                        break;
                    case ERROR:
                        binding.progress.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Fail to send reset password email!", Toast.LENGTH_SHORT).show();
                        break;
                }
            });
        });
    }


}