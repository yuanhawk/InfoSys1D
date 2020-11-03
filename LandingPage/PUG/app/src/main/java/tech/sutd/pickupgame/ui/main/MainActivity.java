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
import android.os.Handler;
import android.os.Looper;
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
    private NavController navController;

    private FragmentManager fragmentManager;
    private Menu menu;
    private Handler handler = new Handler(Looper.getMainLooper());
//    private AppBarConfiguration configuration;

    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentManager = getSupportFragmentManager();
        init();
    }

    private void init() {
        navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
//        configuration = new AppBarConfiguration.Builder(R.id.mainFragment, R.id.userFragment)
//                .build();
//        NavigationUI.setupActionBarWithNavController(this, navController);
//        NavigationUI.setupWithNavController(bottomNavView, navController);
        binding.bottomNavView.setOnNavigationItemSelectedListener(this);
        menu = binding.bottomNavView.getMenu();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainFragment:
                checkBookingFragment();
                navController.popBackStack(R.id.mainFragment, false);
                break;
            case R.id.bookingFragment:
                Fragment fragment = fragmentManager.findFragmentByTag(TAG);
                if (fragment == null && clickState == ClickState.NONE) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.nav_host_fragment, new BookingFragment(), TAG)
                            .commit();
                    clickState = ClickState.CLICKED;

                    setIcon(R.drawable.ic_remove);
                } else {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                    clickState = ClickState.NONE;

                    setIcon(R.drawable.ic_add);
                }
                break;
            case R.id.userFragment:
                checkBookingFragment();
                navController.popBackStack(R.id.userFragment, true);
                navController.navigate(R.id.userFragment);
                break;
        }
        return true;
    }

    private void setIcon(int drawable) {
        handler.post(() -> {
            runOnUiThread(() ->
                    menu.findItem(R.id.bookingFragment).setIcon(drawable));
            Thread.currentThread().interrupt();
        });
    }

    public void checkBookingFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            clickState = ClickState.NONE;

            setIcon(R.drawable.ic_add);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            clickState = ClickState.NONE;
            setIcon(R.drawable.ic_add);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
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