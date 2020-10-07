package tech.sutd.pickupgame.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.databinding.FragmentLoginBinding;

public class LoginFragment extends DaggerFragment implements View.OnClickListener {

    private FragmentLoginBinding binding;

    @Inject
    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.signInWithGoogle.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {

    }
}