package tech.sutd.pickupgame.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dagger.android.support.DaggerAppCompatActivity;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.ActivityAuthBinding;

public class AuthActivity extends DaggerAppCompatActivity {

    private ActivityAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}