package tech.sutd.pickupgame.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.databinding.ActivityAuthBinding;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.ui.main.MainActivity;

public class AuthActivity extends DaggerAppCompatActivity implements BaseInterface {

    private ActivityAuthBinding binding;

    @Inject SessionManager sessionManager;

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

        if (sessionManager.getFirebaseUser() != null)
            customAction();
    }

}