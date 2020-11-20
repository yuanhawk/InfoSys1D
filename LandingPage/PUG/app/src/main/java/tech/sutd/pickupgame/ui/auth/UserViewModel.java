package tech.sutd.pickupgame.ui.auth;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.functions.Function;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.data.UserRepository;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.models.UserProfile;
import tech.sutd.pickupgame.ui.auth.login.LoginFragment;
import tech.sutd.pickupgame.ui.auth.register.RegisterFragment;

public class UserViewModel extends ViewModel {

    private SessionManager sessionManager;
    private UserRepository repository;

    private LiveData<List<User>> users;

    @Inject
    public UserViewModel(@NonNull Application application, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.repository = new UserRepository(application);
        users = repository.getAllUsers();
    }

    public void register(RegisterFragment fragment, Context context, NavController navController, UserProfile user) {
        sessionManager.register(fragment, context, navController, user);
    }

    public void login(LoginFragment fragment, Context context, UserProfile user) {
        sessionManager.login(fragment, this, context, user);
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void deleteAllUsers() {
        repository.deleteAllUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}
