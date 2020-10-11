package tech.sutd.pickupgame.ui.auth.login;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import tech.sutd.pickupgame.databinding.FragmentLoginBinding;
import tech.sutd.pickupgame.databinding.FragmentRegisterBinding;
import tech.sutd.pickupgame.models.User;

public class LoginViewModel extends ViewModel {

    private FirebaseAuth fAuth;
    private User user;

    @Inject
    public LoginViewModel(FirebaseAuth fAuth, User user) {
        this.fAuth = fAuth;
        this.user = user;
    }

    public void login(Context context, FragmentLoginBinding binding) {
        fAuth.signInWithEmailAndPassword(user.getEmail(), user.getPasswd()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    binding.progress.setVisibility(View.GONE);
                    binding.userId.setText(null);
                    binding.passwd.setText(null);

                    binding.progress.setVisibility(View.GONE);
                    binding.userId.setText(null);
                    binding.passwd.setText(null);
                }
        );
    }
}
