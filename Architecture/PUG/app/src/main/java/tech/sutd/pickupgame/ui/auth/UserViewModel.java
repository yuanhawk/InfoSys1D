package tech.sutd.pickupgame.ui.auth;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.databinding.FragmentLoginBinding;
import tech.sutd.pickupgame.databinding.FragmentRegisterBinding;
import tech.sutd.pickupgame.models.User;

public class UserViewModel extends ViewModel {

    private FirebaseAuth fAuth;
    private SessionManager sessionManager;
    private User user;

    @Inject
    public UserViewModel(FirebaseAuth fAuth, SessionManager sessionManager, User user) {
        this.fAuth = fAuth;
        this.sessionManager = sessionManager;
        this.user = user;
    }

    public void login(Context context, FragmentLoginBinding binding) {
        sessionManager.login(context, user);

        binding.progress.setVisibility(View.GONE);
        binding.userId.setText(null);
        binding.passwd.setText(null);

        binding.progress.setVisibility(View.GONE);
        binding.userId.setText(null);
        binding.passwd.setText(null);
    }


    public void register(Context context, FragmentRegisterBinding binding) {
        sessionManager.register(context, user);

        binding.progress.setVisibility(View.GONE);
        binding.name.setText(null);
        binding.userId.setText(null);
        binding.passwd.setText(null);
        binding.confirmPasswd.setText(null);

        user.setUsername(null);
        user.setEmail(null);
        user.setPasswd(null);
    }

}
