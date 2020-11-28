package tech.sutd.pickupgame.ui.auth;

import android.content.Intent;
import android.os.Bundle;

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

    private UserViewModel viewModel;

    @Inject ViewModelProviderFactory providerFactory;
    @Inject SessionManager sessionManager;

    @Override
    public void customAction() { // login from loginFragment
        sessionManager.autoLogin(viewModel);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAuthBinding binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, providerFactory).get(UserViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sessionManager.getFirebaseUser() != null)
            customAction();
    }
}