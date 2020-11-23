package tech.sutd.pickupgame.ui.main.user.editprofile;

import android.Manifest;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;

import java.io.File;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.FragmentEditProfileBinding;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends BaseFragment implements View.OnClickListener {

    private FragmentEditProfileBinding binding;

    private static final int READ_PERMISSION = 1;
    private static final int IMAGE_PICK_CODE = 2;

    @Inject RequestManager requestManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.back.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION);
        else
            binding.profileImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == binding.back.getId())
            getNavController().popBackStack(R.id.userFragment, false);
        else if (id == binding.profileImage.getId())
            loadImages();
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
            requestManager.load(data.getData())
                    .into(binding.profileImage);
        }
    }
}