package tech.sutd.pickupgame.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.ActivityAuthBinding;

public class AuthActivity extends DaggerAppCompatActivity {

    private ActivityAuthBinding binding;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setLogo();
    }

    private void setLogo() {
        requestManager.load(logo)
                .into((ImageView) binding.loginImg);
    }
}