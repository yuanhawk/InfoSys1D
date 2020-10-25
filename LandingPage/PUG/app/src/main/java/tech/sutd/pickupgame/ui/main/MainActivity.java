package tech.sutd.pickupgame.ui.main;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.databinding.ActivityMainBinding;
import tech.sutd.pickupgame.ui.auth.AuthActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private ActivityMainBinding binding;

    private BottomNavigationView bottomNavView;
    private NavController navController;
    private AppBarConfiguration configuration;


    /**
     * bottomnavigationview
     * navcontroller
     * setupwithnavcontroller
     */

    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        bottomNavView = binding.bottomNavView;

        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        configuration = new AppBarConfiguration.Builder(R.id.mainFragment, R.id.bookingFragment, R.id.userFragment)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(bottomNavView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                sessionManager.logout(this);
                return true;

            default:
                return false;
        }
    }

    public void logout() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}