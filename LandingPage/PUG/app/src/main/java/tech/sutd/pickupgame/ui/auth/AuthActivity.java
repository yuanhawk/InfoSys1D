package tech.sutd.pickupgame.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.databinding.ActivityAuthBinding;
import tech.sutd.pickupgame.ui.auth.viewmodel.UserViewModel;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.ui.main.MainActivity;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class AuthActivity extends DaggerAppCompatActivity implements BaseInterface {

    private ActivityAuthBinding binding;
    private UserViewModel viewModel;

    @Inject SessionManager sessionManager;
    @Inject ViewModelProviderFactory providerFactory;

    @Override
    public void customAction() { // login from loginFragment
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, providerFactory).get(UserViewModel.class);

        subscribeObserver();
    }

    private void subscribeObserver() {
        sessionManager.observeAuthState().observe(this, firebaseAuthAuthResource -> {
            switch (firebaseAuthAuthResource.status) {
                case LOADING:
                    showProgressBar(true);
                    break;
                case AUTHENTICATED:
                    if (firebaseAuthAuthResource.data != null)
                        viewModel.insertUserDb(firebaseAuthAuthResource.data);
                    customAction();
                    break;
                case ERROR:
                    showProgressBar(false);
                    Toast.makeText(this, firebaseAuthAuthResource.message, Toast.LENGTH_SHORT).show();
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

}