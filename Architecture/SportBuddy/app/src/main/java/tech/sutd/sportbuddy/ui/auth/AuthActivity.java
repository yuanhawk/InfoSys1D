package tech.sutd.sportbuddy.ui.auth;

import android.os.Bundle;

import dagger.android.support.DaggerAppCompatActivity;
import tech.sutd.sportbuddy.R;
import tech.sutd.sportbuddy.databinding.ActivityAuthBinding;

public class AuthActivity extends DaggerAppCompatActivity {

    private ActivityAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}