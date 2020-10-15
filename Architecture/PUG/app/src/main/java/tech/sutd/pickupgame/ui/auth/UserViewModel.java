package tech.sutd.pickupgame.ui.auth;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import javax.inject.Inject;

import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.data.UserRepository;
import tech.sutd.pickupgame.databinding.FragmentLoginBinding;
import tech.sutd.pickupgame.databinding.FragmentRegisterBinding;
import tech.sutd.pickupgame.models.User;

public class UserViewModel extends ViewModel {

    // TODO: Rmb to implement Visitor Pattern in a recycleradapter

    private SessionManager sessionManager;
    private UserRepository repository;

    private LiveData<List<User>> users;

    @Inject
    public UserViewModel(@NonNull Application application, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.repository = new UserRepository(application);
        users = repository.getAllUsers();
    }

    public void register(Context context, FragmentRegisterBinding binding, User user) {
        repository.update(user);
        sessionManager.register(context, user);

        binding.progress.setVisibility(View.GONE);
        binding.name.setText(null);
        binding.userId.setText(null);
        binding.passwd.setText(null);
        binding.confirmPasswd.setText(null);
    }

    public void login(Context context, FragmentLoginBinding binding, User user) {
        repository.update(user);
        sessionManager.login(context, user);

        binding.progress.setVisibility(View.GONE);
        binding.userId.setText(null);
        binding.passwd.setText(null);

        binding.progress.setVisibility(View.GONE);
        binding.userId.setText(null);
        binding.passwd.setText(null);
    }

    // Remember to reset the user details to -1
    public void logout(Context context, User user) {
        repository.update(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void deleteAllUsers() {
        repository.deleteAllUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

}
