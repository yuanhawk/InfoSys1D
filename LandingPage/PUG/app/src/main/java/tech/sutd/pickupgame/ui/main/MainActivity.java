package tech.sutd.pickupgame.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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
import tech.sutd.pickupgame.constant.ClickState;
import tech.sutd.pickupgame.databinding.ActivityMainBinding;
import tech.sutd.pickupgame.ui.auth.AuthActivity;
import tech.sutd.pickupgame.ui.main.booking.BookingFragment;
import tech.sutd.pickupgame.ui.main.main.MainFragment;
import tech.sutd.pickupgame.ui.main.user.UserFragment;

public class MainActivity extends DaggerAppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private int clickState = ClickState.NONE;

    private static final String TAG = "booking";

    private ActivityMainBinding binding;

    private BottomNavigationView bottomNavView;
    private NavController navController;

    private Fragment fragment;
//    private AppBarConfiguration configuration;

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
        navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
//        configuration = new AppBarConfiguration.Builder(R.id.mainFragment, R.id.userFragment)
//                .build();
//        NavigationUI.setupActionBarWithNavController(this, navController);
//        NavigationUI.setupWithNavController(bottomNavView, navController);
        bottomNavView.setOnNavigationItemSelectedListener(this);

        if (clickState == ClickState.CLICKED) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.nav_host_fragment, new BookingFragment(), TAG)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainFragment:
                checkBookingFragment();
                navController.popBackStack(R.id.mainFragment, false);
                clickState = ClickState.NONE;
                break;
            case R.id.bookingFragment:
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragment = fragmentManager.findFragmentByTag(TAG);
                if (fragment == null && clickState == ClickState.NONE) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.nav_host_fragment, new BookingFragment(), TAG)
                            .commit();
                    clickState = ClickState.CLICKED;
                } else {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                    clickState = ClickState.NONE;
                }
                break;
            case R.id.userFragment:
                checkBookingFragment();
                navController.popBackStack(R.id.userFragment, true);
                navController.navigate(R.id.userFragment);
                clickState = ClickState.NONE;
                break;
        }
        return true;
    }

    public boolean checkBookingFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!checkBookingFragment())
            super.onBackPressed();
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