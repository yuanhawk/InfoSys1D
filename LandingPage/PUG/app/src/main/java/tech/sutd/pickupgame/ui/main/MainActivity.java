package tech.sutd.pickupgame.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.constant.ClickState;
import tech.sutd.pickupgame.data.worker.NewActivitiesWorker;
import tech.sutd.pickupgame.data.worker.UpcomingActivitiesWorker;
import tech.sutd.pickupgame.databinding.ActivityMainBinding;
import tech.sutd.pickupgame.ui.auth.AuthActivity;
import tech.sutd.pickupgame.ui.main.booking.BookingFragment;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, BaseInterface,
        SuccessListener, SuccessListenerTwo {

    private int clickState = ClickState.NONE;

    private static final String TAG = "booking";

    private ActivityMainBinding binding;
    private NavController navController;
    private Menu menu;

    private BaseInterface baseInterface;

    @Inject FragmentManager fragmentManager;
    @Inject SessionManager sessionManager;
    @Inject Handler handler;
    @Inject SharedPreferences preferences;

    public void setBaseInterface(BaseInterface baseInterface) {
        this.baseInterface = baseInterface;
    }

    @Override
    public void onBookingSuccess() {
        handler.post(() -> {
            runOnUiThread(() -> {
                binding.progress.setVisibility(View.GONE);

                baseInterface.customAction();
                Toast.makeText(this, "Activity saved successfully", Toast.LENGTH_SHORT).show();

                checkBookingFragment();
            });
            Thread.currentThread().interrupt();
        });
    }

    @Override
    public void onBookingFailure() {
        handler.post(() -> {
            runOnUiThread(() -> {
                binding.progress.setVisibility(View.GONE);

                Toast.makeText(this, "Activity not saved", Toast.LENGTH_SHORT).show();
            });
            Thread.currentThread().interrupt();
        });
    }

    @Override
    public void customAction() { // setLoadingProgressBar
        handler.post(() -> {
            runOnUiThread(() -> binding.progress.setVisibility(View.VISIBLE));
            Thread.currentThread().interrupt();
        });
    }

    @Override
    public void onSignUpSuccess() {
        binding.progress.setVisibility(View.GONE);
        Toast.makeText(this, "Activity saved successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignUpFailure() {
        binding.progress.setVisibility(View.GONE);
        Toast.makeText(this, "Activity not saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        OneTimeWorkRequest upcomingRequest = new OneTimeWorkRequest.Builder(UpcomingActivitiesWorker.class)
                .build();

        OneTimeWorkRequest newRequest = new OneTimeWorkRequest.Builder(NewActivitiesWorker.class)
                .build();

        WorkManager.getInstance(getApplicationContext())
                .beginWith(upcomingRequest)
                .then(newRequest)
                .enqueue();

        setContentView(binding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        if (preferences.getBoolean(getString(R.string.activities_organised), false)) {
            addBookingFragment();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(getString(R.string.activities_organised), false);
            editor.apply();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            clickState = ClickState.NONE;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(getString(R.string.activities_organised), true);
            editor.apply();
        }
    }

    private void init() {
        navController = ((NavHostFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment))).getNavController();
        binding.bottomNavView.setOnNavigationItemSelectedListener(this);
        menu = binding.bottomNavView.getMenu();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mainFragment) {
            navController.popBackStack(R.id.mainFragment, false);
        } else if (id == R.id.bookingFragment) {
            Fragment fragment = fragmentManager.findFragmentByTag(TAG);
            if (fragment == null && clickState == ClickState.NONE) {
                addBookingFragment();
            } else {
                if (fragment != null) {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                    clickState = ClickState.NONE;

                    setIcon(R.drawable.ic_add);
                }
            }
        } else if (id == R.id.userFragment) {
            navController.popBackStack(R.id.userFragment, true);
            navController.navigate(R.id.userFragment);
        }
        return true;
    }

    private void addBookingFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BookingFragment bookingFragment = new BookingFragment();
        transaction.add(R.id.nav_host_fragment, bookingFragment, TAG)
                .commit();

        setBaseInterface(bookingFragment);

        clickState = ClickState.CLICKED;

        setIcon(R.drawable.ic_remove);
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
            return;
        }

        clickState = ClickState.CLICKED;

        setIcon(R.drawable.ic_remove);
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

}