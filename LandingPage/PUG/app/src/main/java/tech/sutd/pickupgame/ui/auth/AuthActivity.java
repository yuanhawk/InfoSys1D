package tech.sutd.pickupgame.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.databinding.ActivityAuthBinding;
import tech.sutd.pickupgame.ui.main.MainActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private ActivityAuthBinding binding;

    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (sessionManager.getFirebaseUser() != null)
            login();
    }

    public void login() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}