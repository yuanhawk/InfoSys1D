package tech.sutd.pickupgame.ui.main;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import javax.inject.Inject;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.constant.ClickState;
import tech.sutd.pickupgame.data.ui.user.AuthResource;
import tech.sutd.pickupgame.databinding.ActivityMainBinding;
import tech.sutd.pickupgame.BaseActivity;
import tech.sutd.pickupgame.ui.auth.AuthActivity;
import tech.sutd.pickupgame.ui.auth.viewmodel.UserViewModel;
import tech.sutd.pickupgame.ui.main.booking.BookingFragment;
import tech.sutd.pickupgame.ui.main.main.MainFragment;
import tech.sutd.pickupgame.ui.main.main.newact.NewActFragment;
import tech.sutd.pickupgame.ui.main.main.upcomingact.UpcomingActFragment;
import tech.sutd.pickupgame.ui.main.main.viewmodel.BookingActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.NewActViewModel;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        BaseInterface.BookingActListener, BaseInterface.CustomActListener,
        BaseInterface.UpcomingActDeleteListener, BaseInterface.RefreshListener {

    private static final int READ_PERMISSION = 1;

    private int clickState = ClickState.NONE;

    private static final String TAG = "booking";

    private ActivityMainBinding binding;
    private NavController navController;
    private Menu menu;

    private Observer<AuthResource<FirebaseAuth>> fireAuthObserver;

    @Inject BookingActViewModel bookingViewModel;

    @Inject FragmentManager fragmentManager;
    @Inject SessionManager sessionManager;
    @Inject Handler handler;
    @Inject SharedPreferences preferences;
    @Inject UserViewModel userViewModel;
    @Inject NewActViewModel newViewModel;
    @Inject ViewModelProviderFactory providerFactory;

    public void onBookingSuccess() {
        binding.progress.setVisibility(View.GONE);
        Toast.makeText(this, "Activity saved successfully", Toast.LENGTH_SHORT).show();

        checkBookingFragment();
    }

    public void onBookingFailure(String s) {
        binding.progress.setVisibility(View.GONE);
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void customAction() {
        binding.progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSignUpSuccess(MainFragment mainFragment, NewActFragment newFragment) {
        binding.progress.setVisibility(View.VISIBLE);
        pull();

        handler.postDelayed(() -> {
            if (mainFragment != null)
                mainFragment.refreshObserver();
            else if (newFragment != null)
                newFragment.refreshObserver();
            binding.progress.setVisibility(View.GONE);
            Toast.makeText(this, "Activity saved successfully", Toast.LENGTH_SHORT).show();
        }, 3000);
    }

    @Override
    public void onSignUpFailure() {
        Toast.makeText(this, "Activity not saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteSuccess(MainFragment mainFragment, UpcomingActFragment upcomingFragment) {
        binding.progress.setVisibility(View.VISIBLE);
        pull();

        handler.postDelayed(() -> {
            if (mainFragment != null)
                mainFragment.refreshObserver();
            else if (upcomingFragment != null)
                upcomingFragment.refreshObserver();
            binding.progress.setVisibility(View.GONE);
            Toast.makeText(this, "Activity deleted successfully", Toast.LENGTH_SHORT).show();
        }, 2000);
    }

    @Override
    public void onDeleteFailure() {
        Toast.makeText(this, "Activity not deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshObserver() {
        if (sessionManager.observeAuthState().hasActiveObservers())
            sessionManager.observeAuthState().removeObserver(fireAuthObserver);
        sessionManager.observeAuthState().observe(this, fireAuthObserver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION);

        pull();

        setContentView(binding.getRoot());

        bookingViewModel = new ViewModelProvider(this, providerFactory).get(BookingActViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        if (preferences.getBoolean(getString(R.string.activities_organised), false)) {
            addBookingFragment();

            setBookingFragmentStateBySharedPref(false);
        }
        subscribeObserver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            clickState = ClickState.NONE;

            setBookingFragmentStateBySharedPref(true);
        }
    }

    private void setBookingFragmentStateBySharedPref(boolean b) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(getString(R.string.activities_organised), b);
        editor.apply();
    }

    private void subscribeObserver() {
        bookingViewModel.observeBooking().observe(this, bookingActivityResource -> {
            switch (bookingActivityResource.status) {
                case LOADING:
                    binding.progress.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    onBookingSuccess();
                    break;
                case ERROR:
                    onBookingFailure(bookingActivityResource.msg);
                    break;
            }
        });

        fireAuthObserver = firebaseAuthAuthResource -> {
            switch (firebaseAuthAuthResource.status) {
                case UPDATED:
                    userViewModel.insertUserDb(firebaseAuthAuthResource.data);
                    break;
                case NOT_AUTHENTICATED:
                    navLoginScreen();
                    break;
            }
        };
        sessionManager.observeAuthState().observe(this, fireAuthObserver);
    }

    private void navLoginScreen() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
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