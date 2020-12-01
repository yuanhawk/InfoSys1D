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
import tech.sutd.pickupgame.data.ui.user.AuthResource;
import tech.sutd.pickupgame.databinding.ActivityAuthBinding;
import tech.sutd.pickupgame.ui.auth.viewmodel.UserViewModel;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.ui.main.MainActivity;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class AuthActivity extends DaggerAppCompatActivity {

    private ActivityAuthBinding binding;

    @Inject SessionManager sessionManager;

    public void login() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        subscribeObserver();
    }

    private void subscribeObserver() {
        sessionManager.observeAuthState().observe(this, firebaseAuthAuthResource -> {
            if (firebaseAuthAuthResource.status == AuthResource.AuthStatus.AUTHENTICATED) {
                if (firebaseAuthAuthResource.data.getCurrentUser().isEmailVerified())
                    login();
            }
        });
    }

}