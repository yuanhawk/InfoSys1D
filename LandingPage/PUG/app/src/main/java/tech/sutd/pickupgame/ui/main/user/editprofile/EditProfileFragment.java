package tech.sutd.pickupgame.ui.main.user.editprofile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.constant.ClickState;
import tech.sutd.pickupgame.data.ui.user.AuthResource;
import tech.sutd.pickupgame.databinding.FragmentEditProfileBinding;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.models.UserProfile;
import tech.sutd.pickupgame.ui.auth.viewmodel.UserViewModel;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends BaseFragment implements View.OnClickListener {

    private Uri img;
    private User user;

    private FragmentEditProfileBinding binding;

    private static final int READ_PERMISSION = 1;
    private static final int IMAGE_PICK_CODE = 2;

    private BaseInterface.RefreshListener refreshListener;

    @Inject RequestManager requestManager;

    @Inject UserViewModel viewModel;
    @Inject ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this, providerFactory).get(UserViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.back.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            binding.profileImage.setOnClickListener(this);

        binding.changeProfilePhoto.setOnClickListener(this);
        binding.confirm.setOnClickListener(this);

        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            if (users.size() > 0 && !users.get(0).getId().equalsIgnoreCase("0")) {
                user = users.get(0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == binding.back.getId())
            getNavController().popBackStack(R.id.userFragment, false);
        else if (id == binding.profileImage.getId() || id == binding.changeProfilePhoto.getId())
            loadImages();
        else if (id == binding.confirm.getId()) {
            String name = String.valueOf(binding.usernameInput.getText()).trim();
            String age = String.valueOf(binding.ageInput.getText()).trim();

            if (img == null) {
                Toast.makeText(getContext(), "Image is Required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(name)) {
                binding.usernameInput.setError("Name is Required");
                return;
            }

            if (TextUtils.isEmpty(age)) {
                binding.ageInput.setError("Email is Required");
                return;
            }


            viewModel.updateUserDetails(new UserProfile.Builder()
                    .setImg(String.valueOf(img))
                    .setName(name)
                    .setAge(age)
                    .setEmail(user.getEmail())
                    .setPasswd(user.getPasswd())
                    .build()).observe(getViewLifecycleOwner(), firebaseAuthAuthResource -> {
                        switch (firebaseAuthAuthResource.status) {
                            case LOADING:
                                binding.progress.setVisibility(View.VISIBLE);
                                break;
                            case UPDATED:
                                binding.progress.setVisibility(View.GONE);
                                refreshListener.refreshObserver();
                                Toast.makeText(getContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                getNavController().popBackStack(R.id.userFragment, false);
                                break;
                            case ERROR:
                                Toast.makeText(getContext(), firebaseAuthAuthResource.message, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    });

        }
    }

    private void loadImages() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Read external storage permission granted", Toast.LENGTH_SHORT).show();
                loadImages();
            } else
                Toast.makeText(getContext(), "Read external storage permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && data != null) {
            img = data.getData();
            requestManager.load(img)
                    .into(binding.profileImage);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            refreshListener = (BaseInterface.RefreshListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}